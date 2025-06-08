# Use uma imagem do OpenJDK como base para a construção
FROM openjdk:11-jdk-slim AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo pom.xml para o container
COPY pom.xml ./ 

# Baixa as dependências do Maven (sem rodar testes)
RUN ./mvnw dependency:go-offline

# Copia o código fonte para o container
COPY src ./src

# Executa o Maven para compilar o projeto (pule os testes para acelerar o processo)
RUN ./mvnw clean install -DskipTests

# Exponha a porta em que o Spring Boot vai rodar
EXPOSE 8080

# Comando para rodar o Spring Boot dentro do container
CMD ["./mvnw", "spring-boot:run"]
