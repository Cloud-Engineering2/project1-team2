/* ParkingRecord.java
* Parking Lot Management Service
* 주차 기록을 관리하는 엔티티
* 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
* 최종 수정 날짜 : 2024-12-12
*
* ========================================================
* 프로그램 수정 / 보완 이력
* ========================================================
* 작업자       날짜       수정 / 보완 내용
* ========================================================
* 허선호    2024.12.12    최초 작성
* 고민정	  2024.12.12    ParkingArea 연관 관계 필드 타입 변경 (Integer -> ParkingArea)
* 이홍비    2024.12.12    vehicleId 관련 주석 변경
* 고민정    2024.12.12	updateEntryTime 추가, updateExitRecord 수정
* ========================================================
*/ 


package team2.parking.entity;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.ToString;

@Getter
@Entity
@ToString
public class ParkingRecord { // 주차 기록 엔티티
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer recordId; // ID
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, updatable = false, name = "vehicle_id", referencedColumnName = "vehicle_id")
	protected Vehicle vehicleId; // 차량 고유 id 참조
	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	protected LocalDateTime entryTime; //입차 시각
	
	protected LocalDateTime exitTime; //출차 시각
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, updatable = false, name = "area_id", referencedColumnName = "area_id")
	protected ParkingArea areaId; // 주차 위치
	
	protected Integer parkingFee; //주차 요금
	
	protected ParkingRecord() {}
	
	private ParkingRecord(Vehicle vehicleId, ParkingArea areaId) { //생성자
		this.vehicleId = vehicleId;
		this.areaId = areaId;
	}
	
	public static ParkingRecord of(Vehicle vehicleId, ParkingArea areaId) { //입차 시 사용하는 of 메서드
		return new ParkingRecord(vehicleId, areaId);
	}
	
	public void updateEntryTime(LocalDateTime entryTime) {
		this.entryTime = entryTime;
	}
	
	public void updateExitRecord() { //출차 시 사용하는 updateExitRecord 메서드
		this.exitTime = LocalDateTime.now(); // 출차 시각
		
		Duration duration = Duration.between(this.entryTime, this.exitTime); // 출차 - 입차 시각
		int time = (int)(duration.toMinutes()/10); 

		this.parkingFee = time * 500; // 10분에 500원
	}

}
