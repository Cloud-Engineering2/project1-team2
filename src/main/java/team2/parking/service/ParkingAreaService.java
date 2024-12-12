/* ParkingAreaService.java
* Parking Lot Management Service
* 주차 구역 서비스
* 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
* 최종 수정 날짜 : 2024.12.12
*
* ========================================================
* 프로그램 수정 / 보완 이력
* ========================================================
* 작업자       날짜       수정 / 보완 내용
* ========================================================
* 고민정    2024.12.12    ParkingArea 서비스 생성
* ========================================================
*/ 

package team2.parking.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import team2.parking.dto.ParkingAreaDto;
import team2.parking.repository.ParkingAreaRepository;

@RequiredArgsConstructor
@Service
public class ParkingAreaService {
	
	private final ParkingAreaRepository parkingAreaRepository;
	
	@Transactional
	public ParkingAreaDto getArea(String location) {
		return parkingAreaRepository.findByLocation(location)
									.map(ParkingAreaDto::from)
									.orElseThrow();
		
	}
}
