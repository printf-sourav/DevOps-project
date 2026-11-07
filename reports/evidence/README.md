# Recorded evidence

Captured on 7 November 2026 for Sourav Kumar Sahu, USN 1MS24CY063.

- `git-history.txt` and `.png`: retained main/feature branches and the merge graph.
- `maven-build.txt` and `.png`: successful local build and three passing tests.
- `jenkins-job.xml`: actual SCM job configuration, including its repository, branch, Jenkinsfile and polling trigger.
- `jenkins-build.txt`: actual successful Jenkins console output.
- `jenkins-result.json`: build #3 result, SCM trigger cause, archived JAR and JUnit result data.
- `jenkins-*.png`: screenshots of the actual local Jenkins UI, captured through a localhost-only connection.
- `ansible.txt` and `.png`: deployment of the final JAR, then an unchanged repeat run with zero changes.
- `docker.txt` and `.png`: image build and successful HTTP smoke check.
- `kubernetes.txt`, `kubernetes-scaling.png`, `kubernetes-health.png`: successful rollouts, 3 -> 5 -> 3 replicas, Service responses and live probe configuration.

The command-output PNGs are readable renderings of excerpts from the accompanying real logs. They are labelled as recorded output. The complete text files retain startup retries and build details rather than hiding them. The Jenkins PNGs are direct browser screenshots.

The reports' certificate pages are drafts awaiting the separate institutional template and faculty signatures. No signature or certification has been fabricated.
