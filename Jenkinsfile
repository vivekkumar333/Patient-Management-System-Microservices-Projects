pipeline {
    agent any

    environment {
        DOCKER_IMAGES_TAG = "1.0.0"
        DOCKER_USER = "vivek031"
    }

    stages {
        stage('Pull Docker Images') {
            steps {
                script {
                    sh "docker pull ${DOCKER_USER}/gateway-service:${DOCKER_IMAGES_TAG}"
                    sh "docker pull ${DOCKER_USER}/auth-service:${DOCKER_IMAGES_TAG}"
                    sh "docker pull ${DOCKER_USER}/patient-service:${DOCKER_IMAGES_TAG}"
                    sh "docker pull ${DOCKER_USER}/billing-service:${DOCKER_IMAGES_TAG}"
                    sh "docker pull ${DOCKER_USER}/notification-service:${DOCKER_IMAGES_TAG}"
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    sh 'docker-compose -f docker-compose-pms.yml up -d'
					sleep 60
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    sh 'docker ps'   // Check containers are running
                    //sh 'curl -f http://localhost:8080/actuator/health || exit 1'
                }
            }
        }
    }
}