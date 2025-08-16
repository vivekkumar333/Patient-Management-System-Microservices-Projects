# PMS Patient Management System

A production-grade, microservices-based Patient Management System (PMS) built using Java 17, Spring Boot 3, RESTful APIs, Kafka, PostgreSQL, Angular 18, Docker, and Kubernetes.


## Project Overview

This PMS system manages patient,Admin and staff Login & registration, billing, notifications using a decoupled, scalable microservices architecture:

Registers patients and stores patient details.

Charges registration fees via billing service using RESTful WebClient calls.

Accept diffrenet types of Patient hospital bill's.

Taking care backend office work for Admin/staff/patient roles.

Sending notification to the patient registration, bill payment etc.


## Architecture
## Architecture

```text
                                +--------------------+
                                |    Angular UI      |
                                |   (pms-ui module)  |
                                +---------+----------+
                                          |
                                          ▼
                                +---------+----------+
                                |   API Gateway       |
                                |  (gateway-service)  |
                                +---------+----------+
                                          |
        ---------------------------------------------------------------------
        |                |                         |                       |
        ▼                ▼                         ▼                       ▼

+-------------------+   +--------------------+  +------------------+   +---------------------+
|   Auth Service    |   |  Patient Service   |  |  Billing Service |   | Notification Service|
|-------------------|   |--------------------|  |------------------|   |---------------------|
| - Login           |   | - Patient creation |  | - Generate bill  |   | - Send SMS/Email    |
| - Registration    |   | - Sync call billing|  | - Billing ops    |   | - Log notifications |
| - Token mgmt      |   | - Raise events     |  | - Raise events   |   |                     |
|                   |   |                    |  |                  |   |                     |
+-------------------+   +--------------------+  +------------------+   +---------------------+
        |                        ▲                      ▲                       ▲
        |                        |                      |                       |
        |                        |                      |                       |
        |                        |                      |                       |
        |                        |                      |                       |
        |                        |                      |                       |
        |                        |                      |                       |
        |                        |                      |                       |
        |          +---------------------------+        |                       |
        +--------->| auth-patient-registration |        |                       |
                   |          topic            |        |                       |
				   |						   |		|						|
				   |     If Role:PATIENT	   |		|						|
                   +---------------------------+        |                       |
                                                      +------------------+      |
                                                      | billing-topic    |<-----+
                                                      +------------------+
                                +-----------------------------------------------+
                                |         	Kafka Broker topics      			|
                                | (auth-patient-registration/patient/billing )	|
                                +-----------------------------------------------+

                    +-----------------------------------------------+
                    |              	PostgreSQL DB               	|
                    |(Can be separate DBs/Schemas for each service) |
                    | 		But in local docker environment 		|
					|	Created pms_database for below schema		|
					|-----------------------------------------------|
                    |  auth-db, patient-database, billing-database  |
                    +-----------------------------------------------+
```
## Docker-Containerization Port configuration for Services
Postgres db: 5000 	- Independent Container
Kafka: 9092			- Independent Container

patient-service: 4001
billing-service: 4002
notification-service: 4003
auth-service: 4004 
gateway-service: 4005


## Current Microservices

  ### 1️⃣ pms-patient-service

    Registers new patients and stores patient data.

    Calls pms-billing-service to charge patient registration using WebClient.

    Publishes Kafka patient events to pms-notification-service for notifications.

  ### 2️⃣ pms-billing-service

    Handles billing for patient registration.

    Receives WebClient POST calls from pms-patient-service.

    Future integration with Kafka for billing notifications.

  ### 3️⃣ pms-notification-service

    Consumes patient Kafka events from pms-patient-service.

    Sends email and mobile notifications upon patient registration.

    Future integration with billing service for payment notifications.
	
 ### gateway-service: 
    API gateway for routing and intercepting each call & token validation.

 ### auth-service: 
    JWT-based authentication and authorization & registration.

 ### angular-ui: 
    Frontend for registration, login, and patient dashboard, Admin & staff backend.


## Planned enhancements M2 -> RELEASE/REL_2025_M8:
### angular-ui: 
    Enhance the frontend to Create patient dashboard, Where he can book the doctor appointment, upload reports etc.
	Ehance the frontend to create admin/staff dashboard and role managment for backend operation from UI


### Deployment: 
    Plan is to deploy these services in local first in docker and kubernaties environment for testing.
	
	And then deploy them into any free cloud environment.



## Tech Stack

    Backend: Java 17, Spring Boot 3

    Frontend: Angular 18 (planned)

    Database: PostgreSQL

    Message Broker: Apache Kafka

    Containerization: Docker

    Orchestration: Kubernetes

    Build Tool: Maven

    Version Control: Git


