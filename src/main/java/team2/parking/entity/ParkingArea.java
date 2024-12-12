/* ParkingArea.java
* Parking Lot Management Service
* 주차 구역(장소) Entity
* 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
* 최종 수정 날짜 : 2024.12.12
*
* ========================================================
* 프로그램 수정 / 보완 이력
* ========================================================
* 작업자       날짜       수정 / 보완 내용
* ========================================================
* 고민정    2024.12.12    ParkingArea Entity 작성
* ========================================================
*/ 

package team2.parking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;



@Getter
@Entity
public class ParkingArea {
	
	
	@Id
	@Column(nullable=false, name="area_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer areaId; // PK 
	
	
	@Column(nullable=false, length=10)
	private String location; // 주차 공간 위치 : A-01 
	
	
	@Column(name="in_use", nullable=false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean inUse;  // 주차 공간 사용 여부
	
	
	protected ParkingArea() {} // 기본 생성자
	
	private ParkingArea(String location, Boolean inUse) { //생성자
		this.location = location;
		this.inUse = inUse;
	}
	
	public static ParkingArea of(String location, Boolean inUse) { // of 메서드
		return new ParkingArea(location, inUse);
	}

	public void checkInUse(Boolean inUse) {
		this.inUse = inUse;
	}
}
