#Build avec Maven
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copie du projet
COPY ndarray/pom.xml ./pom.xml
COPY ndarray/src ./src

# Compile et package
RUN mvn clean package -DskipTests

#Image légère pour exécuter
FROM eclipse-temurin:17-jre

WORKDIR /app

# Récupère le JAR compilé depuis l'étape de build
COPY --from=build /app/target/ndarray-1.0-SNAPSHOT.jar ./ndarray.jar

# Lance la démo
CMD ["java", "-cp", "ndarray.jar", "com.example.App"]