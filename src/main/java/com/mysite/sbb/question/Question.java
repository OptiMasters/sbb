package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.comment.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set; // voter 속성값이 서로 중복되지 않기 위해

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany; // 1:N 관계
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany; // 다대다 관계

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
	@Id // id 속성을 기본키로 지정
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 해당 속성에 값을 일일이 입력하지 않아도 자동으로 1씩 증가하여 저장
	private Integer id; // 고유 번호
	
	@Column(length=200) // 열의 길이
	private String subject; // 제목
	
	@Column(columnDefinition="TEXT") // ‘텍스트’를 열 데이터로
	private String content; // 내용
	
	private LocalDateTime createDate; // 작성 일시
	private LocalDateTime modifyDate;
	
								// 질문을 삭제하면 그에 달린 답변들도 함께 삭제
	@OneToMany(mappedBy="question",cascade=CascadeType.REMOVE)
	private List<Answer> answerList;
	
	@ManyToOne // 사용자 한 명이 질문을 여러 개 작성할 수 있기 때문
	private SiteUser author;
	
	@ManyToMany
	Set<SiteUser> voter;
	
	@OneToMany(mappedBy="question")
	private List<Comment> commentList;
}
// 엔티티는 데이터베이스와 바로 연결되므로
// 데이터를 자유롭게 변경할 수 있는 Setter 메서드를 허용하는 것이 안전하지 않다