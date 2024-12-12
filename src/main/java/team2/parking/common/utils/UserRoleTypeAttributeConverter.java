package team2.parking.common.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import team2.parking.entity.constant.UserRoleType;

/* UserRoleTypeAttributeConverter.java
 * Parking Lot Management Service
 * 사용자 권한 타입 컨버터
 * 작성자 : semi_lion2 (고민정, 박청조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 박청조    2024.12.12    UserRoleType 컨버터 작성
 * ========================================================
 */

@Converter
public class UserRoleTypeAttributeConverter implements AttributeConverter<UserRoleType, String> {

    // 유저 관련 데이터 생성 시 권한 필드 UserRoleType -> String 컨버팅
    @Override
    public String convertToDatabaseColumn(UserRoleType attribute) {
        return attribute.getRoleType();
    }

    // 유저 관련 데이터 조회 시 권한 필드 String -> UserRoleType 컨버팅
    @Override
    public UserRoleType convertToEntityAttribute(String dbData) {
        return UserRoleType.getInstance(dbData);
    }
}
