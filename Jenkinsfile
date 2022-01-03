pipeline {

    agent any

    tools {
        gradle 'gradle.6.8'
    }
    
    environment {
        APP_ID          = "spring-cloud-eureka-test"
        APP_PATH        = "${env.WORKSPACE}/${APP_ID}"
    }

    stages {

        stage('Checkout Stage') {
            steps {
                echo "---Checkout---"

                git branch: 'main', credentialsId: '98acb507-2f04-424e-a102-61097664f03e', url: 'https://github.com/an-sangkil/spring-cloud-ecosystem-study.git'    
            }
            
            
        }
        
        stage('Build Stage') {
            steps{
                dir ("spring-cloud-eureka-test") {
                    script {
                        echo "---Build Stage---"
                        def directory_path = sh(script: 'pwd', returnStdout: true)
                        echo "path str = ${directory_path}, app path ${APP_PATH}"
                        def ret = sh(script: 'uname', returnStdout: true)
                        println "uname = "+ ret
                        //sh "ls -al \n chmod +x gradlew \n ./gradlew build -x test"
                    }
                }
            }
        }

        stage('Build gradle') {
            steps {
                dir("${APP_PATH}") {
                    sh 'gradle clean bootJar --parallel --build-cache'
                }
            }
        }

        stage("docker image ") {
            steps {
                dir("${APP_PATH}") {
                       //sh """docker build -t bbp-sample:build-${env.BUILD_ID} ./"""
                       sh "docker build -t spring-cloud-eureka-server:latest ./"
                }
            }
        }

        stage('Push Stage') {
            steps {
                echo "---Push Stage---"
            }
        }
    }


}