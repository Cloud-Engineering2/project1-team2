/* VehicleType.java
 * Parking Lot Management Service
 * VehicleType ; 차종 정리 - 열거형 클래스
 * 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
 * 최종 수정 날짜 : 날짜
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 이홍비    2024.12.12   최초 작성 : DB 설계 기반 차종 작성
 * ========================================================
 */


package team2.parking.entity.constant;

import team2.parking.entity.Vehicle;

import java.util.Arrays;

public enum VehicleType {

    SEDAN, // 세단
    HATCHBACK, // 해치백
    CONVERTIBLE, // 컨버터블
    SUV, // SPORT UTILITY VEHICLE
    COUPE, // 쿠페
    WAGON, // 왜건
    RV, // RECREATIONAL VEHICLE
    VAN, // 벤
    TRUCK; // 트럭


    public static VehicleType getInstance(String vehicleType) { // vehicleType 의 값을 문자열로 찾는 함수

        return Arrays.stream(VehicleType.values()) // VehicleType 값 -> 배열 -> 스트림
                .filter(type -> type.name().equals(vehicleType)) // vehicleType 과 type(VehicleType) 값 일치 여부로 정제
                .findFirst() // 정제된 것에서 첫 번째 요소 반환
                .orElseThrow(); // 값 x => 예외
    }


}

