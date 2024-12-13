/* PagingService.java
 * Parking Lot Management Service
 * PagingService : Paging 관련 Service
 * 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 이홍비    2024.12.13   최초 작성 : Service
 * ========================================================
 */

package team2.parking.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PagingService {

    private final static int PAGE_LENGTH = 10; // 화면당 보여 줄 page 개수

    public List<Integer> getPagingNumbers(int pageNumber, int totalPages) { // 현재 page 번호와 총 page 개수를 토대로 화면에 표시할 page 번호 목록 반환

        int start = Math.max((pageNumber - (PAGE_LENGTH / 2)), 0); // 화면에 표시할 시작 page 번호 계산 - 음수 방지 (0)
        int end = Math.min((start + PAGE_LENGTH), totalPages); // 화면에 표시할 마지막 page 번호 계산 - 총 page 개수 넘지 않게 방지

        return IntStream.range(start, end) // start ~ (end - 1) 범위의 int 값
                .boxed() // int -> Integer
                .toList(); // List 로
    }
}
