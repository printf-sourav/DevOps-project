pipeline {
    agent any
    options {
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    triggers { pollSCM('* * * * *') }
    stages {
        stage('Test') {
            steps {
                script {
                    if (isUnix()) { sh 'mvn -B clean test' }
                    else { bat 'mvn -B clean test' }
                }
            }
            post { always { junit 'target/surefire-reports/*.xml' } }
        }
        stage('Build') {
            steps {
                script {
                    if (isUnix()) { sh 'mvn -B -DskipTests package' }
                    else { bat 'mvn -B -DskipTests package' }
                }
                archiveArtifacts artifacts: 'target/quote-api.jar', fingerprint: true
            }
        }
    }
}
