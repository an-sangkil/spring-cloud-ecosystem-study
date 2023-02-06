
```shell
mezzo-skan@mezzoui-MacBookPro spring-service-ads % kubectl apply -f ads-deployment.yaml
deployment.apps/hello-ads-server created
mezzo-skan@mezzoui-MacBookPro spring-service-ads % kubectl get deployments
NAME               READY   UP-TO-DATE   AVAILABLE   AGE
hello-ads-server   0/1     1            0           18s
mezzo-skan@mezzoui-MacBookPro spring-service-ads % kubectl apply -f ads-service.yaml
service/hello-ads-server created
mezzo-skan@mezzoui-MacBookPro spring-service-ads % kubectl get services             
NAME               TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)          AGE
hello-ads-server   NodePort    10.105.224.1   <none>        8082:30090/TCP   8s
kubernetes         ClusterIP   10.96.0.1      <none>        443/TCP          61m
mezzo-skan@mezzoui-MacBookPro spring-service-ads % 
```


# 웹브라우저 접속 정보 확인
minikube service hello-service-ads
```text
mezzo-skan@mezzoui-MacBookPro spring-service-ads % minikube service hello-ads-server 
|-----------|------------------|-------------|---------------------------|
| NAMESPACE |       NAME       | TARGET PORT |            URL            |
|-----------|------------------|-------------|---------------------------|
| default   | hello-ads-server |        8082 | http://192.168.49.2:30090 |
|-----------|------------------|-------------|---------------------------|
🏃  hello-ads-server 서비스의 터널을 시작하는 중
|-----------|------------------|-------------|------------------------|
| NAMESPACE |       NAME       | TARGET PORT |          URL           |
|-----------|------------------|-------------|------------------------|
| default   | hello-ads-server |             | http://127.0.0.1:52663 |
|-----------|------------------|-------------|------------------------|
🎉  Opening service default/hello-ads-server in default browser...
❗  Because you are using a Docker driver on darwin, the terminal needs to be open to run it.
```

