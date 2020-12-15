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

  }
}