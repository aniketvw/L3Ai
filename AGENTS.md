# AGENTS.md

AI-Augmented Incident Analysis & RCA Platform

## Project Overview

This repository is part of a production-grade, multi-repo system
designed to simulate a real-world AI-augmented Incident Analysis and
Root Cause Analysis (RCA) platform for a high-throughput financial
domain (Spot FX trading).

The primary objective is to:

-   Ingest real-time trading and system logs
-   Detect anomalies and incidents
-   Perform AI-assisted root cause analysis
-   Provide structured, explainable incident summaries
-   Maintain enterprise-grade observability, resilience, and scalability

This project is intentionally architected to reflect enterprise backend
platform engineering standards.

------------------------------------------------------------------------

## System Context

The platform simulates a distributed FX trading environment with:

-   Real-time trade ingestion
-   Order processing pipelines
-   Downstream settlement simulations
-   Infrastructure and application log streams

Incidents may include:

-   Trade mismatches
-   Latency spikes
-   Downstream system failures
-   Message loss or duplication
-   Schema evolution issues
-   Infrastructure resource exhaustion

The AI layer assists in:

-   Log summarization
-   Correlating events across services
-   Hypothesis generation for RCA
-   Structured incident reporting

------------------------------------------------------------------------

## Design Principles

Agents contributing to this repository must follow:

-   Production-grade standards (no shortcuts)
-   Event-driven thinking
-   Resilience-first design
-   Observability by default
-   Deterministic AI interfaces

------------------------------------------------------------------------

## AI Constraints

The AI subsystem must:

1.  Operate on structured log context.
2.  Use retrieval over historical incidents.
3.  Generate structured RCA reports.
4.  Maintain explainability via citation of log references.

Free-form hallucinated analysis is unacceptable.

------------------------------------------------------------------------

## Agent Governance Law

**Law 1 --- Human-Led Implementation**

The primary implementation responsibility belongs to the human
developer.

AI agents must: - Generate code only when explicitly requested. -
Default to architectural guidance, analysis, or review. - Avoid
unsolicited code generation. - Prefer explanation over automation.

Automation must not replace deliberate human implementation.

------------------------------------------------------------------------

## Architectural Philosophy

This platform simulates a real-world, distributed financial backend system with AI-assisted incident analysis. The architecture prioritizes:

-   Event-driven communication
-   Deterministic AI integration
-   Production-grade observability
-   Horizontal scalability
-   Clear service boundaries
-   Explicit domain modelling

The system is intentionally designed to reflect enterprise backend standards rather than academic or demo implementations.

------------------------------------------------------------------------

## Core Layers

The platform follows a microservices + event-driven + AI orchestration model.

1.  Event Ingestion Layer
2.  Domain Processing Layer
3.  Incident Detection Layer
4.  AI-Augmented RCA Layer
5.  Persistence Layer
6.  Observability Layer
7.  Presentation Layer (optional)

All cross-service communication is asynchronous unless explicitly justified.

------------------------------------------------------------------------

## AI Architecture (Spring AI)

The system uses Spring AI for:

-   LLM client abstraction
-   Prompt templating
-   Embedding generation
-   Vector store interaction
-   Structured output mapping

## High-Level Component View

                 ┌────────────────────────────┐
                 │     Trade Simulator        │
                 └────────────┬───────────────┘
                              │
                        (Kafka Topics)
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
    Order Processor     Settlement Simulator     Log Aggregator
        │                     │                     │
        └─────────────── Incident Events ───────────┘
                              │
                Incident Detection Service
                              │
                AI-RCA Orchestration Service
                              │
                ┌─────────────┴──────────────┐
                │                            │
            Vector Store                 Incident DB
                │                            │
                └──────── Dashboard/API ─────┘

## Core Services

1. Trade Simulation Service

Generates synthetic FX spot trade events.

Produces high-throughput Kafka messages.

Simulates edge cases (delays, mismatches, partial failures).

2. Order Processing Service

Consumes trade events.

