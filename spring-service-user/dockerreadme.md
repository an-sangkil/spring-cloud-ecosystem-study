### docker file build
``` bash
# 이미지 빌드
docker build -t mycup/spring-service-user ./

# 빌드된 이미지 확인
docker images| grep -i user
mycup/spring-cloud-client            latest                                                  c4df20bf9b93   About a minute ago   482MB

# docker hub login
docker login

# image file 을  docker hub 로 push
docker push mycup/spring-service-user
```