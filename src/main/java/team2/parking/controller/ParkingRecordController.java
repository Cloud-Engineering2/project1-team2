/* ParkingRecordController.java
 * Parking Lot Management Service
 * ParkingRecordController : ParkingRecord 관련 Controller
 * 주차 기록 조회 등 처리
 * 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 이홍비    2024.12.12   최초 작성 : ParkingRecordService test
 * 이홍비    2024.12.12   getParkingRecords()
 * ========================================================
 */

package team2.parking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import team2.parking.dto.ParkingRecordDto;
import team2.parking.service.ParkingRecordService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/parking/history")
public class ParkingRecordController {

    private final ParkingRecordService parkingRecordService;

    @GetMapping()
    public String getParkingRecords(ModelMap model) { // /admin/parking/history 접속 시 처리 : 모든 주차 기록 출력

        List<ParkingRecordDto> recordDtos = parkingRecordService.getAllParkingRecords(); // 모든 주차 기록 get

        model.addAttribute("parkingRecords", recordDtos); // 해당 기록을 ModelMap 에 추가함

        return "parking/history"; // history.html 로 경로 반환

    }




}
