/* AdminVehicleController.java
 * Parking Lot Management Service
 * AdminVehicleController : 차량 조회 관련 Controller
 * 차량 정보 조회 + 블랙리스트 관리
 * 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2025.01.05
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 이홍비    2025.01.02   최초 작성 : AdminVehicleController
 * 이홍비    2025.01.05   차량 조회 처리
 * 이홍비    2025.01.05   블랙리스트 등록, 해제 처리
 * ========================================================
 */


package team2.parking.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForThaiBuddhistDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.parking.dto.VehicleDto;
import team2.parking.service.PagingService;
import team2.parking.service.VehicleService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/vehicle/search")
public class AdminVehicleController {
    private final VehicleService vehicleService;
    private final PagingService pagingService;

    @GetMapping() // /admin/vehicle/search 접속 시 처리 - 차량 조회
    public String getVehicles(@PageableDefault(size = 10, sort = "vId", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(required = false) String vNumber, ModelMap model) {
        log.info("AdminVehicleController - getVehicles()");

        // 등록된 차량 정보 get
        Page<VehicleDto> vehicleDtos = vehicleService.getVehicles(vNumber, pageable);

        // 시작, 종료 page 사용 ; 현재 page, 전체 page 개수 정보 전달
        List<Integer> pagingNumbers =  pagingService.getPagingNumbers(pageable.getPageNumber(), vehicleDtos.getTotalPages());

        // 등록된 차량 수
        Integer totalVehicles = vehicleService.getTotalVehicles();

        // 블랙리스트로 등록된 차량 수
        Integer totalBlackLists = vehicleService.getTotalBlacklisted();

        // ModelMap 에 속성으로 추가
        model.addAttribute("vehicles", vehicleDtos);
        model.addAttribute("pagingNumbers", pagingNumbers);
        model.addAttribute("totalVehicles", totalVehicles);
        model.addAttribute("totalBlackLists", totalBlackLists);

        // 확인
//        System.out.println("Current page number: " + pageable.getPageNumber());
//        System.out.println("Page size: " + pageable.getPageSize());
//        System.out.println("Total elements: " + vehicleDtos.getTotalElements());
//        System.out.println("Total pages: " + vehicleDtos.getTotalPages());
//
//        System.out.println("vehicleDtos : " + vehicleDtos);
//        System.out.println("pagingNumbers : " + pagingNumbers);


        return "parking/search";
    }


    @GetMapping("/setBlacklist")
    public String setBlacklist(@RequestParam Integer vehicleId, ModelMap model) {
        log.info("AdminVehicleController - setBlacklist()");

        vehicleService.setBlacklist(vehicleId);

        return "redirect:/admin/vehicle/search";
    }


}
