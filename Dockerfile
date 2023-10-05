# Usa una imagen base de Java 8
FROM openjdk:8

# Copia el archivo JAR y las dependencias a la imagen
COPY . /app

# Establece el directorio de trabajo en /app
WORKDIR /app

# Comando para ejecutar la aplicación (ajusta el nombre del archivo JAR si es diferente)
CMD ["java", "-jar", "./server/Server.jar"]
