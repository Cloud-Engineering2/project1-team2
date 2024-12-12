/* Vehicle.java
 * Parking Lot Management Service
 * Vehicle ; 차량 정보 entity
 * 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 이홍비    2024.12.12   최초 작성 : DB 설계 기반 entity 작성
 * ========================================================
 */

package team2.parking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import team2.parking.entity.constant.VehicleType;

@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id ")
    private Integer vId; // 자동 설정되는 차량 고유 id

    @Column(name = "vehicle_number", length = 10, nullable = false)
    private String vNumber; // 차량 번호

    @Column(name = "vehicle_type", length = 10)
    @Enumerated(EnumType.STRING)
    private VehicleType vType; // 차종

    @Column(name = "owner_tel", length = 13, nullable = false)
    private String ownerTel; // 차주 전화번호

    @Column(name = "is_blacklisted", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isBlacklisted; // 블랙리스트 등록 여부

    protected Vehicle() { } // 기본 생성자

    private Vehicle(String vNumber, VehicleType vType, String ownerTel, Boolean isBlacklisted) { // 생성자
        // 초기화
        this.vNumber = vNumber;
        this.vType = vType;
        this.ownerTel = ownerTel;
        this.isBlacklisted = isBlacklisted;
    }

    public static Vehicle of(String vNumber, VehicleType vType, String ownerTel, Boolean isBlacklisted) { // static factory method - Vehicle 객체 생성
        return new Vehicle(vNumber, vType, ownerTel, isBlacklisted);
    }

    public void changeBlacklisted(Boolean isBlacklisted) { // 블랙리스트 등록, 해제할 때 사용
        this.isBlacklisted = isBlacklisted;
    }
}

