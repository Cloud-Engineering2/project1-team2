/* ParkingController.java
* Parking Lot Management Service
* 로그인되지 않은 상태에서 주차 조회 관련 컨트롤러
* 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
* 최종 수정 날짜 : 2024-12-13
*
* ========================================================
* 프로그램 수정 / 보완 이력
* ========================================================
* 작업자       날짜       수정 / 보완 내용
* ========================================================
* 허선호    2024.12.12    더미 데이터를 이용한 getParkingrecordByVehicleno 메소드 작성 
* 허선호    2024.12.13    비로그인 getParkingstatus 주차 현황을 표시하는 메소드 작성
* 박청조    2024.12.13    관리자 주차 현황 페이지 메소드 작성
* 
* ========================================================
*/ 

package team2.parking.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import team2.parking.dto.ParkingAreaDto;
import team2.parking.dto.ParkingRecordDto;
import team2.parking.dto.ParkingStatusDto;
import team2.parking.service.ParkingAreaService;
import team2.parking.service.ParkingRecordService;
import team2.parking.service.ParkingService;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class ParkingController {
	
	private final ParkingService parkingService;
	private final ParkingRecordService parkingRecordService;
	private final ParkingAreaService parkingAreaService;
	
	
//	getParkingrecordByVehicleno 메소드 작성(인트로페이지에서 로그인 없이 차량 검색할 경우에 동작)
	@GetMapping(value = "/search/{vehicleno}")
	public String getParkingrecordByVehicleno(@PathVariable("vehicleno") String vehicleno, Model model) {
		String view = "/search";
		ParkingRecordDto record = parkingService.getParkingrecordByVehicleno(vehicleno);
		model.addAttribute("record", record);
		return view;
		
	}

	
	// getParkingstatus 주차 현황을 표시하는 메소드 작성(인트로페이지 첫화면)
	@GetMapping(value = "/")
	public String getParkingstatus(@RequestParam(value="zone", 
		    required = false, defaultValue="A") String zone, Model model) {
		String view = "index.html"; // url 시작에 "/"가 붙어있으면 Error resolving template 오류 발생할 수 있음.

		List<ParkingAreaDto> status = parkingService.getParkingstatus(zone);
		
		model.addAttribute("status", status);
		model.addAttribute("zone", zone);
		return view;
	}



	// 관리자 주차 현황 페이지
	@GetMapping("/admin/parking")
	public String getAdminParkingStatus(@RequestParam(name = "area", defaultValue = "A") String area, Model model) {

		// 주차 현황 데이터 조회
		List<ParkingStatusDto> parkingList = parkingRecordService.getInUseParkingStatus(area);

		// 구역에 따른 주차 공간 10자리 생성
		List<String> areaLocations = IntStream.rangeClosed(1, 10)
				.mapToObj(i -> area + "-" + String.format("%02d", i))
				.toList();

		// 주차중인 구역은 조회한 데이터로 대체하고 미사용 구역은 미사용 데이터만 넣어서 객체 반환
		List<ParkingStatusDto> parkingViewList = areaLocations.stream()
				.map(location -> parkingList.stream()
                        .filter(p -> p.getLocation().equals(location))
                        .findFirst().orElse(new ParkingStatusDto(location)))
				.toList();

		model.addAttribute("parkingList1", parkingViewList.subList(0, 5));
		model.addAttribute("parkingList2", parkingViewList.subList(5, 10));

		// 전체 여유 공간 조회
		int useCount = parkingAreaService.getUsableAreaCount();
		int totalCount = parkingAreaService.getTotalAreaCount();

		System.out.println("전체 공간 - " + totalCount);
		System.out.println("사용 공간 - " + useCount);
		model.addAttribute("leftCount", totalCount - useCount);
		model.addAttribute("totalCount", totalCount);

		// 총 수입 조회
		int totalFee = parkingRecordService.getTotalFee();
		System.out.println("총 수입 - " + totalFee);
		model.addAttribute("totalFee", totalFee);

		model.addAttribute("area", area);

		return "/parking/status";
	}

}
