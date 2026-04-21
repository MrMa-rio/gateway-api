package com.eureka.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestClient

@SpringBootApplication
@EnableDiscoveryClient
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
            .route("swagger-ui") { r ->
                r.path("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**")
                    .uri("lb://face-recognition-api")
            }
            .route("swagger-root") { r ->
                r.path("/")
                    .uri("lb://face-recognition-api")
            }
            .build()
    }

    @Bean
    fun corsWebFilter(): org.springframework.web.cors.reactive.CorsWebFilter {
        val config = org.springframework.web.cors.CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOriginPattern("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")

        val source = org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)

        return org.springframework.web.cors.reactive.CorsWebFilter(source)
    }
}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}