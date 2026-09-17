# DocSearch

DocSearch is a Spring Boot REST API that stores text documents in PostgreSQL and ranks search results using TF-IDF. It is an educational software-engineering project designed to grow into a distributed document-search platform.

## Current features

- Upload UTF-8 `.txt` documents
- List and delete documents
- Tokenize and normalize document text
- Rank multi-keyword search results with TF-IDF
- Return a preview around the first matching term
- Persist data in PostgreSQL
- Validate requests and return structured errors
- Unit-test text processing

## Technology stack

- Java 17
- Spring Boot 3
- Spring Web and Spring Data JPA
- PostgreSQL
- Maven
- JUnit 5
- Docker Compose

## Architecture

```text
HTTP request -> Controller -> Service -> Repository -> PostgreSQL
                              |
                              +-> Text processing and TF-IDF ranking
```

Controllers only handle HTTP input and output. Services implement application rules and search logic. The repository provides database access through Spring Data JPA.

## Run locally

Requirements: Java 17, Maven and Docker Desktop.

1. Start PostgreSQL:

```bash
docker compose up -d
```

2. Run the API:

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

## Try the API

Upload a document:

```bash
curl -F "file=@example.txt" http://localhost:8080/api/documents
```

List documents:

```bash
curl http://localhost:8080/api/documents
```

Search:

```bash
curl "http://localhost:8080/api/search?query=machine%20learning"
```

Delete document 1:

```bash
curl -X DELETE http://localhost:8080/api/documents/1
```

## How TF-IDF ranking works

For each query term, DocSearch calculates:

```text
TF  = occurrences of the term / total words in the document
IDF = log((number of documents + 1) / (documents containing the term + 1)) + 1
score = sum(TF * IDF) for all query terms
```

Documents with the highest scores are returned first.

## Planned milestones

- Store a persistent inverted index rather than scanning all documents
- Add PDF extraction
- Add users and JWT authentication
- Cache frequent queries with Redis
- Add integration tests and GitHub Actions
- Measure indexing time, query latency and throughput
- Deploy the API to AWS

## API endpoints

| Method | Endpoint | Purpose |
| --- | --- | --- |
| POST | `/api/documents` | Upload a `.txt` file as multipart field `file` |
| GET | `/api/documents` | List stored documents |
| DELETE | `/api/documents/{id}` | Delete one document |
| GET | `/api/search?query=...` | Search and rank documents |
