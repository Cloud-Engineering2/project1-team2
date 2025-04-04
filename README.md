
# 🅿️ 주차 관리 서비스
> 주차장 이용자와 관리자를 위한 웹 기반 주차 관리 시스템입니다.  

<br>

## 📌 프로젝트 개요
> 차량 등록, 주차 공간 현황 조회, 주차비 정산, 블랙 리스트 등록 등의 기능을 통해 주차장의 효율적인 운영과 체계적인 관리 환경을 제공합니다.
> 
> [📄 발표 자료](https://github.com/Cloud-Engineering2/project1-team2/blob/main/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B81%20-%202%ED%8C%80%20%EC%82%B0%EC%B6%9C%EB%AC%BC%20-%20%EC%B5%9C%EC%A2%85.pdf)

<br>

## 💻 기술 스택
### Backend
- **언어** : Java 17  
- **프레임워크** : Spring Boot 3.4.0
- **DB 및 데이터 관리** : MySQL, Spring Data JPA
- **보안** : Spring Security 6
- **API** : REST API (API 문서화)
- **기타 라이브러리** : Lombok

### Frontend
- **언어** : JavaScript (ES6), HTML5, CSS3

### Infra
- **서버 및 배포** : VM (Git Clone → Docker Build → 컨테이너 실행)  
- **컨테이너 관리** : Docker  
- **데이터베이스** : MySQL (Docker 컨테이너)  

<br>

## ⚙️ 주요 기능 (Features)
- **서비스**
  - 실시간 주차 현황 조회 (구역별 주차 가능 여부)
  - 주차 중인 차량 조회 (차량 번호 기반)
- **관리자**
  - 관리자 로그인
  - 실시간 주차 현황 관리
  - 차량 입·출차 관리
  - 주차 기록 조회
  - 차량 정보 조회
  - 블랙리스트 등록 및 해제
  
<br>

## ☁️ 배포 절차 (Deployment Steps)
1. **Ubuntu 가상 머신 설치**
   - Ubuntu 이미지 기반으로 가상머신 구성
   - 사용자 계정: `lion2`

2. **서버 기본 세팅**
   - Port forwarding 설정  
     → 쉘 접속 / Spring Boot 서버 포트 / MySQL 포트 개방

3. **Docker 설치 및 구성**
   - Docker 설치 및 `lion2` 사용자에게 권한 부여
   - Docker 네트워크 생성 (`lion2` 네트워크)

4. **MySQL 실행**
   - MySQL 도커 이미지 설치 및 컨테이너 실행
   - 바인드 마운트를 통한 데이터 유지 (`/home/lion2/data/mysql`)

5. **Java & Maven 설치**
   - Java 17 설치
   - Maven 설치 및 환경 변수 설정

6. **애플리케이션 배포**
   - GitHub 저장소 `clone`
   - `mvn package` 명령어로 JAR 파일 생성
   - `Dockerfile` 작성 후, Docker 이미지 빌드 및 컨테이너 실행

<br>

## 💻 개발 환경 (Development Environment)
- **Java 17**  
- **Spring Boot 3.4.0**  
- **Maven**
- **MySQL (Docker 기반 실행)**  

<br>

## 👥 조원
- 조장 : **박청조** ( king01286@naver.com )
- 조원 : **고민정** ( komj36@gmail.com )
- 조원 : **이홍비** ( redrain@yu.ac.kr )
- 조원 : **허선호** ( heoseonho@gmail.com )

<br>

## 📄 application.properties

```
spring.application.name=parking

#DB
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

#url - ubuntu
spring.datasource.url=jdbc:mysql://mysql-server:3306/parking?serverTimezone=Asia/Seoul

#url - windows (ubuntu docker container mysql-server)
#spring.datasource.url=jdbc:mysql://localhost:3333/parking?serverTimezone=Asia/Seoul

#url - windows
#spring.datasource.url=jdbc:mysql://localhost:3306/parking?serverTimezone=Asia/Seoul

spring.datasource.username=semi_lion2
spring.datasource.password=semi_lion2


#JPA
spring.jpa.generate-ddl=false
spring.jpa.hibernate.ddl-auto=none
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true


# Thymeleaf
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.cache=false
spring.thymeleaf.check-template-location=true

```
