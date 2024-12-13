/* ParkingService.java
* Parking Lot Management Service
* 로그인되지 않은 상태에서 주차 조회 서비스
* 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
* 최종 수정 날짜 : 2024.12.12
*
* ========================================================
* 프로그램 수정 / 보완 이력
* ========================================================
* 작업자       날짜       수정 / 보완 내용
* ========================================================
* 허선호    2024.12.12    ParkingService 서비스 생성
* 허선호    2024.12.13    getParkingstatus() 구역내 주차상황 확인 메소드 생성
* ========================================================
*/ 

package team2.parking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.qos.logback.classic.net.SyslogAppender;
import lombok.RequiredArgsConstructor;
import team2.parking.dto.ParkingAreaDto;
import team2.parking.dto.ParkingRecordDto;
import team2.parking.dto.VehicleDto;
import team2.parking.repository.ParkingAreaRepository;
import team2.parking.repository.ParkingRecordRepository;
import team2.parking.repository.VehicleRepository;

@RequiredArgsConstructor
@Service
public class ParkingService {
    private final ParkingRecordRepository parkingRecordRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingAreaRepository parkingAreaRepository;

    // getParkingrecordByVehicleno: 차량 번호로 주차된 장소 및 정보 찾기 메소드
	public ParkingRecordDto getParkingrecordByVehicleno(String vehicleno) {
//		검색창에 입력한 차량 번호와 일치하는 VehicleDto 찾기
    	VehicleDto vehicleDto = vehicleRepository.findByvNumberContaining(vehicleno)
    			.map(VehicleDto::from).orElseThrow(() -> new IllegalArgumentException("요청하신 차량을 찾을 수 없습니다."));
		
    	// exitTime이 비어있는 주차 기록을 찾기
		List<ParkingRecordDto> parkingRecordDtos = parkingRecordRepository.findByExitTimeIsNull()
				.stream().map(ParkingRecordDto::from).toList();


		// 위에서 찾은 주차기록에서 VehicleDto로 필터링해 반환
		ParkingRecordDto result = parkingRecordDtos.stream()
				.filter(r -> r.getVehicleId().getId().equals(vehicleDto.getId()))
				.findFirst().orElseThrow(() -> new IllegalArgumentException("요청하신 차량은 현재 주차 중이 아닙니다."));
		return result;
	}
	
	// 구역 내 주차상황 확인
	public List<ParkingAreaDto> getParkingstatus(String zone) {
		// 현재 주차 중인 기록 찾기
		List<ParkingAreaDto> parkingStatus = parkingAreaRepository.findAll()
				.stream().map(ParkingAreaDto::from).filter(dto -> dto.getLocation().charAt(0) == zone.charAt(0)).toList();
		return parkingStatus;
	}
}
