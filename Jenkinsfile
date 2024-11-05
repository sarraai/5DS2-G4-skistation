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
        echo 'Pushing Docker Image to Docker Hub...';
        // Log in to Docker Hub
        sh 'docker login -u sarraaissaoui -p 123cab456'; 
        // Push the Docker image
        sh 'docker push sarraaissaoui/skistation:1.0.0'; 
    }
}


        // 9. Run Maven Tests
        stage('Run Maven Tests') {
            steps {
                echo 'Running Maven tests...'
                sh 'mvn test'
            }
        }

        stage('Docker Compose Setup') {
    steps {
        echo 'Starting services with Docker Compose...'
        dir('/home/vagrant/docker') { // Navigate to the directory containing docker-compose.yml
            sh 'docker-compose up -d' // Run Docker Compose
        }
    }
}

        

        // 11. Launch Prometheus (Commented Out)
        
        stage('Launch Prometheus') {
            steps {
                echo 'Starting Prometheus for monitoring...'
                sh 'docker run -d --name prometheus -p 9090:9090 prom/prometheus'
            }
        }
    

        // 12. Launch Grafana (Commented Out)
    
        stage('Launch Grafana') {
            steps {
                echo 'Starting Grafana for visualization...'
                sh 'docker run -d --name grafana -p 3000:3000 grafana/grafana'
            }
        }
        

        // 13. Unit Testing with JUnit
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
    }
}







