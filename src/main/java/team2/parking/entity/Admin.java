package team2.parking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import team2.parking.common.utils.UserRoleTypeAttributeConverter;
import team2.parking.entity.constant.UserRoleType;

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
 * ========================================================
 */

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {

    // 관리자 id
    @Id
    @Column(name = "admin_id")
    private String id;

    // 관리자 password
    private String password;

    // 관리자 권한
    @Column(name = "role_type")
    @Convert(converter = UserRoleTypeAttributeConverter.class)
    private UserRoleType roleType;
}
