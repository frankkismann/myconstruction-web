pipeline {
    agent any

    tools {
        // Asegúrate de que Maven esté configurado en Jenkins y el nombre coincida con la configuración de tus herramientas
        maven 'Maven 3.9.6'
    }

    environment {
        // Define las variables de entorno
        ARTIFACTORY_NAME = 'Artifactory-Server'
        ARTIFACTORY_REPO_KEY = 'myconstruction-libs-release'
        ARTIFACTORY_URL = 'https://appsource.jfrog.io/artifactory'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/frankkismann/myconstruction-web.git'
            }
        }

        stage('Build and Artifactory Publish') {
            steps {
                script {
                    timeout(time: 20, unit: 'MINUTES') {
                        def server = Artifactory.server(ARTIFACTORY_NAME)
                        def rtMaven = server.newMavenBuild()
                        rtMaven.tool = 'Maven 3.9.6'
                        rtMaven.resolver server: server, releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot'
                        rtMaven.deployer server: server, releaseRepo: ARTIFACTORY_REPO_KEY, snapshotRepo: ARTIFACTORY_REPO_KEY
                        def buildInfo = rtMaven.run goals: 'clean install -Dmaven.test.skip=true', pom: 'pom.xml'
                        server.publishBuildInfo buildInfo
                    }
                }
            }
        }

        stage('Code Analysis') {
            when {
                fileExists 'pom.xml'
            }
            steps {
                // Asegúrate de que tu servidor Jenkins tiene acceso a SonarQube y que el plugin de SonarQube está configurado correctamente
                bat "mvn sonar:sonar"
            }
        }

        stage('Deploy') {
            steps {
                // Agrega aquí tus pasos de despliegue
                echo 'Deploying the Java EE application...'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
