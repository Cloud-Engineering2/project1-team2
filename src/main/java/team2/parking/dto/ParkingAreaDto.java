/* ParkingAreaDto.java
 * Parking Lot Management Service
 * 주차 공간 DTO
 * 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 고민정    2024.12.12   ParkingArea 엔티티의 DTO 생성
 * ========================================================
 */

package team2.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import team2.parking.entity.ParkingArea;
import team2.parking.entity.Vehicle;
import team2.parking.entity.constant.VehicleType;

@AllArgsConstructor
@ToString
@Getter
public class ParkingAreaDto {

    private Integer id; // 주차 공간 ID
    private String location; // 주차 공간 위치
    private Boolean inUse; // 주차 공간 사용 여부
    
    public static ParkingAreaDto of(String location, Boolean inUse) { // ParkingAreaDTO 생성 : of
        return ParkingAreaDto.of(null, location, inUse);
    }

    public static ParkingAreaDto of(Integer id, String location, Boolean inUse) { // ParkingAreaDTO 생성 : of (All)
        return new ParkingAreaDto(id, location, inUse);
    }

    public static ParkingAreaDto from(ParkingArea parkingArea) { // Entity -> DTO
        return new ParkingAreaDto(
        		parkingArea.getAreaId(),
        		parkingArea.getLocation(),
        		parkingArea.getInUse()
        );
    }

    public ParkingArea toEntity() { // DTO -> Entity
        return ParkingArea.of(location, inUse);
    }

}
