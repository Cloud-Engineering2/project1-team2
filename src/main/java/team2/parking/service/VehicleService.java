/* VehicleService.java
 * Parking Lot Management Service
 * Vehicle 의 Service
 * 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2025.01.27
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 고민정    2024.12.12   VehicleService 생성
 * 고민정    2024.12.12   registerVehicle 차량 등록 메서드
 * 이홍비    2025.01.02   관리자 - 차량 조회 관련 함수 구현
 * 이홍비    2025.01.05   관리자 - 블랙리스트 관련 함수 구현
 * 이홍비	2025.01.27	 getVehicles() - if 조건 추가
 * ========================================================
 */

package team2.parking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import team2.parking.dto.ParkingRecordDto;
import team2.parking.dto.VehicleDto;
import team2.parking.entity.Vehicle;
import team2.parking.repository.VehicleRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class VehicleService {
	
	private final VehicleRepository vehicleRepository;
	
	
	// 차량 등록
	public void registerVehicle(VehicleDto vehicleDto) {  // Vehicle 등록
		Vehicle vehicle = vehicleDto.toEntity(); // DTO -> Entity
		vehicleRepository.save(vehicle); // save
		vehicleDto.updateVid(vehicle.getVId()); // 주차 기록 데이터 추가 시 VehicleDto의 (id=null)인 of 메서드 대응
	}

	// 등록된 차량 조회
	public Page<VehicleDto> getVehicles(String vNumber, Pageable pageable) { // 관리자 - 차량 조회 시 사용함
		log.info("VehicleService - getVehicles()");

		if ((vNumber == null) || "null".equals(vNumber) || vNumber == "") { // 차량 번호 null or 'null' => 등록된 차량 모두 조회
			return vehicleRepository.findAll(pageable) // 등록된 모든 차량 get => 스트림
					.map(VehicleDto::from); // Vehicle => VehicleDto
		}
		else { // 특정 차량 조회
			return vehicleRepository.findByvNumber(vNumber, pageable) // 차량 번호가 vNumber 인 차량 get
					.map(VehicleDto::from); // Vehicle => VehicleDto
		}
	}

	// 차량 블랙리스트 등록, 해제 설정
	public VehicleDto setBlacklist(Integer vId) {
		Vehicle vehicle = vehicleRepository.findById(vId)
				.orElseThrow(() -> new NoSuchElementException("Vehicle not found"));

		vehicle.setBlacklisted(); // vehicle.isBlacklisted 값 설정 (true(1), false(0))
		vehicleRepository.save(vehicle);

		return VehicleDto.from(vehicle);
	}

	// 등록된 총 차량 수
	public Integer getTotalVehicles() {
		return (int) vehicleRepository.count(); // vehicleRepository.count() => long type 반환 => int 로 형 변환 => int -> Integer ; 자동 boxing
	}

	// 블랙리스트로 등록된 차량 수
	public Integer getTotalBlacklisted() {
		return (int) vehicleRepository.countByIsBlacklisted(true);
	}

}
