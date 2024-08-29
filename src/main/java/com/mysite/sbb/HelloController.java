package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 컨트롤러 기능 수행
public class HelloController {
	@GetMapping("/hello") // URL 요청 발생시 hello 메서드 실행
	@ResponseBody
	public String hello() {
		return "Hello Spring Boot Board";
	}
}
