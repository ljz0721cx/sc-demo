# sc-demo
## spring-boot + spring cloud + docker

配置相关的类

ribbon 配置类 DefaultClientConfigImpl
hystrix配置类 HystrixCommandProperties

##eureka配置

```
#spring.application.name=eureka-server
eureka.instance.appname=eureka-server
eureka.instance.virtualHostName=eureka-server
eureka.instance.secureVirtualHostName=eureka-server

server.port=8888
#将自己注册到eureka注册中心，单节点关闭 
eureka.client.registerWithEureka=true
#从注册中心获取注册信息，单节点关闭 
eureka.client.fetchRegistry=true
#从注册中心获取注册信息的时间间隔
eureka.client.registryFetchIntervalSeconds=10
#非安全通信端口
#eureka.instance.nonSecurePort=80
#是否启用非安全端口接受请求
#eureka.instance.nonSecurePortEnabled=true
#安全通信端口
#eureka.instance.securePort=443
#是否启用安全端口接受请求
#eureka.instance.securePortEnabled=true
#是否优先使用IP地址作为主机名的标识，默认false
#eureka.instance.preferIpAddress=false
#eureka节点定时续约时间，默认30
eureka.instance.leaseRenewalIntervalInSeconds=15
#eureka节点剔除时间，默认90
eureka.instance.leaseExpirationDurationInSeconds=45
eureka.instance.instance-id=${spring.cloud.client.ipAddress}:eureka-server-peer:${server.port}
#注册到另外两个节点，实现集群
eureka.client.serviceUrl.defaultZone=http://localhost:8887/eureka/,http://localhost:8888/eureka/,http://localhost:8889/eureka/
#单节点部署，自己注册自己
#eureka.client.serviceUrl.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/
#关闭eureka自我保护机制
eureka.server.enable-self-preservation=false
#eureka定期扫描节点的时间
eureka.server.eviction-interval-timer-in-ms=1000   


# 设置IO线程数, 它主要执行非阻塞的任务,它们会负责多个连接, 默认设置每个CPU核心一个线程
server.undertow.io-threads=19
# 阻塞任务线程池, 当执行类似servlet请求阻塞操作, undertow会从这个线程池中取得线程,它的值设置取决于系统的负载
server.undertow.worker-threads=20
# 以下的配置会影响buffer,这些buffer会用于服务器连接的IO操作,有点类似netty的池化内存管理
server.undertow.buffer-size=1024
# 每个区分配的buffer数量 , 所以pool的大小是buffer-size * buffers-per-region
server.undertow.buffers-per-region=2048
# 是否分配的直接内存
server.undertow.direct-buffers=true
#最大分区数量
server.undertow.max-regions=10
#socket-binding="http"，保持长连接
server.undertow.always-set-keep-alive=true
```

