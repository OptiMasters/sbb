package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.comment.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne; // N:1 관계
import jakarta.persistence.ManyToMany; // 다대다 관계
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition="TEXT")
	private String content;
	
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
	
	@ManyToOne
	private Question question;
	// 질문 엔티티를 참조하기 위해 question 속성을 추가
	// Answer(답변) 엔티티의 question 속성과 Question(질문) 엔티티가 서로 연결
	// 실제 데이터베이스에서는 외래키(foreign key) 관계가 생성
	// 부모는 Question, 자식은 Answer(부모자식관계)
	
	@ManyToOne // 사용자 한 명이 답변을 여러 개 작성할 수 있기 때문
	private SiteUser author;
	
	@ManyToMany
	Set<SiteUser> voter;
	
	@OneToMany(mappedBy="answer")
	private List<Comment> commentList;
}
