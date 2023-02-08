docker build -t mycup/spring-service-product ./


docker images| grep -i product
mycup/spring-cloud-client            latest                                                  c4df20bf9b93   About a minute ago   482MB

docker login

docker push mycup/spring-service-product