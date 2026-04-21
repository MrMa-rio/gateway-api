# Gateway Service (API Gateway & Load Balancer)

Porta de entrada unificada para os microserviços da arquitetura.

## Configurações Principais

- **Porta**: 8080
- **Profiles**: `default`, `local`, `prod`
- **Spring Boot**: 3.5.7 (Kotlin 2.2.21)
- **JDK**: 24

## Funcionalidades

- **Routing Dinâmico**: Roteia requisições para os serviços registrados no Eureka.
- **Load Balancing**: Balanceamento de carga para instâncias escaláveis.
- **OpenAPI Gateway**: Agrega documentação do Swagger centralizado.
- **Circuit Breaker & Resilience**: Camada básica de resiliência integrada ao gateway.

## Rotas Mapeadas

- `lb://face-recognition-api`: Redireciona para o serviço de reconhecimento facial em `/api/v1/face/**`.
- `lb://face-recognition-api` (Swagger): Centraliza Swagger-UI em `/`, `/swagger-ui/**`, e `/v3/api-docs/**`.

## Endpoints de Monitoramento

- `/actuator/gateway/routes`: Lista todas as rotas configuradas.
- `/actuator/health`: Status de saúde.

## Dependências Relevantes

- `spring-cloud-starter-gateway`
- `spring-cloud-starter-loadbalancer`
- `spring-cloud-starter-netflix-eureka-client`
- `springdoc-openapi-starter-webflux-ui`
