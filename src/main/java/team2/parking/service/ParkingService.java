/* ParkingService.java
* Parking Lot Management Service
* 주차 서비스
* 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
* 최종 수정 날짜 : 2024.12.12
*
* ========================================================
* 프로그램 수정 / 보완 이력
* ========================================================
* 작업자       날짜       수정 / 보완 내용
* ========================================================
* 허선호    2024.12.12    ParkingService 서비스 생성
* ========================================================
*/ 

package team2.parking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team2.parking.dto.ParkingRecordDto;
import team2.parking.dto.VehicleDto;
import team2.parking.repository.ParkingRecordRepository;
import team2.parking.repository.VehicleRepository;

@RequiredArgsConstructor
@Service
public class ParkingService {
    private final ParkingRecordRepository parkingRecordRepository;
    private final VehicleRepository vehicleRepository;

	public ParkingRecordDto getParkingrecordByVehicleno(String vehicleno) {
//		검색창에 입력한 차량 번호와 일치하는 VehicleDto 찾기
    	VehicleDto vehicleDto = vehicleRepository.findByvNumberContaining(vehicleno)
    			.map(VehicleDto::from).orElseThrow();
		
    	// exitTime이 비어있는 주차 기록을 찾기
		List<ParkingRecordDto> parkingRecordDtos = parkingRecordRepository.findByExitTimeIsNull()
				.stream().map(ParkingRecordDto::from).toList();


		// 위에서 찾은 주차기록에서 VehicleDto로 필터링해 반환
		System.out.println(vehicleDto);
		System.out.println(parkingRecordDtos);
		ParkingRecordDto result = parkingRecordDtos.stream()
				.filter(r -> r.getVehicleId().getId().equals(vehicleDto.getId()))
				.findFirst().orElseThrow();
		return result;
	}
}
