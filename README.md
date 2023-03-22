# Spring Cloud Study


### Spring Cloud 구조 
스프링 클라우드, server discovery pattern 에 대한 기본 프레임워크 
 - eureka (server client)
 - api gateway
   - router 
   - Circuit Breaker
   - retry
 - open feign (mvc에서 동작됨 non-blocking 미지원) 
 - cloud config
 

### flow
![이미지](./document/쿠버네티스%20spring%20cloud(boot)%20예제.drawio.png)

### 접속 url 
- [eureka server](http:/127.0.0.1:8761)
  - 등록된 서버들을 확인하고 관리 한다. 
- [gateway-server](http://127.0.0.1:8080)
  - 게이트워에 서버 
- [ads-server](http:/127.0.0.1:8082)
  - 광고정보를 제공하는 API 서버 
- [product-server](http://127.0.0.1:8083)
  - 상품 정보를 제공하는 API 서버 
- [user-server](http:/127.0.0.1:8084)
  - 사용자 정보를 제공하는 API 서저


### kubernetes

kubectl apply -f cloud-deployment.yaml
kubectl apply -f cloud-service.yaml
