/* EntryExitController.java
* Parking Lot Management Service
* 입출차에 대한 컨트롤러
* 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
* 최종 수정 날짜 : 2024.12.12
*
* ========================================================
* 프로그램 수정 / 보완 이력
* ========================================================
* 작업자       날짜       수정 / 보완 내용
* ========================================================
* 고민정    2024.12.12    컨트롤러 생성 및 입출차 로직 작성
* ========================================================
*/ 

package team2.parking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import team2.parking.dto.ParkingAreaDto;
import team2.parking.service.ParkingAreaService;

@RequiredArgsConstructor
@Controller
public class EntryExitController {
	
	private final ParkingAreaService parkingAreaService;
	
	
	@GetMapping("/admin/entry-exit/{loc}") // 입출차 페이지
	public String getEntryOrExit(@PathVariable("loc") String location, ModelMap map) {
	
		ParkingAreaDto parkingArea = parkingAreaService.getArea(location); // loc에 해당하는 주차 구역 Dto
		map.addAttribute("parkingArea", parkingArea); // html
		System.out.println("되는 건가..?");
		
		return "/parking/entry-exit";
	}
	
	
	@PostMapping("/admin/entry") // 입차
	public String registerVehicle() {
		System.out.println("입차 시 차량 등록");
		return null;
	}
	
	
	@PostMapping("/admin/exit/{loc}") // 출차
	public String removeVehicle(@PathVariable("loc") String location, ModelMap map) {
		System.out.println("출차 시 차량 삭제");
		return null;
		
	}
	
}
