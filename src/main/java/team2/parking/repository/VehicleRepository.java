/* VehicleRepository.java
 * Parking Lot Management Service
 * 차량 정보 Repository
 * 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2025.01.27
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 허선호    2024.12.13   최초 생성
 * 이홍비    2025.01.02   findAll(pageable) 함수 추가 - paging 기법 사용
 * 이홍비    2025.01.27   findAll(pageable) 함수 주석 처리
 * ========================================================
 */

package team2.parking.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import team2.parking.entity.Vehicle;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    // 입력된 글자를 포함한 차량 번호 찾기
	Optional<Vehicle> findByvNumberContaining(String vehicleno);

    // 등록된 모든 차량 조회
//    @Query("select v from Vehicle v order by v.vId")
//    Page<Vehicle> findAll(Pageable pageable);

    // "isBlacklisted" 값으로 설정된 차량 조회
    // 보통 true 로 인수 전달해서 블랙리스트로 등록된 차량 조회할 때 씀
    long countByIsBlacklisted(Boolean isBlacklisted);

    // 차량 번호 => 차량 조회
    // 함수명 => findByVNumber x ; Entity - Vehicle 에서는 vNumber 을 필드명으로 사용함 => findByvNumber 로 해야 함
    Page<Vehicle> findByvNumber(String vNumber, Pageable pageable);
}
