pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = "vivek031/auth-service"
        DOCKER_TAG   = "1.0.0"
    }

    stages {
        stage('Build & Tag Docker Image') {
            steps {
                script {
					sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(
						credentialsId: 'docker-cred',
						usernameVariable: 'DOCKER_USER',
						passwordVariable: 'DOCKER_PASS'
					)]) {
                        sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
                        sh 'docker push ${DOCKER_IMAGE}:${DOCKER_TAG}'
                    }
                }
            }
        }
    }
}