##zuul配置
```
#spring.application.name=api-gateway-zuul
eureka.instance.appname=api-gateway-zuul
eureka.instance.virtualHostName=api-gateway-zuul
eureka.instance.secureVirtualHostName=api-gateway-zuul

server.port=8869
#将自己注册到eureka注册中心，单节点关闭 
eureka.client.registerWithEureka=true
#从注册中心获取注册信息，单节点关闭 
eureka.client.fetchRegistry=true
#从注册中心获取注册信息的时间间隔
eureka.client.registryFetchIntervalSeconds=10
#非安全通信端口
#eureka.instance.nonSecurePort=80
#是否启用非安全端口接受请求
#eureka.instance.nonSecurePortEnabled=true
#安全通信端口
#eureka.instance.securePort=443
#是否启用安全端口接受请求
#eureka.instance.securePortEnabled=true
#是否优先使用IP地址作为主机名的标识，默认false
#eureka.instance.preferIpAddress=false
#eureka节点定时续约时间，默认30
eureka.instance.leaseRenewalIntervalInSeconds=15
#eureka节点剔除时间，默认90
eureka.instance.leaseExpirationDurationInSeconds=45
#从注册中心获取注册信息的时间间隔
eureka.client.registryFetchIntervalSeconds=5
eureka.client.eureka-server-connect-timeout-seconds=15
eureka.client.eureka-server-read-timeout-seconds=10
eureka.instance.instance-id=${spring.cloud.client.ipAddress}:ribbon-service-provider-peer:${server.port}
#注册到另外两个节点，实现集群
eureka.client.serviceUrl.defaultZone=http://localhost:8887/eureka/,http://localhost:8888/eureka/,http://localhost:8889/eureka/

logging.level.com.kyle.client.feign.inter.HelloServiceFeign=DEBUG

# 设置IO线程数, 它主要执行非阻塞的任务,它们会负责多个连接, 默认设置每个CPU核心一个线程
server.undertow.io-threads=19
# 阻塞任务线程池, 当执行类似servlet请求阻塞操作, undertow会从这个线程池中取得线程,它的值设置取决于系统的负载
server.undertow.worker-threads=20
# 以下的配置会影响buffer,这些buffer会用于服务器连接的IO操作,有点类似netty的池化内存管理
server.undertow.buffer-size=1024
# 每个区分配的buffer数量 , 所以pool的大小是buffer-size * buffers-per-region
server.undertow.buffers-per-region=2048
# 是否分配的直接内存
server.undertow.direct-buffers=true
#最大分区数量
server.undertow.max-regions=10
#socket-binding="http"，保持长连接
server.undertow.always-set-keep-alive=true

#开启hystrix容错，默认是不开启的，目前应用还未加入容错机制
#feign.hystrix.enabled=true
hystrix.command.default.execution.timeout.enabled=true
#设置hystrix超时时间
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000
#熔断配置
#熔断窗口时间的标本数量
#hystrix.command.default.circuitBreaker.requestVolumeThreshold=20
#熔断时间
#hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds=5000
#容器窗口的错误占比，百分制
#hystrix.command.default.circuitBreaker.errorThresholdPercentage=50
#熔断窗口时间
#hystrix.command.default.metrics.rollingStats.timeInMilliseconds=10000
#hystrix窗口期内监控上报的并发上限
#hystrix.command.default.metrics.rollingPercentile.bucketSize=100

#重试机制开启为true，关闭为false
spring.cloud.loadbalancer.retry.enabled=true
ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.ZoneAvoidanceRule
#以下配置全局有效
ribbon.eureka.enabled=true
#建立连接超时时间，原1000
ribbon.ConnectTimeout=60000
#请求处理的超时时间，5分钟
ribbon.ReadTimeout=60000
#所有操作都重试
ribbon.OkToRetryOnAllOperations=true
#重试发生，更换节点数最大值
ribbon.MaxAutoRetriesNextServer=10
#单个节点重试最大值
ribbon.MaxAutoRetries=1

zuul.routes.api-a.path=/api-a/**
zuul.routes.api-a.serviceId=hello-service-provider
zuul.routes.api-b.path=/api-b/**
zuul.routes.api-b.serviceId=feign-service-provider
#zuul.okhttp.enabled=true
zuul.semaphore.max-semaphores=500
#zuul路由最大连接数
zuul.host.maxTotalConnections=200
#每个路由最大线程数
zuul.host.maxPerRouteConnections=1000
zuul.host.max-per-route-connections=5
zuul.host.socket-timeout-millis=60000
zuul.host.connect-timeout-millis=60000
```
##feign配置
```
#spring.application.name=feign-service-provider
eureka.instance.appname=feign-service-provider
eureka.instance.virtualHostName=feign-service-provider
eureka.instance.secureVirtualHostName=feign-service-provider

server.port=8868
#将自己注册到eureka注册中心，单节点关闭 
eureka.client.registerWithEureka=true
#从注册中心获取注册信息，单节点关闭 
eureka.client.fetchRegistry=true
#从注册中心获取注册信息的时间间隔
eureka.client.registryFetchIntervalSeconds=10
#非安全通信端口
#eureka.instance.nonSecurePort=80
#是否启用非安全端口接受请求
#eureka.instance.nonSecurePortEnabled=true
#安全通信端口
#eureka.instance.securePort=443
#是否启用安全端口接受请求
#eureka.instance.securePortEnabled=true
#是否优先使用IP地址作为主机名的标识，默认false
#eureka.instance.preferIpAddress=false
#eureka节点定时续约时间，默认30
eureka.instance.leaseRenewalIntervalInSeconds=15
#eureka节点剔除时间，默认90
eureka.instance.leaseExpirationDurationInSeconds=45
#从注册中心获取注册信息的时间间隔
eureka.client.registryFetchIntervalSeconds=5
eureka.client.eureka-server-connect-timeout-seconds=15
eureka.client.eureka-server-read-timeout-seconds=10
eureka.instance.instance-id=${spring.cloud.client.ipAddress}:ribbon-service-provider-peer:${server.port}
#注册到另外两个节点，实现集群
eureka.client.serviceUrl.defaultZone=http://localhost:8887/eureka/,http://localhost:8888/eureka/,http://localhost:8889/eureka/

logging.level.com.kyle.client.feign.inter.HelloServiceFeign=DEBUG

# 设置IO线程数, 它主要执行非阻塞的任务,它们会负责多个连接, 默认设置每个CPU核心一个线程
server.undertow.io-threads=19
# 阻塞任务线程池, 当执行类似servlet请求阻塞操作, undertow会从这个线程池中取得线程,它的值设置取决于系统的负载
server.undertow.worker-threads=20
# 以下的配置会影响buffer,这些buffer会用于服务器连接的IO操作,有点类似netty的池化内存管理
server.undertow.buffer-size=1024
# 每个区分配的buffer数量 , 所以pool的大小是buffer-size * buffers-per-region
server.undertow.buffers-per-region=2048
# 是否分配的直接内存
server.undertow.direct-buffers=true
#最大分区数量
server.undertow.max-regions=10
#socket-binding="http"，保持长连接
server.undertow.always-set-keep-alive=true

#开启hystrix容错，默认是不开启的，目前应用还未加入容错机制
#feign.hystrix.enabled=true
hystrix.command.default.execution.timeout.enabled=true
#设置hystrix超时时间
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000
#熔断配置
#熔断窗口时间的标本数量
#hystrix.command.default.circuitBreaker.requestVolumeThreshold=20
#熔断时间
#hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds=5000
#容器窗口的错误占比，百分制
#hystrix.command.default.circuitBreaker.errorThresholdPercentage=50
#熔断窗口时间
#hystrix.command.default.metrics.rollingStats.timeInMilliseconds=10000
#hystrix窗口期内监控上报的并发上限
#hystrix.command.default.metrics.rollingPercentile.bucketSize=100

#重试机制开启为true，关闭为false
spring.cloud.loadbalancer.retry.enabled=true
ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.ZoneAvoidanceRule
#以下配置全局有效
ribbon.eureka.enabled=true
#建立连接超时时间，原1000
ribbon.ConnectTimeout=60000
#请求处理的超时时间，5分钟
ribbon.ReadTimeout=60000
#所有操作都重试
ribbon.OkToRetryOnAllOperations=true
#重试发生，更换节点数最大值
ribbon.MaxAutoRetriesNextServer=10
#单个节点重试最大值
ribbon.MaxAutoRetries=1

###feignclient配置
#对当前实例的重试次数
hello-service-provider.ribbon.MaxAutoRetries=1
#切换实例的重试次数
hello-service-provider.ribbon.MaxAutoRetriesNextServer=2
#对所有操作请求都进行重试
hello-service-provider.ribbon.OkToRetryOnAllOperations=true
#
hello-service-provider.ribbon.ServerListRefreshInterval=2000
#请求连接的超时时间
hello-service-provider.ribbon.ConnectTimeout=3000
#请求处理的超时时间
hello-service-provider.ribbon.ReadTimeout=3000

hello-service-provider.ribbon.listOfServers=localhost:8877,localhost:8878

hello-service-provider.ribbon.EnablePrimeConnections=false
```
## ribbon配置
```
#spring.application.name=ribbon-service-provider
eureka.instance.appname=ribbon-service-provider
eureka.instance.virtualHostName=ribbon-service-provider
eureka.instance.secureVirtualHostName=ribbon-service-provider

server.port=8869
#将自己注册到eureka注册中心，单节点关闭 
eureka.client.registerWithEureka=true
#从注册中心获取注册信息，单节点关闭 
eureka.client.fetchRegistry=true
#从注册中心获取注册信息的时间间隔
eureka.client.registryFetchIntervalSeconds=10
#非安全通信端口
#eureka.instance.nonSecurePort=80
#是否启用非安全端口接受请求
#eureka.instance.nonSecurePortEnabled=true
#安全通信端口
#eureka.instance.securePort=443
#是否启用安全端口接受请求
#eureka.instance.securePortEnabled=true
#是否优先使用IP地址作为主机名的标识，默认false
#eureka.instance.preferIpAddress=false
#eureka节点定时续约时间，默认30
eureka.instance.leaseRenewalIntervalInSeconds=15
#eureka节点剔除时间，默认90
eureka.instance.leaseExpirationDurationInSeconds=45
#从注册中心获取注册信息的时间间隔
eureka.client.registryFetchIntervalSeconds=5
eureka.client.eureka-server-connect-timeout-seconds=15
eureka.client.eureka-server-read-timeout-seconds=10
eureka.instance.instance-id=${spring.cloud.client.ipAddress}:ribbon-service-provider-peer:${server.port}
#注册到另外两个节点，实现集群
eureka.client.serviceUrl.defaultZone=http://localhost:8887/eureka/,http://localhost:8888/eureka/,http://localhost:8889/eureka/


# 设置IO线程数, 它主要执行非阻塞的任务,它们会负责多个连接, 默认设置每个CPU核心一个线程
server.undertow.io-threads=19
# 阻塞任务线程池, 当执行类似servlet请求阻塞操作, undertow会从这个线程池中取得线程,它的值设置取决于系统的负载
server.undertow.worker-threads=20
# 以下的配置会影响buffer,这些buffer会用于服务器连接的IO操作,有点类似netty的池化内存管理
server.undertow.buffer-size=1024
# 每个区分配的buffer数量 , 所以pool的大小是buffer-size * buffers-per-region
server.undertow.buffers-per-region=2048
# 是否分配的直接内存
server.undertow.direct-buffers=true
#最大分区数量
server.undertow.max-regions=10
#socket-binding="http"，保持长连接
server.undertow.always-set-keep-alive=true

#开启hystrix容错，默认是不开启的，目前应用还未加入容错机制
#feign.hystrix.enabled=true
hystrix.command.default.execution.timeout.enabled=true
#设置hystrix超时时间
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000
#熔断配置
#熔断窗口时间的标本数量
#hystrix.command.default.circuitBreaker.requestVolumeThreshold=20
#熔断时间
#hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds=5000
#容器窗口的错误占比，百分制
#hystrix.command.default.circuitBreaker.errorThresholdPercentage=50
#熔断窗口时间
#hystrix.command.default.metrics.rollingStats.timeInMilliseconds=10000
#hystrix窗口期内监控上报的并发上限
#hystrix.command.default.metrics.rollingPercentile.bucketSize=100

#重试机制开启为true，关闭为false
spring.cloud.loadbalancer.retry.enabled=true
#以下配置全局有效
ribbon.eureka.enabled=true
#建立连接超时时间，原1000
ribbon.ConnectTimeout=60000
#请求处理的超时时间，5分钟
ribbon.ReadTimeout=60000
#所有操作都重试
ribbon.OkToRetryOnAllOperations=true
#重试发生，更换节点数最大值
ribbon.MaxAutoRetriesNextServer=10
#单个节点重试最大值
ribbon.MaxAutoRetries=1
```
