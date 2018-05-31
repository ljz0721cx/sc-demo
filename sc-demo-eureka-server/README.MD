#Eureka Server

分布式协调服务

##相互注册
配置本机host
```$xslt
127.0.0.1 peer1 peer2
```
打包sc-demo-eureka-server的包
```
java -jar sc-demo-eureka-server-1.0-SNAPSHOT.jar --spring.profiles.active=peer1
java -jar sc-demo-eureka-server-1.0-SNAPSHOT.jar --spring.profiles.active=peer2
```
访问启动的服务 会发现各自都会被注册上   
http://peer1:8760  
http://peer2:8761
