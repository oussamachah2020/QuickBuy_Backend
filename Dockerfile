FROM openjdk:17

# Add Maintainer Info

# Add a volume pointing to /tmp
VOLUME /tmp

# Expose port 8080 to the outside
EXPOSE 8080

# Argument for the JAR file location
ARG JAR_FILE=target/QuickBuy-1.0.0.jar

# Copy the application JAR to the container
COPY ${JAR_FILE} app.jar

# Run the application
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
