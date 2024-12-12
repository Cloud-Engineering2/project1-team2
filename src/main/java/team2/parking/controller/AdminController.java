/* AdminController.java
 * Parking Lot Management Service
 * 관리자 Controller
 * 작성자 : semi_lion2 (고민정, 박청조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 박청조    2024.12.12    관리자 Controller 작성 - 로그인 테스트
 *
 * ========================================================
 */

package team2.parking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // login 페이지 접근
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 로그인 완료 시 띄우는 테스트 창 => 관리자 주차 현황으로 변경 예정
    @GetMapping("/success")
    public String success() {
        return "success";
    }
}
