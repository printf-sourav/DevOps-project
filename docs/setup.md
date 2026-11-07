# Setup and demonstration

This project uses one application throughout. Build the JAR once, let Jenkins test and archive it, then deploy that same JAR with Ansible and Docker/Kubernetes. No cloud account is needed.

## 1. Tools

- Git, JDK 17 and Maven 3.6.3 or newer for the application.
- Jenkins LTS with its supported Java runtime (Java 21 for the version used here).
- Jenkins plugins: Pipeline, Git, JUnit and Timestamper.
- Docker Engine or Docker Desktop in Linux-container mode.
- Kind and kubectl for a local Kubernetes cluster.
- Ubuntu with systemd and Ansible Core for the server demonstration. On Windows, use Ubuntu in WSL2. Run Ansible from Linux, not native PowerShell.

The app needs very little memory, but five JVM pods plus Jenkins and Kubernetes need several GB available to WSL/Docker. Close other heavy applications if the cluster runs out of memory.

## 2. Run the app first

```sh
git clone https://github.com/printf-sourav/DevOps-project.git
cd DevOps-project
mvn clean package
java -jar target/quote-api.jar
```

In a second terminal:

```sh
curl http://localhost:8080/health
curl http://localhost:8080/quotes
curl http://localhost:8080/quotes/random
```

`/health` returns `{"status":"UP"}`. `/quotes` returns three fixed quote objects. `/quotes/random` returns one object from that list. Set `APP_PORT` to change the port. There is no database, login or frontend.

## 3. Git history

The repository keeps `main` and `feature/quote-pipeline`. The feature branch contains the application and pipeline work; a merge commit joins it into main. To view the actual history:

```sh
git fetch --all
git branch -a
git log --graph --oneline --all
```

## 4. Jenkins

1. Install Jenkins LTS locally, keep authentication enabled, and install Pipeline, Git, JUnit and Timestamper plugins. Its build agent must have Git, JDK 17+ and Maven on PATH. Jenkins itself needs the Java version supported by its release.
2. Create a **Pipeline** job named `quote-api`. Choose **Pipeline script from SCM**, select Git, and enter `https://github.com/printf-sourav/DevOps-project.git`.
3. Set the branch specifier to `*/main` and the script path to `Jenkinsfile`.
4. Run **Build Now** once to load the Jenkinsfile. This initial run registers the SCM polling trigger.
5. Open Configure and confirm **Poll SCM** is set to `* * * * *` (every minute).
6. The Test stage runs `mvn -B clean test` and publishes the three JUnit results. Build packages the already-tested code with `mvn -B -DskipTests package` and archives `target/quote-api.jar` with a fingerprint. Test failure prevents Build from running.
7. Make a small real change, commit it, and push to main. Wait for polling; do not click Build Now for this second run. Its cause should say **Started by an SCM change**.
8. Capture job configuration, successful console output, test results and the archived JAR for Component 1.

SCM polling works for a local Jenkins instance without exposing it publicly or setting up a webhook tunnel. Keep the same repo and branch selected in Jenkins that you are pushing to.

## 5. Ansible server setup

Use Ubuntu with systemd. The supplied inventory targets the Linux machine where Ansible runs. The application service uses port 8081 so it can run alongside a local Jenkins or application instance.

```sh
sudo apt-get update
sudo apt-get install -y ansible-core
mvn clean package
cd ansible
ansible-playbook -i inventory playbook.yml --ask-become-pass
curl http://localhost:8081/health
ansible-playbook -i inventory playbook.yml --ask-become-pass
```

If already running as root, omit `--ask-become-pass`. The second run should show `changed=0` and `failed=0` when the artifact and configuration are unchanged. The role installs Java 17, creates an unprivileged service account, copies the JAR, installs a systemd service, and waits for a healthy HTTP response. Copy/template changes notify the restart handler. Defaults in `roles/quote_api/defaults/main.yml` include `app_port`, `app_user` and `app_directory`.

The role was initialized using `ansible-galaxy role init quote_api`; only the directories used by this small role are retained. Do not rerun role initialization over the finished role.

For a remote Ubuntu VM, create an untracked `inventory.local`:

```ini
[app_servers]
my-server ansible_host=YOUR_VM_IP ansible_user=YOUR_LINUX_USER
```

Then use `ansible-playbook -i inventory.local playbook.yml --ask-become-pass`. Keep SSH keys outside the repository. The JAR is read from the control machine's `target/` folder.

## 6. Test Docker before Kubernetes

From the repository root:

```sh
docker build -t quote-api:1.0 .
docker run --rm --name quote-api -p 127.0.0.1:8082:8080 quote-api:1.0
```

In another terminal, run `curl http://localhost:8082/health`. Stop the container with Ctrl+C when done. The runtime image contains Java 17 and the built JAR, runs as UID 10001, and has no Maven installation.

## 7. Kubernetes deployment and scaling

Run these commands in Bash with Docker, Kind and kubectl available:

```sh
kind create cluster --name quote-demo
bash scripts/deploy.sh
kubectl --context kind-quote-demo port-forward service/quote-api 8083:8080
```

In another terminal:

```sh
curl http://localhost:8083/health
curl http://localhost:8083/quotes/random
bash scripts/scale-demo.sh
kubectl --context kind-quote-demo get services
kubectl --context kind-quote-demo describe deployment quote-api
```

The Deployment starts with three replicas. The scale script shows three, then five, then three replicas and waits for each rollout. The ClusterIP Service selects `app: quote-api` and routes port 8080 to the named container port `http`. Port forwarding makes that service reachable locally. Startup, readiness and liveness probes request `/health`; readiness controls traffic and liveness restarts an unhealthy container.

After rebuilding the same image tag, reload it and restart the deployment so running pods use the new image:

```sh
kind load docker-image quote-api:1.0 --name quote-demo
kubectl --context kind-quote-demo rollout restart deployment/quote-api
kubectl --context kind-quote-demo rollout status deployment/quote-api --timeout=180s
```

These scripts explicitly use the dedicated `kind-quote-demo` context. Manual scaling is the required demonstration; this project does not claim autoscaling.

## 8. Reports and submission

The two reports are under `reports/`. Each has its own Abstract, Introduction, Implementation, Enhancement, Results, Conclusion and References. Keep the PDF reports, source folder and `repo_link.txt` together for submission. The report sources are editable Markdown files.

Use the real evidence in `reports/evidence/`, and capture the Jenkins UI pages listed above. The university's official title/certificate template was not supplied with the assignment. Replace the draft cover/certificate with that template before submission and obtain the required signatures. Add the course coordinator through GitHub **Settings > Collaborators** once their GitHub username is available; never guess the account.

## 9. Stop the local demo

Stop port forwarding with Ctrl+C. To remove only this project's cluster and stop its Ansible service:

```sh
kind delete cluster --name quote-demo
sudo systemctl stop quote-api
```

Do not delete the cluster before capturing scaling and health-probe evidence.
