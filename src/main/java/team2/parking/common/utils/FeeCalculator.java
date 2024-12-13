/* FeeCalculator.java
 * Parking Lot Management Service
 * 주차 기능 관련 컨트롤러
 * 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024-12-13
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 박청조    2024.12.13    주차 요금 계산을 위한 클래스 생성
 *                       static 메소드를 통해 주차 요금 계산 구현
 *
 * ========================================================
 */

package team2.parking.common.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class FeeCalculator {

    private static final int FEE_PER_10_MINUTES = 500;

    // 입차 시간만 받아서 계산하느 메소드
    public static int calculateFee(LocalDateTime entryTime) {
        LocalDateTime now = LocalDateTime.now(); // 현재 시각
        return calculateFee(entryTime, now);
    }

    // 입차, 출차 시간 받아서 계산하는 메소드
    public static int calculateFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        if (entryTime.isAfter(exitTime)) {
            throw new IllegalArgumentException("입차 시간은 출차 시간보다 이후일 수 없습니다.");
        }

        // 두 시간의 차이를 분 단위로 계산
        long minutesParked = Duration.between(entryTime, exitTime).toMinutes();

        // 10분 단위 요금 계산
        long feeUnits = minutesParked / 10; // 내림 처리
        return (int) feeUnits * FEE_PER_10_MINUTES;
    }

}
