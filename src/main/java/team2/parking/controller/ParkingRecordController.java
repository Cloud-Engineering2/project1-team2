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
 * 이홍비    2024.12.12   기존 getParkingRecords() 기능 확장
 * ========================================================
 */

package team2.parking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.parking.dto.ParkingRecordDto;
import team2.parking.service.PagingService;
import team2.parking.service.ParkingRecordService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/parking/history")
public class ParkingRecordController {

    private final ParkingRecordService parkingRecordService;
    private final PagingService pagingService;

    @GetMapping() // /admin/parking/history 접속 시 처리
    public String getParkingRecords(@PageableDefault(size = 10, sort = "entryTime", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(required = false) String vNumber, @RequestParam(required = false) String start, @RequestParam(required = false) String end, ModelMap model) { // 처음 /admin/parking/history 접속 => 모든 주차 기록 출력 // 특정 기간, 특정 차량 기록 검색 => 해당 기록만 룰력
        //List<ParkingRecordDto> recordDtos = parkingRecordService.getAllParkingRecords(); // 모든 주차 기록 get

        // 특정 기간 내 특정 차량의 주차 기록 get
        // 검색 x (start, end == null // vNumber == null) => 모든 주차 기록 get
        Page<ParkingRecordDto> recordDtos = parkingRecordService.getParkingRecords(vNumber, start, end, pageable);

        // 시작, 종료 page 사용 ; 현재 page, 전체 page 개수 정보 전달
        List<Integer> pagingNumbers = pagingService.getPagingNumbers(pageable.getPageNumber(), recordDtos.getTotalPages());

        // 총수익 get
        int totalEarning = parkingRecordService.getTotalParkingFeesFromRecords(vNumber, start, end);


        log.info("vNumber : " + vNumber);
        log.info("start : " + start);
        log.info("end : " + end);
        log.info("pagingNumbers : " + pagingNumbers);
        log.info("");

        System.out.println(recordDtos.getTotalElements());
        System.out.println(pagingNumbers);
        System.out.println(totalEarning);

        if (start != null && end != null) {
            List<ParkingRecordDto> check = parkingRecordService.getParkingRecordsInPeriodCh(start, end);
            System.out.println(check);
        }

        if (start != null && end != null && vNumber != null) {
            List<ParkingRecordDto> check = parkingRecordService.getParkingRecordsForVehicleInPeriodCh(vNumber, start, end);
            System.out.println(check);
        }

        // ModelMap 에 속성으로 추가
        model.addAttribute("parkingRecords", recordDtos);
        model.addAttribute("pagingNumbers", pagingNumbers);
        model.addAttribute("totalEarning", totalEarning);

        return "parking/history"; // history.html 로 경로 반환

    }




}
