
# Project Details

## Setup Containers

All done inside main project folder.

```bash
mvn clean package -DskipTests
```

```bash
podman build -t users -f Dockerfile.dockerfile
```

```bash
podman network create mynetwork
```

```bash
podman run --name postgresdb --network mynetwork -e POSTGRES_DB=postgres -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -p 6432:5432  postgres:17.4-alpine
```

```bash
podman run --name users --network mynetwork -p 8090:8080 users
```