# 📅 Daily Work Guide: Sony Music Microservices Platform

**Goal**: This document is your "cheat sheet" for running, testing, and talking about the project daily.

---

## 1. 🌅 Morning Routine: Start the Infrastructure

You don't run everything manually. You use the **Docker Stack**.

### Option A: The "Big Bang" (Run Everything)
Use this if you have a powerful laptop (16GB+ RAM) and want the full experience (Elasticsearch, Kibana, Prometheus, etc.).
```powershell
docker-compose up -d
```
*   **Wait**: It takes ~2 minutes for Kafka and ElasticSearch to settle.
*   **Verify**: Run `docker-compose ps`. All states should be `Up`.

### Option B: The "Dev Mode" (Run Infra Only)
Use this if you want to run the Java Apps in IntelliJ (for debugging), but run dependencies in Docker.
```powershell
# Only start Kafka, Postgres, Elastic, Zipkin (No Java Apps)
docker-compose up -d kafka postgres elasticsearch zipkin
```
Then, `Right Click` -> `Run` on `MetadataServiceApplication.java` in IntelliJ.

---

## 2. 👨‍💻 Development Workflow

### Feature: "Add a new Field to Artist"
1.  **Modify Entity**: Open `Artist.java`. Add `private String country;`.
2.  **Migration**: Create `V2__add_country.sql` in `db/migration`.
    ```sql
    ALTER TABLE artists ADD COLUMN country VARCHAR(255);
    ```
3.  **Test**: Restart the app. Flyway runs the script automatically.

---

## 3. 🔍 Observability (The "Fancy" Stuff)

When you run the full stack, you have these tools available via browser:

| Tool | URL | Credentials | Purpose |
| :--- | :--- | :--- | :--- |
| **API Gateway** | [localhost:8080](http://localhost:8080) | - | Entry point for all calls. |
| **Postgres Data** | Use DBeaver | `sonymusic` / `password` | Check `artists`, `albums` tables. |
| **Kibana** | [localhost:5601](http://localhost:5601) | - | Search logs ("Find errors"). |
| **Grafana** | [localhost:3000](http://localhost:3000) | `admin` / `admin` | See CPU/Memory dashboards. |
| **Zipkin** | [localhost:9411](http://localhost:9411) | - | See request traces (Waterfall chars). |
| **Prometheus**| [localhost:9090](http://localhost:9090) | - | Raw metrics. |

---

## 4. 🧪 Testing with Postman

Import the `SonyMusic_Postman_Collection.json` file into Postman.

**Test Scenario 1: The "Happy Path"**
1.  Run "Create Track".
2.  Status should be `201 Created`.
3.  Go to **Zipkin** (`localhost:9411`). Click "Run Query".
4.  You should see a trace: `gateway` -> `metadata-service` -> `kafka`.

**Test Scenario 2: The "Search"**
1.  Run "Search Tracks".
2.  It hits `localhost:8080/api/search/tracks`.
3.  Gateway routes to `search-service`.
4.  Service queries `Elasticsearch`.

---

## 5. 🗣️ How to Talk About This (Interview)

"I use a **Docker-first** workflow. My `docker-compose.yml` spins up the entire **Data Plane** (Kafka, Postgres, Elastic) and **Control Plane** (Prometheus, Zipkin).
This allows me to debug **Distributed Tracing** issues locally before they ever hit production. For example, I use **Zipkin** to catch latency spikes between the Gateway and the Metadata Service."
