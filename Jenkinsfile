pipeline {

    // jenkins  에이전트를 지정하지 않으므로 자동으로 사용가능한 모든 에이전트를 사용하도록 허용
    agent any

    tools {
        gradle 'gradle.6.8'
    }
    
    environment {
        APP_ID          = "spring-cloud-eureka-test"
        APP_PATH        = "${env.WORKSPACE}/${APP_ID}"
        IMAGE_NAME      = "eureka-server"
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
                       echo "---image Stage---"
                       //sh """docker build -t bbp-sample:build-${env.BUILD_ID} ./"""
                       //sh "docker build -t spring-cloud-eureka-server:${env.BUILD_ID} ./"
                       //docker.build("${IMAGE_NAME}:latest")
                       docker.build("${IMAGE_NAME}:${env.BUILD_NUMBER}")

                }
            }
        }


        stage('docker image push') {
            stages{
                script {

                    echo '------  docker image push ----------'
                    //docker.witRegistry('')
                }

            }

        }

        stage('docker run') {
            steps {
                echo "---Push Stage---"
                dir("${APP_PATH}") {
                    sh ' docker run -d -ti --name eureka-test -p 8761:8761 ${IMAGE_NAME}:latest'
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