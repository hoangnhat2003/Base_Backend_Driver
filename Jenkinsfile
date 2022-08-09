pipeline {
    agent any
    tools {
        maven '3.6.3'
    }

    environment {
    HEROKU_API_KEY = credentials('heroku-api-key')
    }

    parameters {
        string(name: 'APP_NAME', defaultValue: 'java-app-jenkins', description: 'Heroku app name?')
    }

    stages {
        stage('Clean and Install'){
            steps {
              sh 'mvn clean install'
            }
        }
        stage('Build Docker image'){
            steps {
              sh 'docker build -t  nhathoang/java-web-app:lastest .'
            }
        }
        stage('Login Heroku Registry'){
            steps {
              sh 'docker login --username=_ --password=${HEROKU_API_KEY} registry.heroku.com'
            }
        }
        stage('Push to Heroku registry'){
            steps {
              sh 'docker tag nhathoang/java-web-app:lastest registry.heroku.com/$APP_NAME/web'
              sh 'docker push registry.heroku.com/$APP_NAME/web'
            }
        }
        stage('Release the image') {
             steps {
                sh 'heroku container:release web --app=$APP_NAME'
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