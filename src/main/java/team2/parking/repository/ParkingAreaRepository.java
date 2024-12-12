/* ParkingAreaRepository.java
* Parking Lot Management Service
* 주차 구역(장소) 리포지토리
* 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
* 최종 수정 날짜 : 2024.12.12
*
* ========================================================
* 프로그램 수정 / 보완 이력
* ========================================================
* 작업자       날짜       수정 / 보완 내용
* ========================================================
* 고민정    2024.12.12    ParkingArea 리포지토리 생성
* ========================================================
*/ 

package team2.parking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team2.parking.entity.ParkingArea;

@Repository
public interface ParkingAreaRepository extends JpaRepository<ParkingArea, Integer>{

	Optional<ParkingArea> findByLocation(String location);
}
