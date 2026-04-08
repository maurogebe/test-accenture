# Accenture Backend Challenge – Franchise API

## 📌 Description

This project is a **reactive REST API** built with **Spring Boot (WebFlux)** that allows managing:

* Franchises
* Branches
* Products
* Stock per product

The solution follows a **layered architecture inspired by Clean/Hexagonal principles**, separating:

* Application layer (use cases / services)
* Domain layer (business logic)
* Infrastructure layer (controllers, persistence, configuration)

---

## 🚀 Technologies Used

* Java 21
* Spring Boot (WebFlux)
* Reactive MongoDB
* Gradle
* Docker (for application containerization)
* GitHub Actions (CI/CD)
* AWS EC2 (deployment)
* Swagger / OpenAPI (API documentation)
* JUnit 5 + Mockito + StepVerifier (testing)

---

## 📂 Project Structure

```
src/main/java/com/accenture/challenge/franchise

├── application        # Use cases / services
├── domain             # Core business models & rules
├── infrastructure     # Controllers, DB, config
```

---

## ⚙️ Requirements

Before running the project, make sure you have:

* Java 21 installed
* Gradle (or use wrapper `./gradlew`)
* MongoDB Community installed and running locally

---

## 🗄️ Database Configuration

This project uses **MongoDB Community Edition (local installation)**.

Make sure MongoDB is running on:

```
mongodb://localhost:27017/franchise_db
```

You can verify Mongo is running with:

```bash
mongod
```

---

## ▶️ Running the Application Locally

### 1. Clone the repository

```bash
git clone https://github.com/maurogebe/test-accenture.git
cd test-accenture
```

### 2. Run the application

```bash
./gradlew bootRun
```

or on Windows:

```bash
gradlew.bat bootRun
```

---

## 📘 API Documentation (Swagger)

Once the app is running:

* Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

* OpenAPI JSON:

```
http://localhost:8080/v3/api-docs
```

---

## 📡 Available Endpoints

### Franchise

* `POST /api/franchises` → Create franchise
* `PATCH /api/franchises/{id}/name` → Update name
* `GET /api/franchises/{id}/top-stock-products` → Get highest stock product per branch

### Branch

* `POST /api/franchises/{franchiseId}/branches` → Create branch
* `PATCH /api/branches/{id}/name` → Update branch name

### Product

* `POST /api/branches/{branchId}/products` → Create product
* `DELETE /api/branches/{branchId}/products/{productId}` → Delete product
* `PATCH /api/products/{productId}/stock` → Update stock
* `PATCH /api/products/{productId}/name` → Update name

---

## 🧪 Testing

Run all tests:

```bash
./gradlew test
```

The project includes:

* Unit tests for services
* Reactive tests using StepVerifier
* Controller tests using WebTestClient

---

## 🐳 Docker

### Build image

```bash
docker build -t accenture-franchise-api .
```

### Run container

```bash
docker run -d -p 8080:8080 \
  -e SPRING_DATA_MONGODB_URI=mongodb://host.docker.internal:27017/franchise_db \
  --name accenture-api accenture-franchise-api
```

> MongoDB must be running locally or accessible from the container.

---

## 🔄 CI/CD

This project includes a **GitHub Actions pipeline** with:

* Build and test stage
* Deployment to AWS EC2 via SSH
* Automatic container rebuild and restart

---

## ☁️ Deployment (AWS EC2)

The application is deployed on an AWS EC2 instance using:

* Docker
* GitHub Actions (SSH deployment)

Deployment flow:

```
git push → GitHub Actions → EC2 → docker build → docker run
```

---

## 🧠 Design Decisions

* Reactive programming (WebFlux) to handle non-blocking operations
* Separation of concerns via layered architecture
* MongoDB chosen for flexible document structure
* Docker used for consistent deployment
* CI/CD implemented to automate delivery

---

## 📌 Notes

* MongoDB was installed locally using MongoDB Community Edition (not Docker)
* The application container is independent from the database
* Environment variables are used for configuration

---

## 👨‍💻 Author

Fabian Mauricio Guerra Bedoya

---
