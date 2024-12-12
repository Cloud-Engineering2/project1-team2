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
 * ========================================================
 */

package team2.parking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team2.parking.dto.ParkingRecordDto;
import team2.parking.service.ParkingRecordService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class ParkingRecordController {

    private final ParkingRecordService parkingRecordService;

    @GetMapping("/test1")
    public List<ParkingRecordDto> getRecords() {
        return parkingRecordService.getAllParkingRecords();
    }

    @GetMapping("/test2")
    public List<ParkingRecordDto> getRecordsByVNumberAndPeriod() {
        LocalDateTime start = LocalDateTime.of(2024,12,8, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024,12,11, 0, 0);
        String vNumber = "54바7226";

        return parkingRecordService.getParkingRecordsForVehicleInPeriod(vNumber, start, end);
    }

    @GetMapping("/test3")
    public List<ParkingRecordDto> getRecordsByPeriod() {
        LocalDateTime start = LocalDateTime.of(2024,12,8, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024,12,11, 0, 0);

        return parkingRecordService.getParkingRecordsInPeriod(start, end);
    }




}
