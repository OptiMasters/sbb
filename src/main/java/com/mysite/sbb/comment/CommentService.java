package com.mysite.sbb.comment;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;
	
	// 질문
	public Comment createQuestion(Question question, SiteUser author, String content) {
		Comment c=new Comment();
		c.setContent(content);
		c.setCreateDate(LocalDateTime.now());
		c.setQuestion(question);
		c.setAuthor(author);
		c=this.commentRepository.save(c);
		return c;
	}
	
	// 답변
	public Comment createAnswer(Answer answer, SiteUser author, String content) {
		Comment c=new Comment();
		c.setContent(content);
		c.setCreateDate(LocalDateTime.now());
		c.setAnswer(answer);
		c.setAuthor(author);
		c=this.commentRepository.save(c);
		return c;
	}
	
	public Optional<Comment> getComment(Integer id){
		return this.commentRepository.findById(id);
	}
	public Comment modify(Comment c, String content) {
		c.setContent(content);
		c.setModifyDate(LocalDateTime.now());
		c=this.commentRepository.save(c);
		return c;
	}
	public void delete(Comment c) {
		this.commentRepository.delete(c);
	}
	

}
