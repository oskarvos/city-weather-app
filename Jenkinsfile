pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven'
    }

    environment {
        POSTGRES_CONTAINER = 'postgres-test'
        HOST_IP = ''
    }

    stages {
        stage('Checkout') {
            steps {
                // Укажите вашу ветку (dev)
                git branch: 'dev', url: 'https://github.com/oskarvos/city-weather-app.git'
            }
        }

        stage('Prepare Test Database') {
            steps {
                script {
                    // Получаем IP хоста (адрес, по которому Jenkins-контейнер видит хост)
                    HOST_IP = sh(script: "ip route | awk '/default/ { print \$3 }'", returnStdout: true).trim()
                    echo "Host IP: ${HOST_IP}"

                    // Удаляем старый контейнер, если остался
                    sh """
                        docker stop ${POSTGRES_CONTAINER} || true
                        docker rm ${POSTGRES_CONTAINER} || true
                    """

                    // Запускаем PostgreSQL
                    sh """
                        docker run -d --name ${POSTGRES_CONTAINER} \
                          -e POSTGRES_PASSWORD=postgres \
                          -e POSTGRES_USER=postgres \
                          -e POSTGRES_DB=testdb \
                          -p 5432:5432 \
                          postgres:15
                    """

                    // Ждём, пока база будет готова
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
                    // Запускаем тесты, указывая подключение к БД через системные свойства
                    sh """
                        mvn test \
                          -Dspring.datasource.url=jdbc:postgresql://${HOST_IP}:5432/testdb \
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
            // Останавливаем и удаляем контейнер с PostgreSQL
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