pipeline {
    agent any

    environment {
        // Define las variables de entorno si es necesario
        MAVEN_HOME = "C:/Users/frank/Documents/MAVEN/apache-maven-3.9.6"
    }

    stages {
        stage('Checkout') {
            steps {
                // Obtener el código fuente del repositorio especificado
                bat 'git clone https://github.com/frankkismann/myconstruction-web.git'
            }
        }
        
        stage('Build') {
            steps {
                // Construir el proyecto Java EE con Maven
                bat "\"${MAVEN_HOME}\\bin\\mvn\" clean package"
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
}
