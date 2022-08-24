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
        stage('Login to Docker Hub'){
            steps {
              sh 'docker-compose up -d --build'
            }
        }
        stage('Push image to Docker Hub'){
            steps {
               sh 'docker-compose up -d --build'
            }
        }
        stage ('Deploy') {
            steps {
               sh 'scp docker-compose.dev.yml ${REMOTE_USER}@${REMOTE_HOST}:~/'
               sh 'ssh ${REMOTE_USER}@${REMOTE_HOST} ./deploy.ssh'
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