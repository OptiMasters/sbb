package com.mysite.sbb.comment;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import java.util.Optional;
import java.security.Principal;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
    private AnswerService answerService;
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value="/create/question/{id}")
	public String createQuestionComment(CommentForm commentForm) {
		return "comment_form";
	}
//	@PreAuthorize("isAuthenticated()")
//    @PostMapping(value = "/create/question/{id}")
//    public String createQuestionComment(@PathVariable("id") Integer id, @Valid CommentForm commentForm,
//            BindingResult bindingResult, Principal principal) {
//        Optional<Question> question = this.questionService.getQuestion(id);
//        Optional<SiteUser> user = this.userService.getUser(principal.getName());
//        if (question.isPresent() && user.isPresent()) {
//            if (bindingResult.hasErrors()) {
//                return "comment_form";
//            }
//            Comment c = this.commentService.create(question.get(), user.get(), commentForm.getContent());
//            return String.format("redirect:/question/detail/%s", c.getQuestionId());
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
//        }
//    }

	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/create/question/{id}")
	public String createQuestionComment(@PathVariable("id") Integer id, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
		Question question=this.questionService.getQuestion(id);
		SiteUser user=this.userService.getUser(principal.getName());
		if (question != null && user != null) {
			if (bindingResult.hasErrors()) {
				return "comment_form";
			}
			Comment c=this.commentService.createQuestion(question, user, commentForm.getContent());
			return String.format("redirect:/question/detail/%s", c.getQuestionId()); // 
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"entity not found");
		}
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modifyComment(CommentForm commentForm, @PathVariable("id") Integer id, Principal principal) {
		Optional<Comment> comment=this.commentService.getComment(id);
		if (comment.isPresent()) {
			Comment c= comment.get();
			if(!c.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
			}
			commentForm.setContent(c.getContent());
		}
		return "comment_form";
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modifyComment(@Valid CommentForm commentForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
		if (bindingResult.hasErrors()) {
			return "comment_form";
		}
		Optional<Comment> comment=this.commentService.getComment(id);
		if (comment.isPresent()) {
			Comment c=comment.get();
			if (!c.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
			}
			c=this.commentService.modify(c, commentForm.getContent());
			return String.format("redirect:/question/detail/%s",c.getQuestionId());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"entity not found");
		}
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String deleteComment(Principal principal, @PathVariable("id") Integer id) {
		Optional<Comment> comment=this.commentService.getComment(id);
		if (comment.isPresent()) {
			Comment c=comment.get();
			if (!c.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다.");
			}
			this.commentService.delete(c);
			return String.format("redirect:/question/detail/%s", c.getQuestionId());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"entity not found");
		}
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value="/create/answer/{id}")
	public String createAnswerComment(CommentForm commentForm) {
		return "comment_form";
	}
	
	// 답변의 댓글
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/create/answer/{id}")
	public String createAnswerComment(@PathVariable("id") Integer id, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
		Answer answer=this.answerService.getAnswer(id);
		SiteUser user=this.userService.getUser(principal.getName());
		if (answer != null && user != null) {
			if (bindingResult.hasErrors()) {
				return "comment_form";
			}
			Comment c=this.commentService.createAnswer(answer, user, commentForm.getContent());
			return String.format("redirect:/question/detail/%s", c.getQuestionId()); //  answer/detail 존재x
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"entity not found");
		}
	}
}
