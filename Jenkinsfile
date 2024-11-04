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
        stage('ARTIFACT CONSTRUCTION') {
            steps {
                echo 'Building Maven package...'
                sh 'mvn -f gestion-station-ski/pom.xml package -Dmaven.test.skip=true -P test-coverage' // Ensure correct path
            }
        }

//4. SonarQube 
stage('SonarQube') {
            steps {
                echo 'Analyse the quality of code : ';
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=123BIZERTE@cab456';
            }
        }


        // 4. Docker Image Creation
        stage('Docker Image Creation') {
            steps {
                echo 'Building Docker Image...'
                sh 'docker build -t 5ds2-g4-skistation .'
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

        // 7. Deploy to Nexus Repository
        stage('Nexus Deployment') {
            steps {
                echo 'Deploying to Nexus...'
                sh 'mvn -f gestion-station-ski/pom.xml deploy -DskipTests'
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

        // 11. Check Database Connection
        stage('CHECK DATABASE CONNECTION') {
            steps {
                script {
                    echo 'Checking database connection...'
                    withCredentials([usernamePassword(credentialsId: 'mysql-credentials', usernameVariable: 'DB_USER', passwordVariable: 'DB_PASSWORD')]) {
                        def dbHost = 'your_database_host' // Replace with your actual database host
                        def dbPort = 'your_database_port' // Replace with your actual database port

                        // Attempt to connect to the database
                        try {
                            sh """
                                echo "SELECT 1;" | mysql -h ${dbHost} -P ${dbPort} -u ${DB_USER} -p${DB_PASSWORD}
                            """
                        } catch (err) {
                            error("Database connection failed: ${err}")
                        }
                    }
                }
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

