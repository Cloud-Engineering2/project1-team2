/* UserRoleType.java
 * Parking Lot Management Service
 * 사용자 권한 타입
 * 작성자 : semi_lion2 (고민정, 박청조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 박청조    2024.12.12    UserRoleType 작성
 * ========================================================
 */

package team2.parking.entity.constant;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@ToString
public enum UserRoleType {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String roleType;

    UserRoleType(String roleType) {
        this.roleType = roleType;
    }

    public static UserRoleType getInstance(String roleType) {
        return Arrays.stream(UserRoleType.values())
                .filter(role -> role.getRoleType().equals(roleType))
                .findFirst()
                .orElseThrow();
    }
}
