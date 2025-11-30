# Conference Ticket Booking System Evolution

A hands-on journey through software design: from monolith mess to reactive mastery.

## Overview

Rebuilding a concurrent ticket booking system to master domain-driven design, clean architecture, and paradigm shifts (OOP → Functional → Reactive).

## Phases

- **Phase 1: Classic Spring Boot Monolith** (Current) – Synchronous, blocking, "realistic" enterprise start. Expect race conditions and N+1 pain.
- **Phase 2: Vert.x Reactive** – Non-blocking port of the _exact same domain_.
- **Phase 3: Wildcard** – Quarkus? Helidon? Your call.

## Local Setup

- Java 21+
- Maven (or Gradle)
- `mvn spring-boot:run`

## Commits to Watch

- `legacy-bad-design`: The painful baseline.
- `refactor-rich-domain`: Entities with behavior.
- `phase-2-vertx`: Reactive glory.

Built with ❤️ for software design mastery. Contributions via PRs.
