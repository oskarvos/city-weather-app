pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven'
    }

    environment {
        // IP хоста изнутри контейнера Jenkins (стандартный для Docker)
        HOST_IP = '172.17.0.1'
        // Параметры подключения к БД
        DB_URL = "jdbc:postgresql://${HOST_IP}:5432/weather_db"
        DB_USER = 'postgres'
        DB_PASS = '2223399z'
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
                      -Dspring.datasource.url=${DB_URL} \
                      -Dspring.datasource.username=${DB_USER} \
                      -Dspring.datasource.password=${DB_PASS}
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