package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import java.util.List;
import java.security.Principal;

import jakarta.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import lombok.RequiredArgsConstructor;;

@RequestMapping("/question")
@RequiredArgsConstructor // questionService 객체도 @RequiredArgsConstructor에 의해 생성자 방식으로 주입
@Controller // 컨트롤러를 통해 URL 매핑 성공
public class QuestionController {
	private final QuestionService questionService;
	private final UserService userService;
	@GetMapping("/list") // /question/list 동일
//	@ResponseBody // 템플릿을 사용으로 없어도 됨
	public String list(Model model, @RequestParam(value="page",defaultValue="0") int page, @RequestParam(value="kw", defaultValue="")String kw) { // 매개변수를 Model로 지정하면 객체 자동 생성
		// 질문 목록 데이터인 questionList를 생성히고,
		// Model 객체에 ‘questionList’라는 이름으로 저장
		Page<Question> paging=this.questionService.getList(page,kw);
		model.addAttribute("paging",paging);
		model.addAttribute("kw",kw);
		return "question_list";
	}
	@GetMapping(value="/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question",question);
		return "question_detail";
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create") // 화면에서 입력한 subject와 content를 매개변수로 받는다
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		SiteUser siteUser=this.userService.getUser(principal.getName());
		this.questionService.create(questionForm.getSubject(),questionForm.getContent(),siteUser); // 질문 데이터(subject, content)를 저장하는 코드
		return "redirect:/question/list"; // 질문 저장 후 질문 목록으로 이동)
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
		Question question=this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
		}
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		return "question_form";
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,Principal principal, @PathVariable("id") Integer id) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		Question question=this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
		}
		this.questionService.modify(question, questionForm.getSubject(),questionForm.getContent());
		return String.format("redirect:/question/detail/%s",id);
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal,@PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다.");
		}
		this.questionService.delete(question);
		return "redirect:/";
	}
	@PreAuthorize("isAuthenticated()") // 추천 기능 로그인한 사람만 사용 가능
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal,@PathVariable("id") Integer id) {
		Question question=this.questionService.getQuestion(id);
		SiteUser siteUser=this.userService.getUser(principal.getName());
		this.questionService.vote(question, siteUser);
		return String.format("redirect:/question/detail/%s", id);
	}
}
