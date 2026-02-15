Hospital Management Microservices System
<img width="1339" height="758" alt="image" src="https://github.com/user-attachments/assets/828ec4b4-2bae-49d4-8f54-cee70cf6c119" />
This repository contains a Java / Spring Boot–based microservices architecture for a hospital management system.
The project demonstrates real-world backend patterns such as service decomposition, synchronous and asynchronous communication, authentication, and event-driven design.

High-Level Overview

The system is composed of independent microservices, each responsible for a single business capability.
Services communicate using REST, gRPC, and Apache Kafka, and are designed to be deployed in a containerized environment.

Architecture Overview
Core Services

API Gateway

Entry point for all client requests

Routes requests to downstream services

Central place for cross-cutting concerns (auth, rate limiting, etc.)

Auth Service

Manages user authentication and authorization

Issues and validates JWT tokens

Stores users and roles in a relational database

Patient Service

Manages patient-related data

Acts as a Kafka producer for patient-related events

Communicates with Billing Service via gRPC

Billing Service

Handles billing and payment-related logic

Exposes gRPC endpoints for internal service-to-service communication

Notification Service

Consumes Kafka events

Sends notifications based on system events (e.g., patient created, billing completed)

Analytics Service

Processes and analyzes system events

Can be extended for reporting and insights

Integration Tests

End-to-end and integration-level tests covering service interactions

Communication Patterns Used
1. REST

External client communication

API Gateway → downstream services

2. gRPC

High-performance internal communication

Used between Patient Service and Billing Service

3. Apache Kafka (Event-Driven)

Asynchronous communication

Patient Service publishes events

Notification and Analytics services consume events

Data Management

PostgreSQL

Used by services that require persistent relational storage (Auth, Patient)

Schema evolution

Managed via JPA/Hibernate

Initialization

Services support automatic database initialization on startup

Security

JWT-based authentication

Role-based access control

Spring Security used in Auth Service

API Gateway can be extended to enforce authentication centrally

Development & Debugging

Designed to run locally using containers

Supports remote debugging via JVM debug ports

Environment-based configuration (12-factor app style)

Key Concepts Demonstrated

Microservices decomposition

Service-to-service communication

gRPC with Spring Boot

Kafka-based event-driven architecture

Centralized authentication

Database-per-service pattern

Clean separation of concerns

Production-style configuration management

