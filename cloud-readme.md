```shell


mezzo-skan@mezzoui-MacBookPro spring-cloud-ecosystem-study % kubectl apply -f cloud-service.yaml
service/hello-cloud-service created

mezzo-skan@mezzoui-MacBookPro spring-cloud-ecosystem-study % kubectl get deployment
NAME                         READY   UP-TO-DATE   AVAILABLE   AGE
hello-ads-cloud-deployment   0/2     2            0           65s
hello-ads-server             1/1     1            1           19m


mezzo-skan@mezzoui-MacBookPro spring-cloud-ecosystem-study % kubectl apply -f cloud-deployment.yaml
deployment.apps/hello-ads-cloud-deployment created


mezzo-skan@mezzoui-MacBookPro spring-cloud-ecosystem-study % kubectl get service
NAME                  TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)                                      AGE
hello-ads-server      NodePort    10.109.30.79     <none>        8082:30090/TCP                               20m
hello-cloud-service   NodePort    10.111.228.178   <none>        80:30091/TCP,8761:30092/TCP,8082:30093/TCP   62s
kubernetes            ClusterIP   10.96.0.1        <none>        443/TCP                                      66d



## 미니큐브 사용시 터널링을 해주어야 외부에서 접속할수있다.  
mezzo-skan@mezzoui-MacBookPro spring-cloud-ecosystem-study % minikube service hello-cloud-service
|-----------|---------------------|----------------------|---------------------------|
| NAMESPACE |        NAME         |     TARGET PORT      |            URL            |
|-----------|---------------------|----------------------|---------------------------|
| default   | hello-cloud-service | client/80            | http://192.168.49.2:30091 |
|           |                     | eureca-continer/8761 | http://192.168.49.2:30092 |
|           |                     | ads-continer/8082    | http://192.168.49.2:30093 |
|-----------|---------------------|----------------------|---------------------------|
🏃  hello-cloud-service 서비스의 터널을 시작하는 중
|-----------|---------------------|-------------|------------------------|
| NAMESPACE |        NAME         | TARGET PORT |          URL           |
|-----------|---------------------|-------------|------------------------|
| default   | hello-cloud-service |             | http://127.0.0.1:58328 |
|           |                     |             | http://127.0.0.1:58329 |
|           |                     |             | http://127.0.0.1:58330 |
|-----------|---------------------|-------------|------------------------|
[default hello-cloud-service  http://127.0.0.1:58328
http://127.0.0.1:58329
http://127.0.0.1:58330]
❗  Because you are using a Docker driver on darwin, the terminal needs to be open to run it.

```


# argo cd
 - 참고하여 설치
 - https://argo-cd.readthedocs.io/en/stable/getting_started/



```shell

# 설치를 하고 서비스를 보면 모두 ClusterIP로 나온다. 
mezzo-skan@mezzoui-MacBookPro spring-cloud-ecosystem-study % kubectl get service -n argocd
NAME                                      TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)                      AGE
argocd-applicationset-controller          ClusterIP   10.104.234.198   <none>        7000/TCP,8080/TCP            129m
argocd-dex-server                         ClusterIP   10.106.150.106   <none>        5556/TCP,5557/TCP,5558/TCP   129m
argocd-metrics                            ClusterIP   10.109.110.47    <none>        8082/TCP                     129m
argocd-notifications-controller-metrics   ClusterIP   10.110.137.140   <none>        9001/TCP                     129m
argocd-redis                              ClusterIP   10.97.26.116     <none>        6379/TCP                     129m
argocd-repo-server                        ClusterIP   10.106.114.159   <none>        8081/TCP,8084/TCP            129m
argocd-server                             ClusterIP   10.101.125.129   <none>        80/TCP,443/TCP   129m
argocd-server-metrics                     ClusterIP   10.102.113.18    <none>        8083/TCP                     129m


# 설치하고 서비스를 보면 모두 ClusterIp 로 되어 있다. 이에 argocd-server pod 를 외부에서 접속 가능하도록 nodeport로 변경해준다.
# argocd-server service의 type NodePort로 변경
kubectl patch svc argocd-server -n argocd -p '{"spec": {"type": "NodePort"}}'


# 서비스 목록 확인 
mezzo-skan@mezzoui-MacBookPro spring-cloud-ecosystem-study % kubectl get service -n argocd
NAME                                      TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)                      AGE
argocd-applicationset-controller          ClusterIP   10.104.234.198   <none>        7000/TCP,8080/TCP            129m
argocd-dex-server                         ClusterIP   10.106.150.106   <none>        5556/TCP,5557/TCP,5558/TCP   129m
argocd-metrics                            ClusterIP   10.109.110.47    <none>        8082/TCP                     129m
argocd-notifications-controller-metrics   ClusterIP   10.110.137.140   <none>        9001/TCP                     129m
argocd-redis                              ClusterIP   10.97.26.116     <none>        6379/TCP                     129m
argocd-repo-server                        ClusterIP   10.106.114.159   <none>        8081/TCP,8084/TCP            129m
argocd-server                             NodePort    10.101.125.129   <none>        80:32238/TCP,443:32619/TCP   129m
argocd-server-metrics                     ClusterIP   10.102.113.18    <none>        8083/TCP                     129m

# 이제  nodeport로 변경했기때문에 type이 nodeport로 보인다. 
# 접속은 80 port로 접속하려면 32238  로 접속하고 443은  32619 로 접속한다.
# http://{IP}:32338  혹은 https://{IP}:32619 로 접속



# 미니큐브를 사용한다면 바로 nodeport로 접속이 불가능하며, 터널링을 실행해 주어야 한다. 
# ui 서비스 터널링 실행 
mezzo-skan@mezzoui-MacBookPro spring-cloud-ecosystem-study % minikube service argocd-server -n argocd
|-----------|---------------|-------------|---------------------------|
| NAMESPACE |     NAME      | TARGET PORT |            URL            |
|-----------|---------------|-------------|---------------------------|
| argocd    | argocd-server | http/80     | http://192.168.49.2:32238 |
|           |               | https/443   | http://192.168.49.2:32619 |
|-----------|---------------|-------------|---------------------------|
🏃  argocd-server 서비스의 터널을 시작하는 중
|-----------|---------------|-------------|------------------------|
| NAMESPACE |     NAME      | TARGET PORT |          URL           |
|-----------|---------------|-------------|------------------------|
| argocd    | argocd-server |             | http://127.0.0.1:61010 |
|           |               |             | http://127.0.0.1:61011 |
|-----------|---------------|-------------|------------------------|
[argocd argocd-server  http://127.0.0.1:61010
http://127.0.0.1:61011]
❗  Because you are using a Docker driver on darwin, the terminal needs to be open to run it.

# http://127.0.0.1:61010 으로 접속 , 

```

## argocd 비빌번호확인
 - 비빌번호를 확인 할순있지만 인코딩되어 있어 알아볼수없다.
```shell
# secret에서 data.password 값 조회
mezzo-skan@mezzoui-MacBookPro ~ % kubectl get secret argocd-initial-admin-secret -n argocd -o jsonpath="{.data.password}" ;echo
WnBpV2xxUFBNcXZPQW1wcQ==
```
 - 디코딩으로 암호를 확인
```shell
mezzo-skan@mezzoui-MacBookPro ~ % kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d; echo
ZpiWlqPPMqvOAmpq
```
admin/ZpiWlqPPMqvOAmpq