
### docker build 하기
```shell
    # docker build -t {image name}:{tag version} {docker file path}    
    docker build -t mycup/spring-server-ads:latest ./
    
    ## 아규먼트로 사용할 ACTIVE 정보 입력 
    docker build --build-arg ENVIRONMENT=dev -t mycup/spring-server-ads:latest ./	
    
    ## 이미지 확인
    docker images
    
    ------
    REPOSITORY                         TAG          IMAGE ID       CREATED          SIZE
    spring-cloud-eureka-server   latest       702b2ed6b7ba   34 seconds ago   485MB

```
### docker login
```text
mezzo-skan@mezzoui-MacBookPro spring-service-ads % docker login                 
Authenticating with existing credentials...
Stored credentials invalid or expired
Login with your Docker ID to push and pull images from Docker Hub. If you don't have a Docker ID, head over to https://hub.docker.com to create one.
Username (mycup): mycup
Password: 
Login Succeeded

```

### docker push
웹에서 생성한것과 docker tag 를 docker hub id와 동일하게 생성햐준다. 
```shell


  # docker push <Docker 레지스트리 URL>/<이미지 이름>:<태그> 형식입니다.
  # docker push mycup/adserver:tagname
  docker push mycup/adserver:spring-server-ads



```