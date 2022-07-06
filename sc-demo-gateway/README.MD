## 基本介绍
https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#glossary  

本项目提供了一个构建在 Spring 生态之上的 API Gateway， 包括：Spring 5、Spring Boot 2 和 Project Reactor。Spring Cloud Gateway
旨在提供一种简单而有效的方式来路由到 API，并为它们提供横切关注点，例如：安全性、监控/指标和弹性。

## 核心概念

- Route：网关的基本构建块。它由 ID、目标 URI、谓词集合和过滤器集合定义。如果聚合谓词为真，则匹配路由。

- 断言：这是一个Java 8 函数谓词。输入类型是Spring Framework的ServerWebExchange。这使您可以匹配来自 HTTP 请求的任何内容，例如标头或参数。

- 过滤器： 一个标准的Spring webFilter，Spring Cloud Gateway中的Filter分为两种类型，分别是Gateway Filter和Global Filter。过滤器Filter可以对请求和响应进行处理。

## 依赖配置

```aidl
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```
注意SpringCloud Gateway使用的web框架为webflux，和SpringMVC不兼容。引入的限流组件是hystrix。redis底层不再使用jedis，而是lettuce。


## predicates Factory
```aidl
   - After=2017-01-20T17:42:47.789-07:00[America/Denver]
   - Before=2017-01-20T17:42:47.789-07:00[America/Denver]
   - Between=2017-01-20T17:42:47.789-07:00[America/Denver], 2017-01-21T17:42:47.789-07:00[America/Denver]
   - Cookie=chocolate, ch.p
   - Header=X-Request-Id, \d+
   - Host=**.somehost.org,**.anotherhost.org
   - Method=GET,POST
   - Path=/red/{segment},/blue/{segment}
   - Query=green
   - Query=token,abc.       //参数名称,参数正则规则 
   - RemoteAddr=192.168.1.1/24
```

## filters  Factory
基本配置
```aidl
   - AddRequestHeader=X-Request-red, blue
     predicates:
        - Path=/red/{segment}
        filters:
        - AddRequestHeader=X-Request-Red, Blue-{segment}
        - AddRequestParameter=foo, bar-{segment}
   - AddRequestParameter=red, blue
   - AddRequestParameter=foo, bar-{segment}
```
熔断配置
```aidl
spring:
  cloud:
    gateway:
      routes:
      - id: ingredients
        uri: lb://ingredients
        predicates:
        - Path=//ingredients/**
        filters:
        - name: CircuitBreaker
          args:
            name: fetchIngredients
            fallbackUri: forward:/fallback
      - id: ingredients-fallback
        uri: http://localhost:9994
        predicates:
        - Path=/fallback
        
//匹配代码
@Bean
public RouteLocator routes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("circuitbreaker_route", r -> r.path("/consumingServiceEndpoint")
            .filters(f -> f.circuitBreaker(c -> c.name("myCircuitBreaker").fallbackUri("forward:/inCaseOfFailureUseThis").addStatusCode("INTERNAL_SERVER_ERROR"))
                .rewritePath("/consumingServiceEndpoint", "/backingServiceEndpoint")).uri("lb://backing-service:8088")
        .build();
}
```

限流配置



## 常见的问题
- Failed to bind properties under 'spring.cloud.gateway'  
检查是否单词写错了
- failed to convert java.lang.String to org.springframework.cloud.gateway.handler.predicate.  
检查对应的地方配置是否没有空格
- were left unbound.  
可能是配置predicates参数是用的：不是=号  
- Unable to find RoutePredicateFactory with name path  
-Path名字首字母大写Path

