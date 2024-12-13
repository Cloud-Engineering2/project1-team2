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
 * 이홍비    2024.12.13   총수익 계산 및 Page<> 주차 기록 조회 method 구현
 * 박청조    2024.12.13   주차 현황에 필요한 데이터 가져오는 메서드 추가 (ParkingStatusDto, totalFee)
 * ========================================================
 */

package team2.parking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import team2.parking.dto.ParkingAreaDto;
import team2.parking.dto.ParkingRecordDto;

import team2.parking.dto.VehicleDto;
import team2.parking.entity.ParkingRecord;
import team2.parking.dto.ParkingStatusDto;
import team2.parking.entity.ParkingArea;
import team2.parking.repository.ParkingAreaRepository;
import team2.parking.entity.Vehicle;
import team2.parking.repository.ParkingRecordRepository;
import team2.parking.repository.VehicleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;


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

    public List<ParkingRecordDto> getParkingRecordsForVehicleInPeriod(String vNumber, LocalDateTime startDate, LocalDateTime endDate) { // 특정 기간에 머물던 특정 차량 기록 조회

        // 차량 번호 + 날짜 기준 조회 ; 해당 기간 내 그 차가 입차, 출차한 기록
        return parkingRecordRepository.findDistinctByVehicleAndTimePeriod(vNumber, startDate, endDate).stream() // startDate ~ endDate 기간 내 입차, 출차한 vNumber 차량 조회 => 스트림
                .map(ParkingRecordDto::from) // ParkingRecord => ParkingRecordDto
                .toList(); // List 로
    }

    public List<ParkingRecordDto> getParkingRecordsForVehicleInPeriodCh(String vNumber, String startDate, String endDate) { // 특정 기간에 머물던 특정 차량 기록 조회
        // String => LocalDate
        LocalDate startLocalDate = LocalDate.parse(startDate);
        LocalDate endLocalDate = LocalDate.parse(endDate);

        // LocalDate => LocalDateTime
        LocalDateTime start = startLocalDate.atStartOfDay(); // 0분 0시 0초로 설정
        LocalDateTime end = endLocalDate.atTime(LocalTime.MAX); // 23시 59분 59.9999초로 설정

        // 차량 번호 + 날짜 기준 조회 ; 해당 기간 내 그 차가 입차, 출차한 기록
        return parkingRecordRepository.findDistinctByVehicleAndTimePeriod(vNumber, start, end).stream() // startDate ~ endDate 기간 내 입차, 출차한 vNumber 차량 조회 => 스트림
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

    // 주어진 구역의 주차된 (차량 번호 / 폰 번호 / 위치 / 누적시간 / 누적요금) 데이터 리스트 반환
    public List<ParkingStatusDto> getInUseParkingStatus(String area) {

        // 1. parking area 에서 사용중인 구역의 id만 추출
        List<Integer> idList = parkingAreaRepository.findParkingAreasByInUseIsTrueAndLocationStartsWith(area)
                .stream().mapToInt(ParkingArea::getAreaId).boxed().toList();

        // 2. 추출한 id로 parking record 조회해서 차량번호 / 폰번호 빼내서 객체 생성 (ParkingStatusDto)
        List<ParkingRecord> records = parkingRecordRepository.findParkingRecordsByExitTimeIsNullAndAreaId_AreaIdIn(idList);
        return records
                .stream()
                .map(record -> new ParkingStatusDto(record.getAreaId(),
                        record.getVehicleId(),
                        record.getEntryTime()))
                .collect(Collectors.toList());
    }

    // 주차 현황 총 수입 표시를 위한 메서드
    public int getTotalFee() {
        return parkingRecordRepository.getTotalFee();
    }
    public List<ParkingRecordDto> getParkingRecordsInPeriod(LocalDateTime startDate, LocalDateTime endDate) { // 특정 기간 내 머물던 기록 조회
        return parkingRecordRepository.findDistinctByTimePeriod(startDate, endDate).stream() // startDate ~ endDate 기간 내 입차, 출차한 차량 조회 => 스트림
                .map(ParkingRecordDto::from) // ParkingRecord => ParkingRecordDto
                .toList(); // List 로
    }

    public List<ParkingRecordDto> getParkingRecordsInPeriodCh(String startDate, String endDate) { // 특정 기간 내 머물던 기록 조회
        // String => LocalDate
        LocalDate startLocalDate = LocalDate.parse(startDate);
        LocalDate endLocalDate = LocalDate.parse(endDate);

        // LocalDate => LocalDateTime
        LocalDateTime start = startLocalDate.atStartOfDay(); // 0분 0시 0초로 설정
        LocalDateTime end = endLocalDate.atTime(LocalTime.MAX); // 23시 59분 59.9999초로 설정

        return parkingRecordRepository.findDistinctByTimePeriod(start, end).stream() // startDate ~ endDate 기간 내 입차, 출차한 차량 조회 => 스트림
                .map(ParkingRecordDto::from) // ParkingRecord => ParkingRecordDto
                .toList(); // List 로
    }


    public Page<ParkingRecordDto> getParkingRecords(String vNumber, String startDate, String endDate, Pageable pageable) { // 주차 기록 조회




        if ((startDate == null) || (endDate == null)) { // startDate 나 endDate 가 null 이다 => 검색 x => /admin/parking/history 처음 접속
            // 모든 주차 기록 반환
            return parkingRecordRepository.findAll(pageable) // 전체 기간 - 모든 주차 기록 get => 스트림
                    .map(ParkingRecordDto::from); // ParkingRecord => ParkingRecordDto
        }


        // startDate, endDate != null

        // String => LocalDate
        LocalDate startLocalDate = LocalDate.parse(startDate);
        LocalDate endLocalDate = LocalDate.parse(endDate);

        // LocalDate => LocalDateTime
        LocalDateTime start = startLocalDate.atStartOfDay(); // 0분 0시 0초로 설정
        LocalDateTime end = endLocalDate.atTime(LocalTime.MAX); // 23시 59분 59.9999초로 설정


        if (vNumber == null) { // 차량 번호가 null 이다 => startDate ~ endDate 기간 내 입출차 기록 조회

            return parkingRecordRepository.findParkingRecordsPageByTimePeriod(start, end, pageable) // start ~ end 기간 내 입차, 출차한 차량 조회
                    .map(ParkingRecordDto::from); // ParkingRecord => ParkingRecordDto

        }
        else { // startDate ~ endDate 기간 내 차량 번호가 vNumber 인 차 입출 기록 조회
            return parkingRecordRepository.findDParkingRecordsPageByVehicleAndTimePeriod(vNumber, start, end, pageable) // start ~ end 기간 내 입차, 출차한 vNumber 차량 조회
                    .map(ParkingRecordDto::from); // ParkingRecord => ParkingRecordDto
        }


    }

    public int getTotalParkingFeesFromRecords(String vNumber, String startDate, String endDate) { // 총수익 계산
        List<Integer> fees = new ArrayList<>(); // 주차피 조회해서 담을 객체

        if ((startDate == null) || (endDate == null)) { // startDate 나 endDate 가 null 이다 => 검색 x => /admin/parking/history 처음 접속
            // 전체 기간 총수익 계산
            fees = parkingRecordRepository.findAllParkingFees(); // 모든 주차 기록에서 주차비 조회

            if ((fees == null) || fees.isEmpty()) { // fees 가 null 이거나 비었다 => 총수익 0원
                return 0; // 0 반환
            }

            return fees.stream() // List<Integer> fees => 스트림
                    .filter(Objects::nonNull)  // null 값 제외
                    .mapToInt(Integer::intValue) // Integer => int
                    .sum(); // 합산
        }


        // startDate, endDate != null

        // String => LocalDate
        LocalDate startLocalDate = LocalDate.parse(startDate);
        LocalDate endLocalDate = LocalDate.parse(endDate);

        // LocalDate => LocalDateTime
        LocalDateTime start = startLocalDate.atStartOfDay(); // 0분 0시 0초로 설정
        LocalDateTime end = endLocalDate.atTime(LocalTime.MAX); // 23시 59분 59.9999초로 설정

        if (vNumber == null) { // 차량 번호가 null 이다 => startDate ~ endDate 기간 내 총수익 계산
            fees = parkingRecordRepository.findDistinctParkingFeeByTimePeriod(start, end);
        }
        else { // startDate ~ endDate 기간 내 차량 번호가 vNumber 인 차 - 총 주차비 계산
            fees = parkingRecordRepository.findDistinctParkingFeeByVehicleAndTimePeriod(vNumber, start, end);
        }

        if ((fees == null) || fees.isEmpty()) { // fees 가 null 이거나 비었다 => 총수익 0원
            return 0; // 0 반환
        }

        return fees.stream() // List<Integer> fees => 스트림
                .filter(Objects::nonNull)  // null 값 제외
                .mapToInt(Integer::intValue) // Integer => int
                .sum(); // 합산
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