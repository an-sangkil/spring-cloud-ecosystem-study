### docker build 하기 
```shell 
    docker build -t {image name}:{tag version} {docker file path}	
    docker build -t spring-cloud-eureka-server:latest ./	
    docker images
    
    ------
    REPOSITORY                         TAG          IMAGE ID       CREATED          SIZE
    spring-cloud-eureka-server   latest       702b2ed6b7ba   34 seconds ago   485MB
```
##### docker build option 설명
  - -t --tag: 레파지토리(이미지이름)과 테그명을 입력한다 ':' 기준으로 {이미지이름}:{테그번호}로 남겨진다.  테그 번호를 남기지 않으면 자동으로 latest가 된다 
  - ./ : Dockerfile 이 있는 경로를 지정해 준다.  (지금은 현재 디렉토라)



#### image 생성후 docker run 으로 container 생성 
docker run -d -p 8761:8761 --name={container_name} {image_name}
docker run -d -p 8761:8761 --name=spring-discovery spring-cloud-eureka-server:latest

### 생성된 container 확인 
docker build 로는 이미지를 생성하는 것이기 때문에 컨테이너에는 나타나지 않는다.
docker run 으로 실행시킨후 container 가 새성된 것을 확인 할 수 있다.
```shell
skan@mezzoui-MacBookPro spring-cloud-eureka-test % docker ps
CONTAINER ID   IMAGE                               COMMAND                  CREATED          STATUS          PORTS                                                                                      NAMES
8f55b814771b   spring-cloud-eureka-server:latest   "java -Djava.securit…"   8 seconds ago    Up 8 seconds    0.0.0.0:8761->8761/tcp, :::8761->8761/tcp                                                  spring-discovery
0efaf8cded3a   jenkins_jenkins                     "/sbin/tini -- /usr/…"   59 minutes ago   Up 59 minutes   0.0.0.0:50000->50000/tcp, :::50000->50000/tcp, 0.0.0.0:9090->8080/tcp, :::9090->8080/tcp   jenkins
756f617f2699   mysql:5.7.21                        "docker-entrypoint.s…"   5 weeks ago      Up 6 hours      0.0.0.0:3306->3306/tcp, :::3306->3306/tcp                                                  mysql
```

### docker start 및 stop
docker run 으로 생성한 컨테이너들은 다시 run 으로 생성 할 수 없다. 
컨테이너 아이디를 사용하여 서비스를 시작 혹은 중지한다.

```shell
docker start {container_id}
docker stop {container_id}
```

### docker run , spring parameter 적용 
docker run -p 80:8080 -d --restart always -e USE_PROFILE={profile} --name={container_name} {image_name}
docker run -p 8761:8761 -d --restart always -e USE_PROFILE=prod --name=spring-discovery spring-cloud-eureka-server:latest

실행된 콘솔에 `the following profiles are active: prod` 로 바뀌어서 나온걸 확인 할 수 있다.
 
```text

spring-discovery
spring-cloud-eureka-server:latest
RUNNING
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.5.1)


2021-12-28 09:22:35.568  INFO 1 --- [           main] eureka.EurekaApplication                 : Starting EurekaApplication using Java 11.0.13 on 13077e1d3735 with PID 1 (/spring-cloud-eureka.jar started by root in /)
2021-12-28 09:22:35.570  INFO 1 --- [           main] eureka.EurekaApplication                 : The following profiles are active: prod
2021-12-28 09:22:36.690  INFO 1 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=3ebe42c1-5a14-3c99-9cf0-16663e96ee0e
2021-12-28 09:22:37.055  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8761 (http)

```

