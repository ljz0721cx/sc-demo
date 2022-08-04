# sc-demo-thclouds-agent

## agent 规划支持功能
### 1、限流限频
#### 1.1、对外服务提供
#### 1.2、服务之间调用
### 2、支持链路追踪
### 3、记录访问监控
### 4、支持集群隔离配置
### 5、支持地址冲突和异常调用的分析

## agent 架构


## 使用方式
打包
  mvn clean package

修改conf下的配置



## 插件定义


## 插件加载


## 基于plugin的形式对外提供字节码插装，已经支持plugin

1、拦截feign熔断限流                       pluginGroup.add(new FeignPlugin());
2、拦截@conntroller注解的类熔断限流          pluginGroup.add(new ControllerPlugin());
3、拦截WebFlux的DispatcherHandler熔断限流   pluginGroup.add(new DispatcherHandlerPlugin());
4、解决地址冲突，异常提示返回                 pluginGroup.add(new HandlerResultPlugin());






  

