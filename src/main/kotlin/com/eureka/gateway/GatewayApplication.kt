package com.eureka.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestClient

@SpringBootApplication(exclude = [EurekaClientAutoConfiguration::class])
class GatewayApplication {

    @Bean
    @LoadBalanced
    fun restClient(): RestClient {
        return RestClient.builder().build()
    }

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route("face-recognition-api") { r ->
                r.path("/api/v1/face/**")
                    .uri("lb://face-recognition-api")
            }
            .route("swagger") { r ->
                r.path("/swagger-ui/**", "/v3/api-docs/**", "/")
                    .uri("lb://face-recognition-api")
            }
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}