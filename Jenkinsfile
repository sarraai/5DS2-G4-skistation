pipeline {
    environment {
        registry = "sarraai/skiexam"
        registryCredential = 'dockerHub'
        dockerImage = ''
    }
    agent any

    stages {
        // 1. Checkout SCM
        stage('CHECKOUT GIT') {
            steps {
                echo 'Cloning GitHub repository...'
                git branch: 'sarra', url: 'https://github.com/sarraai/5DS2-G4-skistation.git'
            }
        }

        // 2. Maven Clean
        stage('MVN CLEAN') {
            steps {
                echo 'Running Maven clean...'
                sh 'mvn clean'
            }
        }

        // 3. Artifact Construction
        stage('COMPILATION') {
            steps {
                echo 'Building Maven package...'
                sh 'mvn compile' // Ensure correct path
            }
        }

        // 4. SonarQube 
        stage('SonarQube') {
            steps {
                echo 'Analyzing the quality of code...'
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=123BIZERTE@cab456'
            }
        }

        // 5. Maven Package
        stage('Maven Package') {
            steps {
                echo 'Creating deliverable...'
                sh 'mvn package -DskipTests'
            }
        }

        // 6. Deploy to Nexus
        stage('Deploy to Nexus') {
            steps {
                echo 'Deploying to Nexus...'
                sh 'mvn deploy -DskipTests'
            }
        }

        // 7. Docker Image Creation
        stage('Docker Image Creation') {
            steps {
                echo 'Building Docker Image...'
                sh 'docker build -t sarraaissaoui/skistation:1.0.0 .'
            }
        }

        // 8. Push Docker Image to Docker Hub
        stage('Push Docker Image to Docker Hub') {
            steps {
                echo 'Pushing Docker Image to Docker Hub...'
                // Log in to Docker Hub
                sh 'docker login -u sarraaissaoui -p 123cab456' 
                // Push the Docker image
                sh 'docker push sarraaissaoui/skistation:1.0.0'
            }
        }

        // 9. Run Maven Tests
        stage('Run Maven Tests') {
            steps {
                echo 'Running Maven tests...'
                sh 'mvn test'
            }
        }

        // 10. Launch Prometheus
        stage('Launch Prometheus') {
            steps {
                script {
                    echo 'Starting Prometheus for monitoring...'
                    // Check if the Prometheus container exists and remove it if it does
                    sh '''
                        if [ "$(docker ps -aq -f name=prometheus)" ]; then
                            docker stop prometheus || true
                            docker rm prometheus || true
                        fi
                        docker run -d --name prometheus -p 9091:9090 -v /home/vagrant/docker/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus
                    '''
                }
            }
        }

        // 11. Launch Grafana
        stage('Launch Grafana') {
            steps {
                script {
                    echo 'Starting Grafana for visualization...'
                    // Check if the Grafana container exists and remove it if it does
                    sh '''
                        if [ "$(docker ps -aq -f name=grafana)" ]; then
                            docker stop grafana || true
                            docker rm grafana || true
                        fi
                        docker run -d --name grafana -p 3001:3000 grafana/grafana
                    '''
                }
            }
        }

        // 12. Unit Testing with JUnit
        stage('Unit Testing with JUnit') {
            steps {
                echo 'Executing Unit Tests...'
                // Run the tests with more output and skip integration tests
                sh 'mvn test -DskipITs' 
                
                // Optionally, check for test reports
                echo 'Checking test results...'
                sh 'mvn surefire-report:report' // Generate test reports (if you want to review them in Jenkins)
            }
        }

        // 13. Docker Compose Setup
        stage('Docker Compose Setup') {
            steps {
                echo 'Starting services with Docker Compose...'
                dir('/home/vagrant/docker') { // Navigate to the directory containing docker-compose.yml
                    // Check if docker-compose.yml exists before running the command
                    if (fileExists('docker-compose.yml')) {
                        echo 'docker-compose.yml found, starting services...'
                        sh 'docker-compose up -d' // Run Docker Compose
                    } else {
                        error 'docker-compose.yml not found!'
                    }
                }
            }
            post {
                always {
                    echo 'Stopping and cleaning up Docker Compose services...'
                    dir('/home/vagrant/docker') { // Ensure you're in the correct directory
                        sh 'docker-compose down' // Stops and removes the containers after the stage completes
                    }
                }
            }
        }

        // 14. Docker Compose Up
        stage('Docker Compose Up') {
            steps {
                script {
                    // Navigate to the directory containing docker-compose.yml
                    dir('/home/vagrant/docker') {
                        // Check if docker-compose.yml exists before running the command
                        if (fileExists('docker-compose.yml')) {
                            echo 'Starting services with Docker Compose...'
                            // Execute docker-compose up
                            sh """
                                export REGISTRY=${registry}
                                export BUILD_NUMBER=${env.BUILD_NUMBER}
                                docker-compose up -d
                            """
                        } else {
                            error 'docker-compose.yml not found!'
                        }
                    }
                }
            }
        }
    }
}









