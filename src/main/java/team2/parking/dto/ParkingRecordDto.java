/* ParkingRecordDto.java
* Parking Lot Management Service
* 주차 기록 DTO
* 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
* 최종 수정 날짜 : 2024.12.12
*
* ========================================================
* 프로그램 수정 / 보완 이력
* ========================================================
* 작업자       날짜       수정 / 보완 내용
* ========================================================
* 허선호    2024.12.12    ParkingRecord Dto 작성
* ========================================================
*/ 

package team2.parking.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import team2.parking.entity.ParkingRecord;

@AllArgsConstructor
@ToString
@Getter
public class ParkingRecordDto { // 주차 기록 DTO 클래스
	private Integer recordId;  // 기록 ID
	private Integer vehicleId; // 차량 ID
	private LocalDateTime entryTime; // 입차 시각
	private LocalDateTime exitTime; // 출차 시각
	private Integer areaId; // 주차장소 ID
	private Integer parkingFee; // 주차비
	
	public static ParkingRecordDto of(Integer vehicleId, Integer areaId) { // DTO 생성을 위한 메소드
		return ParkingRecordDto.of(null, vehicleId, null, null, areaId, null);
	}
	
	public static ParkingRecordDto of(Integer recordId, Integer vehicleId, LocalDateTime entryTime, LocalDateTime exitTime, Integer areaId, Integer parkingFee) {
		return new ParkingRecordDto(recordId, vehicleId, entryTime, exitTime, areaId, parkingFee);
	}
	
	
	public static ParkingRecordDto from(ParkingRecord parkingRecord) { // 엔티티를 DTO로 변환하는 메소드
		return new ParkingRecordDto(
				parkingRecord.getRecordId(),
				VehicleDto.from(parkingRecord.getVehicleId()), // 차량 DTO 참고
				parkingRecord.getEntryTime(),
				parkingRecord.getExitTime(),
				ParkingAreaDto.from(parkingRecord.getAreaId()), // 주차 구역 DTO 참고
				parkingRecord.getParkingFee()
			);	
	}
	
	public ParkingRecord toEntity() { // DTO를 엔티티로 변환하는 메소드
		return ParkingRecord.of(vehicleId, areaId);
	};
}
