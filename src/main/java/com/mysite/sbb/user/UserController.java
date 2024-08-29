package com.mysite.sbb.user;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.dao.DataIntegrityViolationException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	@GetMapping("/signup") // URL이 GET으로 요청되면 회원 가입을 위한 템플릿을 렌더링
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	@PostMapping("/signup") // POST로 요청되면 회원 가입을 진행
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signup_form";
		}
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2","passwordInCorrect","2개의 패스워드가 일치하지 않습니다.");
			return "signup_form"; // bindingResult.rejectValue(필드명, 오류 코드, 오류 메시지)
		}
		try { // > 사용자로부터 전달받은 데이터를 저장 
			userService.create(userCreateForm.getUsername(),userCreateForm.getEmail(),userCreateForm.getPassword1());
		}catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed","이미 등록된 사용자입니다.");
			return "signup_form";				// > 예외에 관한 구체적인 오류 메시지를 출력
		}catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed",e.getMessage());
			return "signup_form";
		}
		return "redirect:/";
	}
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
}
