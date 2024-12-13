/* ParkingRecordService.java
 * Parking Lot Management Service
 * ParkingRecordService : ParkingRecord 관련 Service
 * 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 이홍비    2024.12.12   최초 작성 : Service
 * 이홍비    2024.12.12   호출 함수 변경 (getParkingRecordsForVehicleInPeriod(), getParkingRecordsInPeriod())
 * 고민정    2024.12.12   addEntryParkingRecord, addExitParkingRecord 메서드 작성
 * ========================================================
 */

package team2.parking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import team2.parking.dto.ParkingAreaDto;
import team2.parking.dto.ParkingRecordDto;
import team2.parking.dto.VehicleDto;
import team2.parking.entity.ParkingArea;
import team2.parking.entity.ParkingRecord;
import team2.parking.entity.Vehicle;
import team2.parking.repository.ParkingAreaRepository;
import team2.parking.repository.ParkingRecordRepository;
import team2.parking.repository.VehicleRepository;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ParkingRecordService {
    private final ParkingRecordRepository parkingRecordRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingAreaRepository parkingAreaRepository;

    public List<ParkingRecordDto> getAllParkingRecords() { // 모든 주차 기록을 얻는 함수
        return parkingRecordRepository.findAll().stream() // 모든 주차 기록 get => 스트림
                .map(ParkingRecordDto::from) // ParkingRecord => ParkingRecordDto
                .toList(); // List 로 반환
    }

    public ParkingRecordDto getParkingRecordById(Integer id) { // 특정 주차 기록을 얻는 함수
        return parkingRecordRepository.findById(id) // id 에 해당하는 ParkingRecord get
                .map(ParkingRecordDto::from) // ParkingRecord => ParkingRecordDto
                .orElseThrow(); // 없음 => 예외
    }

    public List<ParkingRecordDto> getParkingRecordsForVehicleInPeriod(String vNumber, LocalDateTime start, LocalDateTime end) { // 특정 기간에 머물던 특정 차량 기록 출력

        // 차량 번호 + 날짜 기준 조회 ; 해당 기간 내 그 차가 입차, 출차한 기록
        return parkingRecordRepository.findDistinctByVehicleAndTimePeriod(vNumber, start, end).stream()
                .map(ParkingRecordDto::from) // ParkingRecord => ParkingRecordDto
                .toList(); // List 로
    }

    public List<ParkingRecordDto> getParkingRecordsInPeriod(LocalDateTime start, LocalDateTime end) { // 특정 기간 내 머물던 기록 조회
        return parkingRecordRepository.findDistinctByTimePeriod(start, end).stream()
                .map(ParkingRecordDto::from) // ParkingRecord => ParkingRecordDto
                .toList(); // List 로
    }
    
   
    public void addEntryParkingRecord(ParkingRecordDto parkingRecordDto) { // 주차 기록 추가
    	
    	Integer vehicleId = parkingRecordDto.getVehicleId().getId(); // VehicleDto Id
    	Integer parkingAreaId = parkingRecordDto.getAreaId().getId(); // ParkingAreaDto Id
    	
    	 Vehicle vehicle = vehicleRepository.findById(vehicleId) // Vehicle 엔티티
    		        						.orElseThrow(() -> new RuntimeException("Vehicle not found"));
	     ParkingArea parkingArea = parkingAreaRepository.findById(parkingAreaId) // ParkingArea 엔티티
    		        									.orElseThrow(() -> new RuntimeException("ParkingArea not found"));
    		    
    	ParkingRecord parkingRecord = parkingRecordDto.toEntity(vehicle, parkingArea); // ParkingRecord : DTO -> Entity
    	
    	
		parkingRecord.updateEntryTime(); // 입차 시각 = 등록하는 현재 시각
    	
    	
    	parkingRecordRepository.save(parkingRecord); // ParkingRecord 추가
    }
    
    
    @Transactional
    public void addExitParkingRecord(ParkingAreaDto parkingAreaDto) {

    	ParkingArea parkingArea = parkingAreaDto.toEntity();// ParkingArea
    	parkingArea.updateAreaId(parkingAreaDto.getId());
    	
    	ParkingRecord parkingRecord = parkingRecordRepository.findFirstByAreaIdOrderByEntryTimeDesc(parkingArea)  // ParkingArea Id로 가장 최근의 입차 기록 조회
    														 .orElseThrow(() -> new RuntimeException("ParkingRecord not found"));
 
    	// 출차 시각 및 주차비 정산
    	parkingRecord.updateExitRecord();
        
    }
    
    
    public ParkingRecord getParkingRecordByAreaIdOrderByEntryTimeDesc(ParkingArea parkingArea) {
    	return parkingRecordRepository.findFirstByAreaIdOrderByEntryTimeDesc(parkingArea)  // ParkingArea Id로 가장 최근의 입차 기록 조회
    								  .orElseThrow(() -> new RuntimeException("ParkingRecord not found"));
	

}


	public VehicleDto getVehicleByAreaId(Integer areaId) {
		ParkingArea parkingArea = parkingAreaRepository.findById(areaId)
														.orElseThrow(() -> new RuntimeException("ParkingArea not found"));
		ParkingRecord parkingRecord = parkingRecordRepository.findFirstByAreaIdOrderByEntryTimeDesc(parkingArea)  // ParkingArea Id로 가장 최근의 입차 기록 조회
		  								                     .orElseThrow(() -> new RuntimeException("ParkingRecord not found"));
		VehicleDto vehicleDto = VehicleDto.from(parkingRecord.getVehicleId());
		
		return vehicleDto;
		
	}
}