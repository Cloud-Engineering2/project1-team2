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
 * ========================================================
 */

package team2.parking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import team2.parking.dto.ParkingRecordDto;
import team2.parking.repository.ParkingRecordRepository;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ParkingRecordService {
    private final ParkingRecordRepository parkingRecordRepository;

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
        return parkingRecordRepository.findDistinctByVehicleVNumberAndEntryTimeBetweenOrExitTimeBetween(vNumber, start, end).stream()
                .map(ParkingRecordDto::from) // ParkingRecord => ParkingRecordDto
                .toList(); // List 로
    }

    public List<ParkingRecordDto> getParkingRecordsInPeriod(LocalDateTime start, LocalDateTime end) { // 특정 기간 내 머물던 기록 조회
        return parkingRecordRepository.findDistinctByEntryTimeBetweenOrExitTimeBetween(start, end).stream()
                .map(ParkingRecordDto::from) // ParkingRecord => ParkingRecordDto
                .toList(); // List 로
    }

}
