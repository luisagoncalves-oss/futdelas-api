## Visão geral
API em Spring Boot que consulta dados de futebol (integração com SofaScore via RapidAPI) e expõe endpoints para torneios e times. Aplicação modularizada em controllers, services, clientes externos e modelos.

## Requisitos
- Java 17
- Maven (ou usar o wrapper `./mvnw`)
- Variáveis de ambiente para API externa:
  - `EXTERNAL_API_URL` (opcional)
  - `EXTERNAL_API_HOST` (opcional)
  - `EXTERNAL_API_KEY` (obrigatório para chamadas ao SofaScore)

As propriedades estão carregadas em [src/main/resources/application.properties](src/main/resources/application.properties) e podem ser sobrescritas por variáveis de ambiente.

## Como rodar localmente
Compilar e executar com o wrapper Maven:
```sh
./mvnw clean package
java -jar target/futdelas-api-*.jar
```
Ou executar diretamente em desenvolvimento:

```sh
./mvnw spring-boot:run
```