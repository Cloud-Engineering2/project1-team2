/* VehicleDto.java
 * Parking Lot Management Service
 * VehicleDto ; 차량 정보 dto
 * 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 이홍비    2024.12.12   최초 작성 : Vehicle 기반 dto 작성
 * 고민정	   2024.12.12   updateVid - ParkingRecord 생성 시, Entity의 id가 DTO의 id에 반영되도록
 * ========================================================
 */

package team2.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import team2.parking.entity.Vehicle;
import team2.parking.entity.constant.VehicleType;

@AllArgsConstructor
@ToString
@Getter
public class VehicleDto {

    private Integer id; // 차량 고유 id
    private String number; // 차량 번호
    private VehicleType type; // 차종
    private String ownerTel; // 차주 번호
    private Boolean isBlacklisted; // 블랙리스트 등록 여부

    public static VehicleDto of(String number, VehicleType type, String ownerTel, Boolean isBlacklisted) { // static factory method - VehicleDto 객체 생성
        return VehicleDto.of(null, number, type, ownerTel, isBlacklisted);
    }

    public static VehicleDto of(Integer id, String number, VehicleType type, String ownerTel, Boolean isBlacklisted) { // static factory method - VehicleDto 객체 생성
        return new VehicleDto(id, number, type, ownerTel, isBlacklisted);
    }

    public static VehicleDto from(Vehicle vehicle) { // Vehicle entity -> VehicleDto
        return new VehicleDto(
                vehicle.getVId(),
                vehicle.getVNumber(),
                vehicle.getVType(),
                vehicle.getOwnerTel(),
                vehicle.getIsBlacklisted()
        );
    }

    public Vehicle toEntity() { // VehicleDto -> Vehicle
        return Vehicle.of(number, type, ownerTel, isBlacklisted);
    }
    
    public void updateVid(Integer vid) {
    	this.id = vid;
    }

}
