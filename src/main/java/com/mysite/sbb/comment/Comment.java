package com.mysite.sbb.comment;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition="TEXT")
	private String content;

	@ManyToOne
	private SiteUser author;
		
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
	
	@ManyToOne
	private Question question;
	
	@ManyToOne
	private Answer answer;

	public Integer getQuestionId() { // 댓글을 통해 질문의 id 값을 리턴하는 메서드
		Integer result=null;
		if (this.question != null) {
			result=this.question.getId();
		} else if (this.answer != null) {
			result = this.answer.getQuestion().getId();
		}
		return result;
	}
}
