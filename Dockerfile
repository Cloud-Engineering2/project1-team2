# Dockerfile
# 최종 수정 일자 : 2024.12.13


FROM openjdk:17
COPY /target/parking-0.0.1-SNAPSHOT.jar parking.jar
ENTRYPOINT ["java", "-jar", "/parking.jar"]
