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
        stage('Docker-Compose Up'){
            steps {
              sh 'docker-compose up -d --build'
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