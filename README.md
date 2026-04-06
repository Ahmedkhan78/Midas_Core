# JPMorganChase Software Engineering Simulation

**Author:** Ahmed Hasan  
**Completed:** April 6, 2026  
**Platform:** Forage

This repository contains the project completed as part of the JPMorganChase Software Engineering job simulation on the Forage platform. The project demonstrates backend development skills using Java, Spring Boot, Kafka, SQL, and REST APIs in a real-world simulation.

## Project Overview

The goal of this simulation was to build a backend microservice capable of processing high-volume transaction messages, validating and persisting them, and exposing user balances via a REST API.

### Key Features

- **Kafka Integration:** Consume and deserialize high-volume transaction messages using a configurable topic and embedded Kafka test framework.
- **Transaction Processing:** Validate and persist transactions using Spring Data JPA and an H2 SQL database, updating User balances accordingly.
- **REST API Integration:** Connect to an external Incentive API using `RestTemplate` and incorporate responses into transactional workflows.
- **REST Endpoints:** Developed endpoints to query user balances with clean architectural separation.
- **Testing & Validation:** Verified system behavior using Maven test suites and debugger-driven inspection.

### Skills Demonstrated

- Spring Framework
- Java Programming
- SQL Databases
- REST API Development
- Message Queuing (Kafka)
- Build Tools (Maven/Gradle)
- Backend Microservice Architecture

### Usage

1. Clone the repository:

```bash
git clone https://github.com/Ahmedkhan78/Midas_Core.git
```
