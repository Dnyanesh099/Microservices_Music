# 🛡️ THE DEFENSE GUIDE: Sony Music Microservices Platform
**Your Secret Weapon for the Interview**

You have listed this project on your resume. Now you must **DEFEND** it.
This guide maps every single bullet point from your resume to the **actual code** we just built.

---

## 📌 Resume Point 1: "Developed RESTful APIs using Spring Boot"

**The Question:** *"Can you walk me through an API you built?"*

**Your Answer (The Script):**
"I built the **Metadata Service**. One key endpoint was `POST /api/tracks`.
It wasn't just a simple save.
1.  It took a `TrackDTO`.
2.  I used **MapStruct** to convert it to a `Track` entity (to keep the API layer clean).
3.  I validated the payload using **Spring Validation** (`@NotNull`, `@Size`).
4.  And finally, I saved it to **PostgreSQL**."

**Show them the Code (Mental Check):**
*   **File**: `MetadataController.java`
*   **Annotation**: `@RestController`, `@PostMapping`
*   **Tech**: Spring Web, Lombok, MapStruct.

---

## 📌 Resume Point 2: "Implemented ISRC validation & Duplicate Checks"

**The Question:** *"How did you handle data integrity? What is an ISRC?"*

**Your Answer:**
"The ISRC (International Standard Recording Code) is the unique ID for a song, like a car's VIN number. The format is strict: `CC-XXX-YY-NNNNN`.
I implemented a **Regex Validation** inside the Service layer to reject invalid codes immediately.
For duplicates, I used a `findByIsrc` query. If the ISRC already existed, I updated the existing record (upsert) instead of creating a duplicate, to keep our catalog clean."

**Show them the Code:**
*   **File**: `MetadataService.java`
*   **Logic**: `trackDTO.getIsrc().matches("^[A-Z]{2}-...")`

---

## 📌 Resume Point 3: "Kafka Producers for Events (track-created)"

**The Question:** *"Why did you use Kafka? Why not just call the other service directly?"*

**Your Answer:**
"We needed **decoupling**. When a Track is uploaded, 5 other things need to happen:
1.  Search Index needs updating (Elasticsearch).
2.  Analytics needs to count it.
3.  Rights Management needs to know.
If I called all 5 services synchronously (REST), the upload would take 10 seconds and fail if *any* service was down.
Instead, I fire a **'Fire and Forget'** event: `TrackCreatedEvent`. The `SearchService` listens to this and indexes the track asynchronously."

**Show them the Code:**
*   **File**: `MetadataService.java` (Producer), `SearchEventListener.java` (Consumer).
*   **Topic**: `track-events`.

---

## 📌 Resume Point 4: "React.js Screens & Axios Integration"

**The Question:** *"How did the Frontend talk to your Backend?"*

**Your Answer:**
"The Frontend (React) never talks to microservices directly. It talks to the **API Gateway** on port `8080`.
The Gateway routed request `/api/metadata` to the `Metadata-Service`.
I handled **CORS** (Cross-Origin Resource Sharing) at the Gateway level so the React app running on localhost:3000 wouldn't get blocked."

**The Architecture:**
`React` --(Axios)--> `API Gateway` --(LoadBalancer)--> `Eureka` --> `Metadata Service`

---

## 📌 Resume Point 5: "Docker & Postman"

**The Question:** *"How did you test and deploy this?"*

**Your Answer:**
"Running 5 microservices manually is a nightmare. I used **Docker Compose**.
I can spin up the entire 'Sony Music Platform' (Kafka, Postgres, Elastic, Services) with one command: `docker-compose up -d`.
For testing, I created a **Postman Collection** with environmental variables so the QA team could test easily."

---

## ⚔️ The "Design Pattern" Checklist
(Mention these to sound Senior)

1.  **Circuit Breaker**: "Note how I used Resilience4j in the Gateway. If the Metadata Service dies, the user gets a friendly error, not a stack trace."
2.  **Database Per Service**: "The Metadata Service has its own Postgres. The Search Service has Elastic. No shared databases!"
3.  **Discovery Pattern**: "I used **Netflix Eureka** so services can find each other dynamically without hardcoding IPs."

---

## 🚀 How to Practice Tonight
1.  Open `MetadataService.java` in IntelliJ. Read it line-by-line.
2.  Run the app (`docker-compose up -d`).
3.  Hit the API with Postman.
4.  **Say the answers above out loud.**
