docker build -t mycup/spring-cloud-gateway ./


docker images| grep -i gateway
mycup/spring-cloud-client            latest                                                  c4df20bf9b93   About a minute ago   482MB

docker login

docker push mycup/spring-cloud-gateway 