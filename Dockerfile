# Usa una imagen de Java
FROM eclipse-temurin:17

# Crea carpeta de trabajo
WORKDIR /app

# Copia el jar generado
COPY target/*.jar app.jar

# Expone el puerto donde corre tu app
EXPOSE 8086

# Comando para iniciar el backend
ENTRYPOINT ["java", "-jar", "app.jar"]
