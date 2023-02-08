docker build -t mycup/spring-service-user ./


docker images| grep -i user
mycup/spring-cloud-client            latest                                                  c4df20bf9b93   About a minute ago   482MB

docker login

docker push mycup/spring-service-user