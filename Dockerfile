# Dockerfile
# 프로그램 수정
# 2024.12.13. Docker 작성
# 2024.12.15. 한국 표준시 설정 부분 추가 구현

FROM openjdk:17-slim

# 기준 시간 : 한국 표준시 (KST) => tzdata 설치 + 시간대 설정
# 설정 안 하면 UTC 기준 <=> 영국 표준시 기준
RUN apt-get update && apt-get install -y tzdata && \
    ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    echo "Asia/Seoul" > /etc/timezone

COPY /target/parking-0.0.1-SNAPSHOT.jar parking.jar
ENTRYPOINT ["java", "-jar", "/parking.jar"]
