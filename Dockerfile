FROM openjdk:17
EXPOSE 8080
ADD target/binar-kosthub-server.jar binar-kosthub-server.jar
ENTRYPOINT ["java","-jar","/binar-kosthub-server.jar"]