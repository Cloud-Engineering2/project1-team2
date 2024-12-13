/* ParkingRecordRepository.java
 * Parking Lot Management Service
 * ParkingRecord 의 Repository
 * 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 이홍비    2024.12.12   최초 작성
 * 이홍비    2024.12.12   query method 추가
 * 이홍비    2024.12.12   query method 오류 발생 (쿼리 생성 불가) => @Query() 직접 작성
 * 허선호    2024.12.13   findByExitTimeIsNull 쿼리 메소드 추가
 * 고민정    2024.12.13   findFirstByAreaIdOrderByEntryTimeDesc 메서드 추가
 * ========================================================
 */

package team2.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team2.parking.entity.ParkingArea;
import team2.parking.entity.ParkingRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, Integer> {

    // startDate ~ endDate 기간 내 차량 번호가 vNumber 인 차의 입차, 출차 기록 조회
    // 입차 ; 기간 외, 출차 ; 기간 내 => 조회
    // 입차 ; 기간 내, 출차 ; 기간 외 => 조회
    // 입차 ; 기간 내, 출차 ; 기간 내 => 조회
    // 이때 발생하는 중복 제거 ; Distinct
    @Query("select distinct pr from ParkingRecord pr join pr.vehicleId v where (v.vNumber = :vNumber and ((pr.entryTime between :startDate and :endDate) or (pr.exitTime between :startDate and :endDate)))")
    List<ParkingRecord> findDistinctByVehicleAndTimePeriod(@Param("vNumber") String vNumber, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // startDate ~ endDate 기간 내 입차, 출차 기록 조회
    @Query("select distinct pr from ParkingRecord pr where ((pr.entryTime between :startDate and :endDate) or (pr.exitTime between :startDate and :endDate))")
    List<ParkingRecord> findDistinctByTimePeriod(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // 현재 주차중인 차량 검색
	List<ParkingRecord> findByExitTimeIsNull();
	
	// 한 구역에 대한 주차 기록 중 가장 최근의 것 조회
	Optional<ParkingRecord> findFirstByAreaIdOrderByEntryTimeDesc(ParkingArea parkingArea); 


}
