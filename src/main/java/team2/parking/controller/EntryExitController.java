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
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import team2.parking.dto.ParkingAreaDto;
import team2.parking.dto.ParkingRecordDto;
import team2.parking.dto.VehicleDto;
import team2.parking.entity.ParkingArea;
import team2.parking.entity.constant.VehicleType;
import team2.parking.service.ParkingAreaService;
import team2.parking.service.ParkingRecordService;
import team2.parking.service.VehicleService;

@RequiredArgsConstructor
@Controller
public class EntryExitController {
	
	private final ParkingAreaService parkingAreaService;
	private final VehicleService vehicleService;
	private final ParkingRecordService parkingRecordService;
	
	// 입출차 페이지
	@GetMapping("/admin/entry-exit/{loc}") // loc="A-01"
	public String getEntryOrExit(@PathVariable("loc") String location, ModelMap map) {
	
		ParkingAreaDto parkingArea = parkingAreaService.getArea(location); // loc에 해당하는 주차 구역 Dto 가져오기 
		map.addAttribute("parkingArea", parkingArea); // html
		
		return "/parking/entry-exit";
	}
	
	
	// 입차
	@PostMapping("/admin/entry") 
	public String registerVehicle(@RequestParam("area_id") Integer areaId,
								  @RequestParam("vehicle_type") String vehicleType, // entry-exit.html의 입차 form에서 받아온 차 종류, 전화번호, 차 번호를 기반으로 입차 등록
	        					  @RequestParam("owner_tel") String ownerTel,
	        					  @RequestParam("vehicle_number") String vehicleNumber) 
	{
	
		System.out.println("입차 시 차량 등록");
		
		// 폼 정보로 DTO 생성
		VehicleType type = VehicleType.getInstance(vehicleType); // VehicleType 상수
		VehicleDto vehicleDto = VehicleDto.of(vehicleNumber, type, ownerTel, false); // VehicleDto 생성		
		ParkingAreaDto parkingAreaDto = parkingAreaService.getArea(areaId); // ParkingAreaDto 생성
		ParkingRecordDto parkingRecordDto = ParkingRecordDto.of(vehicleDto, parkingAreaDto); // ParkingRecordDto 생성
		System.out.println(parkingRecordDto.getAreaId().getLocation());
		System.out.println(parkingRecordDto.getVehicleId().getNumber());
		
		// Vehicle 등록
		vehicleService.registerVehicle(vehicleDto); 
		// ParkingRecord 추가
		parkingRecordService.addParkingRecord(parkingRecordDto);
		
		// ParkingArea.inUse 변경
		parkingAreaService.updateInUse(areaId, true);
		
		return "redirect:/admin/entry-exit/A-09"; // (관리자) 주차현황 페이지로 이동
	}
	
	
	// 출차
	@PostMapping("/admin/exit/{loc}") 
	public String removeVehicle(@PathVariable("loc") String location, ModelMap map) {
		System.out.println("출차 시 차량 삭제");
		
		// Vehicle 삭제
		
		// ParkingArea.inUse 변경
		
		
		return null;
		
	}
	
}
