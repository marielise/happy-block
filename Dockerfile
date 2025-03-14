FROM openjdk:17
VOLUME /tmp
ADD target/your-app.jar app.jar
RUN mkdir /wallets
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]