pipeline {
    agent any

    environment {
        HOST_IP = '172.17.0.1'
        DOCKER_COMPOSE_FILE = 'docker-compose.win-app-kafka-jenkins.yml'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'dev', url: 'https://github.com/oskarvos/city-weather-app.git'
            }
        }

        stage('Build weather-app') {
            steps {
                echo '🔨 Собираем weather-app...'
                sh 'mvn clean compile'
            }
        }

        stage('Build weather-consumer') {
            steps {
                echo '🔨 Собираем weather-consumer...'
                dir('weather-consumer') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Test weather-app') {
            steps {
                echo '🧪 Тестируем weather-app...'
                sh """
                    mvn test \
                      -Dspring.datasource.url=jdbc:postgresql://${HOST_IP}:5432/weather_db \
                      -Dspring.datasource.username=postgres \
                      -Dspring.datasource.password=2223399z
                """
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Test weather-consumer') {
            steps {
                echo '🧪 Тестируем weather-consumer...'
                dir('weather-consumer') {
                    sh """
                        mvn test \
                          -Dspring.datasource.url=jdbc:postgresql://${HOST_IP}:5432/weather_logs_db \
                          -Dspring.datasource.username=postgres \
                          -Dspring.datasource.password=2223399z
                    """
                }
            }
            post {
                always {
                    junit 'weather-consumer/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo '📦 Упаковываем weather-app...'
                sh 'mvn package -DskipTests'

                echo '📦 Упаковываем weather-consumer...'
                dir('weather-consumer') {
                    sh 'mvn package -DskipTests'
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                echo "🐳 Собираем Docker образы..."
                sh "docker compose -f ${DOCKER_COMPOSE_FILE} build"
            }
        }

        stage('Deploy') {
            steps {
                echo "🚀 Развертываем контейнеры..."
                sh "docker compose -f ${DOCKER_COMPOSE_FILE} down"
                sh "docker compose -f ${DOCKER_COMPOSE_FILE} up -d"
            }
        }

        stage('Health Check') {
            steps {
                echo '🏥 Проверяем работоспособность...'
                script {
                    sleep time: 30, unit: 'SECONDS'

                    def containers = ['zookeeper', 'kafka', 'weather-app', 'weather-consumer']

                    containers.each { container ->
                        def status = sh(script: "docker ps --filter name=${container} --format '{{.Status}}'", returnStdout: true).trim()
                        if (status.contains('Up')) {
                            echo "✅ ${container} работает"
                        } else {
                            error "❌ ${container} не запущен!"
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo '✅ Пайплайн выполнен успешно!'
        }
        failure {
            echo '❌ Пайплайн завершился с ошибкой!'
        }
        always {
            cleanWs()
        }
    }
}