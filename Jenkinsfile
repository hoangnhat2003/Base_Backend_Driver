pipeline {
    agent any
    tools {
        maven '3.6.3'
    }

    environment {
       DOCKERHUB_CREDENTIALS=credentials('dockerhub')
    }

    stages {
        stage('Clean and Install'){
            steps {
              sh 'mvn clean install'
            }
        }
        stage('Build Docker Image'){
            steps {
              sh 'docker build -t nhathoang07/booking-backend:v1 .'
            }
        }
        stage('Docker Push') {
            steps {
              sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
              sh 'docker push nhathoang07/booking-backend:v1'
           }
        }
        stage ('Deploy') {
            steps {
              sh 'docker-compose -f docker-compose.dev.yml down'
              sh 'docker-compose -f docker-compose.dev.yml up -d --build'
              sh 'docker image prune'
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