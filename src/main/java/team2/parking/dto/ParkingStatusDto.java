/* ParkingStatusDto.java
 * Parking Lot Management Service
 * 주차현황 데이터 반환을 위한 DTO
 * 작성자 : semi_lion2 (고민정, 박청조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.13
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 박청조    2024.12.13   주차 현황 페이지에 반환할 주차 현황 데이터 DTO 생성
 *
 * ========================================================
 */

package team2.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import team2.parking.common.utils.FeeCalculator;
import team2.parking.entity.ParkingArea;
import team2.parking.entity.Vehicle;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
public class ParkingStatusDto {
    private Integer areaId;
    private String location;
    private boolean inUse;
    private String vehicleNumber;
    private String phoneNumber;
    private String parkingTime;
    private String parkingFee;

    // 미사용 공간 생성을 위한 생성자
    public ParkingStatusDto(String location) {
        this.location = location;
        this.inUse = false;
    }

    // 사용중인 공간 생성을 위한 생성자
    public ParkingStatusDto(ParkingArea area, Vehicle vehicle, LocalDateTime entryTime) {
        this.areaId = area.getAreaId();
        this.location = area.getLocation();
        this.inUse = area.getInUse();
        this.vehicleNumber = vehicle.getVNumber();
        this.phoneNumber = vehicle.getOwnerTel();
        this.parkingTime = formatTimeDifference(entryTime);
        this.parkingFee = FeeCalculator.calculateFee(entryTime) + "원";
    }

    // 주차 시간 표시를 위한 포매터 (ex. "20시간 15분")
    private static String formatTimeDifference(LocalDateTime entryTime) {
        LocalDateTime now = LocalDateTime.now();

        if (entryTime.isAfter(now)) {
            throw new IllegalArgumentException("입차 시간은 현재 시간보다 이후일 수 없습니다.");
        }

        // 두 시간의 차이를 Duration으로 계산
        Duration duration = Duration.between(entryTime, now);

        // 시간과 분 추출
        long totalMinutes = duration.toMinutes();
        long hours = totalMinutes / 60; // 전체 시간을 60으로 나눔 = 시간
        long minutes = totalMinutes % 60; // 나머지 = 분

        // 포맷팅된 문자열 반환
        return hours + "시간 " + minutes + "분";
    }
}