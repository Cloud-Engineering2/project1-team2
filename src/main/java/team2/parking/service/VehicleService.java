/* VehicleService.java
 * Parking Lot Management Service
 * Vehicle 의 Service
 * 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 고민정    2024.12.12   VehicleService 생성
 * 고민정    2024.12.12   registerVehicle 차량 등록 메서드
 * ========================================================
 */

package team2.parking.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import team2.parking.dto.VehicleDto;
import team2.parking.entity.Vehicle;
import team2.parking.repository.VehicleRepository;

@RequiredArgsConstructor
@Service
public class VehicleService {
	
	private final VehicleRepository vehicleRepository;
	
	
	// 차량 등록
	@Transactional
	public void registerVehicle(VehicleDto vehicleDto) {  // Vehicle 등록
		Vehicle vehicle = vehicleDto.toEntity(); // DTO -> Entity
		vehicleRepository.save(vehicle); // save
		vehicleDto.updateVid(vehicle.getVId()); // 주차 기록 데이터 추가 시 VehicleDto의 (id=null)인 of 메서드 대응
	}

}
