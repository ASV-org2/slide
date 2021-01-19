pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo 'Starting build step'
        sh './gradlew build'
        echo 'Build step complete'
      }
    }

    stage('Test') {
      steps {
        echo 'Starting test step'
        sh './gradlew test'
        echo 'Test step complete'
      }
    }

    stage('Sonarqube') {
      steps {
        echo 'Start Sonarqube step'
        sh '''./gradlew sonarqube \\
  -Dsonar.projectKey=slide \\
  -Dsonar.host.url=http://localhost:9000 \\
  -Dsonar.login=4a1120c2ad29e561e8e5fe473801f0a6ad2accfe'''
        echo 'Sonarqube step complete'
      }
    }

  }
}