# Transaction Statistics

Este projeto é uma aplicação Java que fornece estatísticas de transações.

## Pré-requisitos

- Java 17 ou superior **ou** Docker instalado
- Maven para build local

## Executando com Docker

1. **Build do projeto:**

   ```sh
   mvn clean package
   ```

2. **Build da imagem Docker:**

   ```sh
   docker build -t transaction-statistics .
   ```

3. **Execute o container:**

   ```sh
   docker run -p 8090:8090 transaction-statistics
   ```

A aplicação estará disponível em [http://localhost:8090](http://localhost:8090).

**Observação:**  
O Dockerfile já está configurado para rodar a aplicação com um usuário não-root, seguindo boas práticas de segurança.

## Endpoints úteis

- **Swagger UI:**  
  [http://localhost:8090/swagger-ui.html](http://localhost:8090/swagger-ui.html)

- **Micrometer Metrics (execution.time):**  
  [http://localhost:8090/actuator/metrics/execution.time](http://localhost:8090/actuator/metrics/execution.time)

- **Actuator Health:**  
  [http://localhost:8090/actuator/health](http://localhost:8090/actuator/health)

## Executando localmente (sem Docker)

1. **Build do projeto:**

   ```sh
   mvn clean package
   ```

2. **Execute o JAR gerado:**

   ```sh
   java -jar target/transaction-statistics-0.0.1-SNAPSHOT.jar
   ```

A aplicação estará disponível em [http://localhost:8090](http://localhost:8090).

## Executando os testes

Para rodar os testes automatizados, utilize:

```sh
mvn test
```
