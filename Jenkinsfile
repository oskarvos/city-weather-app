pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven'
    }

    environment {
        HOST_IP = '172.17.0.1'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'dev', url: 'https://github.com/oskarvos/city-weather-app.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh """
                    mvn test \
                      -Dspring.datasource.url=jdbc:postgresql://${HOST_IP}:5432/weather_db \
                      -Dspring.datasource.username=postgres \
                      -Dspring.datasource.password=2223399z
                """
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        success {
            echo '✅ Сборка успешно завершена!'
        }
        failure {
            echo '❌ Сборка упала. Проверьте логи.'
        }
    }
}