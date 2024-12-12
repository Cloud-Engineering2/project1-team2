/* ParkingController.java
* Parking Lot Management Service
* 주차 기능 관련 컨트롤러
* 작성자 : semi_lion2 (고민정, 박창조, 이홍비, 허선호)
* 최종 수정 날짜 : 2024-12-12
*
* ========================================================
* 프로그램 수정 / 보완 이력
* ========================================================
* 작업자       날짜       수정 / 보완 내용
* ========================================================
* 허선호    2024.12.12    더미 데이터를 이용한 getParkingrecordByVehicleno 메소드 작성 
* 
* 
* ========================================================
*/ 

package team2.parking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.AllArgsConstructor;
import team2.parking.dto.ParkingRecordDto;
import team2.parking.service.ParkingService;

@Controller
@AllArgsConstructor
public class ParkingController {
	
	private final ParkingService parkingService;
	
	
//	getParkingrecordByVehicleno 메소드 작성(인트로페이지에서 로그인 없이 차량 검색할 경우에 동작)
	@GetMapping(value = "/search/{vehicleno}")
	public String getParkingrecordByVehicleno(@PathVariable("vehicleno") Integer vehicleno, Model model) {
		String view = "/search";
		ParkingRecordDto record = parkingService.getParkingrecordByVehicleno(vehicleno);
		model.addAttribute("record", record);
		return view;
		
	}
	
}
