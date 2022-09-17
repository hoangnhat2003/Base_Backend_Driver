pipeline {
    agent {label 'slave1'}
    tools {
        maven '3.6.3'
    }
    environment {
       DOCKERHUB_CREDENTIALS=credentials('dockerhub')
    }

    stages {
//         stage('SCM Checkout'){
//              git branch: 'develop', credentialsId: 'git_credentials', url: 'https://github.com/hoangnhat2003/Base_Backend_Drivor'
//         }
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
        stage ('Run Docker on Dev Server') {
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
