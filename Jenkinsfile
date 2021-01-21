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
  -Dsonar.host.url=http://localhost:9000'''
        echo 'Sonarqube step complete'
      }
    }

  }
}