pipeline {
    agent any

    tools {
        // Esta sección asegura que Maven esté configurado si es necesario, puedes ajustar la versión si es diferente.
        maven 'Maven 3.9.6' 
    }

    environment {
        // Define las variables de entorno si es necesario
        ARTIFACTORY_NAME = 'Artifactory-Server' // El nombre de tu configuración de Artifactory en Jenkins
        ARTIFACTORY_REPO_KEY = 'myconstruction-libs-release' // El key del repositorio de Artifactory para desplegar artefactos
        ARTIFACTORY_URL = 'https://appsource.jfrog.io/artifactory' // URL de tu servidor Artifactory
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
                    // Definir la instancia de Artifactory y el objeto Maven para la construcción
                    def server = Artifactory.server(ARTIFACTORY_NAME)
                    def rtMaven = server.newMavenBuild()
                    rtMaven.tool = 'Maven 3.9.6' // Asegúrate de que corresponda con el nombre de la herramienta Maven configurada en Jenkins.
                    rtMaven.resolver server: server, releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot'
                    rtMaven.deployer server: server, releaseRepo: ARTIFACTORY_REPO_KEY, snapshotRepo: ARTIFACTORY_REPO_KEY
                    def buildInfo = rtMaven.run goals: 'clean install -Dmaven.test.skip=true', pom: 'pom.xml'
                    
                    // Publicar la información de construcción en Artifactory
                    server.publishBuildInfo buildInfo
                }
            }
        }

        stage('Code Analysis') {
            steps {
                // Integrar herramientas de análisis de código como SonarQube
                bat "mvn sonar:sonar"
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
