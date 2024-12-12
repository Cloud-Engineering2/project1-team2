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
 * ========================================================
 */

package team2.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team2.parking.entity.ParkingRecord;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, Integer> {

    // startDate ~ endDate 기간 내 차량 번호가 vNumber 인 차의 입차, 출차 기록 조회
    // 입차 ; 기간 외, 출차 ; 기간 내 => 조회
    // 입차 ; 기간 내, 출차 ; 기간 외 => 조회
    // 입차 ; 기간 내, 출차 ; 기간 내 => 조회
    // 이때 발생하는 중복 제거 ; Distinct
    List<ParkingRecord> findDistinctByVehicleVNumberAndEntryTimeBetweenOrExitTimeBetween(String vNumber, LocalDateTime startDate, LocalDateTime endDate);

    // startDate ~ endDate 기간 내 입차, 출차 기록 조회
    List<ParkingRecord> findDistinctByEntryTimeBetweenOrExitTimeBetween(LocalDateTime start, LocalDateTime end);
}
