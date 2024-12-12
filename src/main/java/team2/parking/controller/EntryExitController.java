package team2.parking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EntryExitController {
	@GetMapping("/admin/entry-exit/{area}") // 주차 구역 조회
	public String getEntryExit(@PathVariable("area") String a) {
		System.out.println(a);
		return "/parking/entry-exit";
	}
}
