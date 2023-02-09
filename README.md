"# spring-cloud-template-test" 

### Spring Cloud Study
스프링 클라우드, server discovery pattern 에 대한 기본 프레임워크 

 - eureka (server client)
 - api gateway
   - router 
   - Circuit Breaker
   - retry
 - open feign (mvc에서 동작됨 non-blocking 미지원) 


### flow
![이미지](./document/쿠버네티스%20spring%20cloud(boot)%20예제.drawio.png)

### url 
- [eureka server](http:/127.0.0.1:8761)
- [gateway-server](http://127.0.0.1:8080)
- [ads-server](http:/127.0.0.1:8082)
- [product-server](http://127.0.0.1:8083)
- [user-server](http:/127.0.0.1:8084)


### kubernetes

kubectl apply -f cloud-deployment.yaml
kubectl apply -f cloud-service.yaml
