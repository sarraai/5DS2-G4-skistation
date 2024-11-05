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
        
        // 8. Push Docker Image to Docker Hub (Commented Out)
        /*
        stage('BUILDING IMAGE') {
            steps {
                script {
                    dockerImage = docker.build("${registry}:${BUILD_NUMBER}")
                }
            }
        }
        */

        // 9. Run Maven Tests
        stage('Run Maven Tests') {
            steps {
                echo 'Running Maven tests...'
                sh 'mvn test'
            }
        }

        // 10. Docker Compose to Launch Services (Commented Out)
        /*
        stage('Docker Compose Setup') {
            steps {
                echo 'Starting services with Docker Compose...'
                sh 'docker compose up -d'
            }
        }
        */

        // 11. Launch Prometheus
        

        // 12. Launch Grafana
        // 11. Launch Prometheus
stage('Launch Prometheus') {
    steps {
        echo 'Starting Prometheus for monitoring...'
        script {
            // Run Prometheus container with a different name
            def result = sh(script: 'docker run -d --name prometheus_new -p 9090:9090 prom/prometheus', returnStatus: true)
            
            // Check if the container started successfully
            if (result != 0) {
                error 'Failed to start Prometheus container!'
            }

            // Wait for Prometheus to be ready
            sleep 10 // Wait for 10 seconds for Prometheus to start

            // Check if Prometheus is running
            def isRunning = sh(script: 'docker ps | grep prometheus_new', returnStatus: true)
            if (isRunning != 0) {
                error 'Prometheus container is not running!'
            }
        }
    }
}


        // 13. Unit Testing with JUnit
        stage('Unit Testing with JUnit') {
            steps {
                echo 'Executing Unit Tests...'
                sh 'mvn test'
            }
        }
    }
}





