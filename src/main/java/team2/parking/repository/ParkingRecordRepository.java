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
 * ========================================================
 */

package team2.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team2.parking.entity.ParkingRecord;

@Repository
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, Integer> {

}
