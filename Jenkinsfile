pipeline {
    agent any
    tools {
        maven '3.6.3'
    }

    stages {
        stage('Clean and Install'){
            steps {
              sh 'mvn clean install'
            }
        }
        stage('Build Images'){
            steps {
              sh 'docker-compose -d --build'
            }
        }
        stage('Docker-Compose Up'){
            steps {
              sh 'docker-compose up -d'
            }
        }
    }

    post {
        success {
          echo "SUCCESSFUL"
        }
        failure {
          echo "FAILED"
        }
      }
}