package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	@GetMapping("/sbb") // 요청된 URL(/sbb)과의 매핑을 담당
	@ResponseBody // URL 요청에 대한 응답으로 문자열을 리턴
	public String index() {
		return "안녕하세요 sbb에 오신 것을 환영합니다.";
//		System.out.println("index");
	}
	@GetMapping("/")
	public String root() {
		return "redirect:/question/list"; // URL로 페이지를 리다이렉트
	}
}
