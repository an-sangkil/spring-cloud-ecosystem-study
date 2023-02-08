docker build -t mycup/spring-cloud-client:latest ./

docker images| grep -i client
mycup/spring-cloud-client            latest                                                  c4df20bf9b93   About a minute ago   482MB

docker login

docker push mycup/spring-cloud-client