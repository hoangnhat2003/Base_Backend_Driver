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
        stage('Build Docker Image'){
            steps {
              sh 'docker build -t  nhathoang07/booking-backend:v1 .'
            }
        }
        stage('Docker Push') {
            agent any
            steps {
              withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                sh 'docker push nhathoang07/booking-backend:v1'
              }
           }
        }
        stage ('Deploy') {
            steps {
               sh 'sftp -i "webserver.pem" ubuntu@ip'
               sh 'put docker-compose.dev.yml'
               sh 'ssh -i "webserver.pem" ubuntu@ip'
               sh 'docker-compose -f docker-compose.dev.yml up -d --build'
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