pipeline {
    agent any

    environment {
        // Define las variables de entorno si es necesario
        MAVEN_HOME = "C:/Users/frank/Documents/MAVEN/apache-maven-3.9.6"
        ARTIFACTORY_NAME = 'Artifactory-Server' // El nombre de tu configuración de Artifactory en Jenkins
        ARTIFACTORY_REPO_KEY = 'your-repo-key' // El key del repositorio de Artifactory para desplegar artefactos
        ARTIFACTORY_URL = 'http://artifactory.mycompany.com/artifactory' // URL de tu servidor Artifactory
    }

    stages {
        stage('Checkout') {
            steps {
                // Obtener el código fuente del repositorio especificado
                git url: 'https://github.com/frankkismann/myconstruction-web.git'
            }
        }
        
        stage('Build and Artifactory Publish') {
            steps {
                script {
                    def artifactory = Artifactory.server("${ARTIFACTORY_NAME}")
                    def rtMaven = artifactory.newMavenBuild()
                    def buildInfo = artifactory.newBuildInfo()
                    
                    rtMaven.resolver releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot'
                    rtMaven.deployer releaseRepo: "${ARTIFACTORY_REPO_KEY}", snapshotRepo: "${ARTIFACTORY_REPO_KEY}", url: "${ARTIFACTORY_URL}"
                    rtMaven.run pom: 'pom.xml', goals: 'clean deploy', buildInfo: buildInfo
                    
                    artifactory.publishBuildInfo buildInfo
                }
            }
        }

        stage('Code Analysis') {
            steps {
                // Integrar herramientas de análisis de código como SonarQube
                bat "\"${MAVEN_HOME}\\bin\\mvn\" sonar:sonar"
            }
        }

        stage('Deploy') {
            steps {
                // Pasos para desplegar la aplicación Java EE
                echo 'Deploying the Java EE application...'
                // Aquí puedes agregar los pasos necesarios para desplegar tu aplicación Java EE
                // Por ejemplo, desplegar en un servidor de aplicaciones como WildFly, Tomcat, etc.
                // bat 'deploy_to_server.bat'
            }
        }
    }
    post {
        // Ejecutar acciones post-build como limpieza o notificaciones
        always {
            // Ejemplo: Limpiar el workspace
            cleanWs()
        }
    }
}
