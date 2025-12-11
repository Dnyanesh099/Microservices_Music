# ☁️ How Sony Music Runs in the CLOUD (Real World)

**Goal**: You know how to run it on `localhost` (Docker Compose). Now, let's explain how this exact architecture runs in **Production (AWS/Azure)**.

---

## 1. The Mapping: "Local vs. Cloud"

| Localhost (Docker Compose) | Real Production (AWS) |
| :--- | :--- |
| **Your Laptop** | **Kubernetes Cluster (EKS)** |
| `docker-compose.yml` | **Helm Charts / K8s Manifests** |
| `postgres` container | **AWS RDS for PostgreSQL** (Managed DB) |
| `kafka` container | **AWS MSK (Managed Streaming for Kafka)** |
| `elasticsearch` container | **AWS OpenSearch Service** |
| `localhost:8080` | **api.sonymusic.com** (Route53 + Load Balancer) |

---

## 2. 🚦 The Request Flow in the Cloud

**Scenario**: A user in London uploads a song.

1.  **DNS Resolution**: User hits `api.sonymusic.com`. AWS Route53 resolves this to the **AWS Application Load Balancer (ALB)**.
2.  **Ingress Controller**: The request hits the Kubernetes Cluster (EKS). An **Nginx Ingress** routes it to your `API Gateway Service`.
3.  **Discovery**: The Gateway asks **Eureka** (running in a pod): *"Where is the Metadata Service?"*
4.  **Service-to-Service**: Gateway forwards the request to a `Metadata-Service` **Pod** running on an EC2 worker node.
5.  **Persistence**: The Metadata Service saves the Artist to **AWS RDS** (not a container!).
6.  **Events**: The Metadata Service sends a message to **AWS MSK (Kafka)**.

---

## 3. ☸️ Kubernetes (K8s) Deployment Strategy

In your interview, say this:

> "Locally, I use Docker Compose for speed.
> But in Production, we deploy each microservice as a **Deployment** in **Kubernetes**.
> We use a **Horizontal Pod Autoscaler (HPA)**.
> For example: On Friday (Release Day), traffic spikes. HPA automatically scales `metadata-service` from **2 pods to 50 pods** to handle the load."

---

## 4. 🚀 The CI/CD Pipeline (GitHub Actions -> AWS)

*"How does code get to the cloud?"*

1.  **Commit**: I push code to `main`.
2.  **CI (Build)**: GitHub Actions builds the JAR and runs Unit Tests.
3.  **Docker Push**: It builds a Docker Image `sonymusic/metadata:v1` and pushes it to **AWS ECR** (Elastic Container Registry).
4.  **CD (Deploy)**: ArgoCD (or a script) detects the new image and updates the Kubernetes Cluster.
5.  **Zero Downtime**: Kubernetes performs a **Rolling Update**. It replaces old pods one-by-one so users never notice a downtime.

---

## 5. 🗣️ The "Magic Words" for the Interview

If they ask "cloud", use these exact sentences:

*   "We use **Managed Services** for stateful components. We don't run Postgres in Docker containers in Prod; we use **AWS RDS** for reliability."
*   "Our microservices are stateless and containerized, deployed on **EKS (Kubernetes)**."
*   "We use **ConfigMaps** in K8s to inject environment variables (like DB passwords) instead of hardcoding them in `application.yml`."
