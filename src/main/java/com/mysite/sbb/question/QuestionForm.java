package com.mysite.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
	@NotEmpty(message="제목은 필수항목입니다.")
	@Size(max=200) // 200 바이트보다 큰 제목이 입력되면 오류가 발생
	private String subject;
	
	@NotEmpty(message="내용은 필수항목입니다.")
	private String content;
}
// question_form.html 템플릿의 입력 항목인 subject와 content가
// 폼 클래스의 subject, content 속성과 바인딩
// 