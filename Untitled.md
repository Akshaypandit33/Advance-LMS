

## 🏷 Entities Per Service (Simplified)

### 🔐 auth-service

- UserLoginRequest
- JwtToken, RefreshToken
- Tenant context resolver

### 🏢 tenant-service

- Tenant (id, name, schemaName, dbUrl, status)
- TenantConfigService → provisions new schemas
- Flyway or Liquibase per-tenant migration

### 👥 user-service

- User, Role, Permission
- TenantUserMapping
- ABAC using SpEL (canViewGrades, etc.)

### 🎓 course-service

- Department, Branch, Course, Module, Assignment

### 📘 learning-service

- Enrollment, Progress, Attendance, QuizResult

### 🧠 adaptive-service

- StudentModel (vector or JSON)
- ContentRecommendation, AdaptiveQuiz, DropoutPrediction
- Communicates with ML backend (FastAPI or Flask)

---

## 🧠 adaptive-service: Python ML Integration

| Endpoint              | Method | Description                                  |
|-----------------------|--------|----------------------------------------------|
| /recommendations    | POST   | Get content list based on learning vector    |
| /predict-dropout    | POST   | Predict dropout probability                  |
| /generate-quiz      | POST   | Auto-generate quiz (HuggingFace T5/BART)     |

> Integration via Spring WebClient or RestTemplate.  
> ML served via FastAPI + Gunicorn + Docker.

---

## 🌐 API Gateway Routing (Spring Cloud Gateway)

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: auth-service
          uri: lb://AUTH-SERVICE
          predicates:
            - Path=/auth/**
        - id: user-service
          uri: lb://USER-SERVICE
          predicates:
            - Path=/users/**
        - id: adaptive-service
          uri: lb://ADAPTIVE-SERVICE
          predicates:
            - Path=/adaptive/**