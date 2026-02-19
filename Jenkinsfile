pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven'
    }

    environment {
        POSTGRES_CONTAINER = 'postgres-test'
        HOST_IP = '172.17.0.1'          // фиксированный IP хоста
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'dev', url: 'https://github.com/oskarvos/city-weather-app.git'
            }
        }

        stage('Prepare Test Database') {
            steps {
                script {
                    // Удаляем старый контейнер, если остался
                    sh """
                        docker stop ${POSTGRES_CONTAINER} || true
                        docker rm ${POSTGRES_CONTAINER} || true
                    """

                    // Запускаем PostgreSQL на порту 5433
                    sh """
                        docker run -d --name ${POSTGRES_CONTAINER} \
                          -e POSTGRES_PASSWORD=postgres \
                          -e POSTGRES_USER=postgres \
                          -e POSTGRES_DB=testdb \
                          -p 5433:5432 \
                          postgres:15
                    """

                    // Ждём готовности
                    sh """
                        timeout 30s sh -c 'until docker exec ${POSTGRES_CONTAINER} pg_isready; do sleep 2; done'
                    """
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                script {
                    // Подключаемся к БД на порту 5433
                    sh """
                        mvn test \
                          -Dspring.datasource.url=jdbc:postgresql://${HOST_IP}:5433/testdb \
                          -Dspring.datasource.username=postgres \
                          -Dspring.datasource.password=postgres
                    """
                }
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
        always {
            sh """
                docker stop ${POSTGRES_CONTAINER} || true
                docker rm ${POSTGRES_CONTAINER} || true
            """
        }
        success {
            echo '✅ Сборка успешно завершена!'
        }
        failure {
            echo '❌ Сборка упала. Проверьте логи.'
        }
    }
}