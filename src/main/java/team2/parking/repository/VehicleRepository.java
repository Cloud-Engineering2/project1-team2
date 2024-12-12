/* VehicleRepository.java
 * Parking Lot Management Service
 * Vehicle 의 Repository
 * 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 고민정    2024.12.12   VehicleRepository 생성
 * ========================================================
 */

package team2.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team2.parking.entity.Vehicle;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
	
}
