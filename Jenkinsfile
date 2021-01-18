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
        withSonarQubeEnv 'sonarqube'
        sh '''./gradlew sonarqube \\
  -Dsonar.projectKey=slide \\
  -Dsonar.host.url=http://localhost:9000 \\
  -Dsonar.login=73390eca9a8bc164a4dd1e429a42089d54647293'''
        echo 'Sonarqube step complete'
      }
    }

  }
}