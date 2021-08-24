package com.zh.springcloud.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
不使用yml进行配置，
使用java代码进行网关配置，访问百度新闻 http://news.baidu.com/guonei
 */
@Configuration
public class GateWayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){

        RouteLocatorBuilder.Builder routes=routeLocatorBuilder.routes();
        routes.route("path_route_guoni",
                r -> r.path("/guonei").uri("http://news.baidu.com/guonei")).build();  //相当于yml配置文件中的id

        return  routes.build();
    }


    @Bean
    public RouteLocator customRouteLocator1(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder route=routeLocatorBuilder.routes();
        route.route("path_route_guoji",r->r.path("/guoji").uri("http://news.baidu.com/guoji")).build();
        return route.build();
    }

}
