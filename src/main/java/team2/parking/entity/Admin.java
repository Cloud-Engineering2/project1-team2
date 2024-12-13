/* Admin.java
 * Parking Lot Management Service
 * 관리자 Entity
 * 작성자 : semi_lion2 (고민정, 박청조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 박청조    2024.12.12    Admin Entity 작성
 * 박청조    2024.12.12    enum 타입 role 테스트 중 문제로 String 타입으로 수정
 *
 * ========================================================
 */

package team2.parking.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {

    // 관리자 id
    @Id
    @Column(name = "admin_id", nullable = false)
    private String id;

    // 관리자 password
    @Column(nullable = false)
    private String password;

    // 관리자 권한
    @Column(name = "role_type", nullable = false)
    private String role;
}
