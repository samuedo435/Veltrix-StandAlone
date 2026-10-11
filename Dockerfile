# Usa Java 21 correspondiente a la versión configurada en pom.xml
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia el JAR generado en la carpeta target
COPY target/*.jar app.jar

# Copia los datos reales de la base de datos H2 (veltrixdb.mv.db, etc.)
COPY data/ ./data/

# Volumen para persistencia y sincronización de datos
VOLUME /app/data

# El puerto por defecto de Spring Boot
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]