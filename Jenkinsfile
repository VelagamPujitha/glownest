pipeline {

    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    environment {

        APP_NAME = "glownest"

        AWS_REGION = "us-east-1"

        AWS_ACCOUNT_ID = "123456789012"

        ECR_REPO = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${APP_NAME}"

        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {

            steps {

                checkout scm

            }

        }

        stage('Build') {

            steps {

                sh 'mvn clean compile'

            }

        }

        stage('Unit Test') {

            steps {

                sh 'mvn test'

            }

        }

        stage('Package') {

            steps {

                sh 'mvn clean package -DskipTests'

            }

        }

        stage('Sonar Scan') {

            steps {

                echo 'Running SonarQube Scan'

            }

        }

        stage('Trivy FS Scan') {

            steps {

                sh 'trivy fs .'

            }

        }

        stage('Docker Build') {

            steps {

                sh '''
                docker build \
                -t ${APP_NAME}:${IMAGE_TAG} .
                '''
            }

        }

        stage('Docker Image Scan') {

            steps {

                sh '''
                trivy image ${APP_NAME}:${IMAGE_TAG}
                '''
            }

        }

        stage('Login ECR') {

            steps {

                sh '''
                aws ecr get-login-password \
                --region ${AWS_REGION} \
                | docker login \
                --username AWS \
                --password-stdin \
                ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
                '''
            }

        }

        stage('Push To ECR') {

            steps {

                sh '''
                docker tag \
                ${APP_NAME}:${IMAGE_TAG} \
                ${ECR_REPO}:${IMAGE_TAG}

                docker push \
                ${ECR_REPO}:${IMAGE_TAG}
                '''
            }

        }

        stage('Deploy To Kubernetes') {

            steps {

                sh '''
                kubectl apply -f k8s/
                '''
            }

        }

    }

    post {

        success {

            echo 'Application Deployed Successfully'

        }

        failure {

            echo 'Pipeline Failed'

        }

    }

}