Validates, enriches, routes.

Emits structured logs and metrics.

Produces downstream settlement events.

3. Settlement Service

Simulates clearing and settlement.

Injects controlled failure patterns for testing.

4. Log Aggregator

Centralizes structured logs.

Ensures logs are schema-consistent.

Forwards logs to persistence and AI ingestion pipeline.

5. Incident Detection Service

Correlates anomalies.

Detects threshold breaches.

Emits IncidentDetectedEvent.

Detection mechanisms may include:

Rule-based anomaly detection

Statistical deviation

Pattern matching

6. AI-RCA Orchestration Service

This is the intelligence layer.

Responsibilities:

Retrieve relevant logs

Query vector store

Build RAG context

Call LLM via Spring AI

Enforce structured output schema

Persist RCA report

### Structured RCA Output Schema

``` json
{
  "incident_id": "",
  "summary": "",
  "probable_root_cause": "",
  "supporting_evidence": [],
  "confidence_score": 0.0,
  "recommended_remediation": ""
}
```

All fields required and validated before persistence.

------------------------------------------------------------------------

## Observability (OpenTelemetry)

Every service must:

-   Emit distributed traces
-   Attach correlation IDs
-   Emit structured metrics
-   Use context propagation across Kafka

Instrumentation includes:

- HTTP server spans
- Kafka producer/consumer spans
- DB spans
- AI invocation spans

AI calls must be traced with:

- Model name
- Token usage
- Latency
- Outcome

------------------------------------------------------------------------

## Persistence

-   Relational DB for incidents and RCA reports
-   MongoDB for semi-structured logs, log clusters, Aggregated event documents
-   Vector store for embeddings, similarity search

Index tuning must support high read throughput.

------------------------------------------------------------------------

## Messaging

Apache Kafka is the event backbone with:

-   Idempotent consumers
-   Dead-letter handling
-   Retry with backoff
-   Schema evolution strategy
-   Partition-aware producers
-   Idempotent consumers

------------------------------------------------------------------------

## Resilience

Required patterns:

-   Circuit breakers
-   Retry policies
-   Bulkhead isolation
-   Idempotent event handling
-   Timeout enforcement

AI layer must degrade gracefully if LLM is unavailable.

## AI Architecture
The system uses Spring AI for:

LLM client abstraction

Prompt templating

Embedding generation

Vector store interaction

Structured output mapping

### AI Flow

Incident detected.

Relevant logs retrieved from DB.

Historical incidents retrieved via vector similarity.

RAG context constructed.

LLM invoked via Spring AI.

Response validated against strict JSON schema.

RCA report persisted.

AI outputs must be:

Deterministic in structure

Validated before storage

Traceable to evidence sources

Free-form responses are not acceptable.

### Retrieval-Augmented Generation (RAG)

RAG pipeline consists of:

Embedding generator

Vector store index

Similarity search

Context window construction

Prompt template injection

Vector store design:

Embeddings for historical incidents

Embeddings for enriched log clusters

Indexed by timestamp, service, severity

## Logging Standards

All logs must be:

- JSON structured

- Machine-parsable

- Correlated with trace ID

- Severity classified

Never log raw AI prompts or sensitive data without sanitization.

## Security Architecture

JWT-based authentication for APIs

Role-based authorization

Secure storage of AI credentials

No secrets in code

Audit logging for RCA generation

## Deployment Architecture

Containerized services.

Compatible with:

Kubernetes

OpenShift

Environment profiles:

Local (minimal)

Cloud (Azure AI enabled)

Production simulation

## Domain Modeling Principles

Explicit domain objects

No anemic models

Domain invariants enforced in aggregate roots

Clear separation between:

Domain logic

Infrastructure

AI orchestration

## Non-Goals

This system is not:

A chatbot

A monolith

A toy microservice demo

A loosely structured AI experiment

It is a disciplined backend + AI platform simulation.

## Architectural Priorities (In Order)

Determinism

Observability

Scalability

Domain clarity

AI explainability

Performance optimization