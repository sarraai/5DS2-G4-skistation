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

//4. SonarQube 
stage('SonarQube') {
            steps {
                echo 'Analyse the quality of code : ';
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=123BIZERTE@cab456';
            }
        }

        stage('Maven Package') {
            steps {
                echo 'Création du livrable : ';
                sh 'mvn package -DskipTests';
            }
        }

         stage('Deploy to Nexus') {
            steps {
                echo 'Déploiement sur Nexus : '
                sh 'mvn deploy -DskipTests'
            }
        }


        // 4. Docker Image Creation
        stage('Docker Image Creation') {
            steps {
                echo 'Building Docker Image...'
                sh 'docker build -t sarraaissaoui/skistation:1.0.0 .';
            }
        }

        // 5. Push Docker Image to Docker Hub
        stage('Push Docker Image to Docker Hub') {
            steps {
                echo 'Pushing Docker Image to Docker Hub...'
                withCredentials([usernamePassword(credentialsId: 'dockerHub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh '''
                        echo "Logging into Docker Hub..."
                        docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD
                        docker push sarraai/5DS2-G4-skistation:1.0.0
                    '''
                }
            }
        }

        // 6. Maven Tests
        stage('MVN TESTS') {
            steps {
                echo "Maven Test";
            }
        }

        

        // 8. Docker Compose to Launch Services
        stage('Docker Compose Setup') {
            steps {
                echo 'Starting services with Docker Compose...'
                sh 'docker compose up -d'
            }
        }

        // 9. Launch Prometheus
        stage('Launch Prometheus') {
            steps {
                echo 'Starting Prometheus for monitoring...'
                sh 'docker run -d --name prometheus -p 9090:9090 prom/prometheus'
            }
        }

        // 10. Launch Grafana
        stage('Launch Grafana') {
            steps {
                echo 'Starting Grafana for visualization...'
                sh 'docker run -d --name grafana -p 3000:3000 grafana/grafana'
            }
        }

       

        // 12. JUnit Test Execution
        stage('Unit Testing with JUnit') {
            steps {
                echo 'Executing Unit Tests...'
                sh 'mvn test'
            }
        }
    }
}

