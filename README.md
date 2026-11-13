# Quote API DevOps Project

A small Java REST API taken through Git, Maven, Jenkins, Ansible, Docker and Kubernetes.

Author: Sourav Kumar Sahu  
USN: 1MS24CY063  
Course: DevOps (25CIAEC59 / 25CYAEC59)

Repository: https://github.com/printf-sourav/DevOps-project

## Run locally

```sh
mvn clean package
java -jar target/quote-api.jar
```

| Route | Response |
| --- | --- |
| `GET /quotes` | Three quotes as JSON |
| `GET /quotes/random` | One random quote |
| `GET /health` | `{"status":"UP"}` |

The app uses Java 17, Spring Boot and an in-memory list. It has no database or frontend.

## Pipeline

`Git -> Jenkins tests -> Maven JAR -> Ansible service / Docker -> Kubernetes -> scale 3 to 5 to 3`

- **Component 1:** Maven build, Jenkins SCM polling, JUnit test stage and archived JAR.
- **Component 2:** Ansible role with variables and restart handler, Docker image, Kubernetes Deployment and Service, manual scaling, health probes and ConfigMap settings.

Follow [the setup and demo guide](docs/setup.md) for Jenkins, Ansible, Docker and Kubernetes commands. See [the requirements checklist](docs/requirements.md) for coverage.

The completed [institutional report](reports/CY_devops_report_Sourav_Kumar_Sahu.pdf) includes both components in the supplied college format, with the actual code and evidence. Its editable [LaTeX source](reports/overleaf/main.tex) is ready for Overleaf. Signatures and evaluator marks remain blank.
