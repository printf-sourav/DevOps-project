# Assignment coverage

Source: the five-page DevOps assignment for course 25CIAEC59 / 25CYAEC59.

| Requirement | Implementation / evidence |
| --- | --- |
| One small API, 2-3 endpoints, no database | `QuoteController.java`: `/quotes`, `/quotes/random`, `/health` |
| Run locally and request the API | Packaged JAR checked with HTTP requests; commands in `docs/setup.md` |
| GitHub repository | `repo_link.txt` and repository links in both reports |
| Feature branch and merge into main | Retained `feature/quote-pipeline`, `main`, and a non-fast-forward merge commit |
| Maven/Gradle build and working artifact | `pom.xml`, `mvn clean package`, executable `target/quote-api.jar` |
| Jenkins SCM configuration | Pipeline from SCM; repository and branch settings in `docs/setup.md` |
| Automatic CI trigger | `pollSCM('* * * * *')` in `Jenkinsfile` |
| Build and artifact archive | Build stage and `archiveArtifacts` with fingerprint |
| CI enhancement | Three JUnit tests run in a Test stage before Build |
| Ansible inventory with a target host | `ansible/inventory`: local Ubuntu/WSL host |
| Install runtime, copy artifact, start service | `ansible/roles/quote_api/tasks/main.yml` |
| Variable, handler and role | `app_port` and other defaults; restart handler; `quote_api` role initialized with `ansible-galaxy role init` |
| Successful Ansible execution | Initial run succeeded; unchanged second run reported `changed=0`, `failed=0` |
| Docker image build and local run | `Dockerfile`; image built and HTTP responses checked |
| Local Kubernetes cluster | Dedicated Kind cluster named `quote-demo` |
| Deployment initially has 3 replicas | `k8s/deployment.yaml` |
| Service and matching selectors | `k8s/service.yaml`, `app: quote-api`, named HTTP port |
| Reach app through Service | Port-forward Service to localhost:8083, then curl |
| Scale 3 -> 5 -> 3 | `scripts/scale-demo.sh`, waits for each rollout |
| Deployment enhancement | Startup, readiness and liveness HTTP probes on `/health` |
| Separate reports for both components | `reports/component-1.md`, `reports/component-2.md` and their PDFs |
| Required report sections | Cover, draft certificate, Abstract, Introduction, Implementation, Enhancement, Results, Conclusion, References |
| Screenshots and recorded outputs | `reports/evidence/`; the reports identify the evidence and its capture status |
| Source code folder | This repository contains all application, test, pipeline and deployment source |
| Repository link inside reports and text file | Included in both reports and `repo_link.txt` |
| Coordinator collaborator | Pending coordinator's GitHub username; invite through repository Settings |
| Official cover/certificate format | Pending the separate institutional template; draft certificate is unsigned |
| One resume line per component | Included in each report; use after the demonstrated stages are complete |

Only one CI enhancement and one deployment enhancement are required. This project chooses automated tests and health probes. It does not add a second microservice, database, paid cloud infrastructure or autoscaling.
