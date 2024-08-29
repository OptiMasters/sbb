package com.mysite.sbb;

import com.mysite.sbb.question.QuestionService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional; // null값을 유연하게 처리하기 위한 클래스

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

@SpringBootTest // 스프링 부트의 테스트 클래스임을 의미
class SbbApplicationTests {
	@Autowired
	private QuestionService questionService;
	@Test
	void testJpa() {
		for (int i=1; i<= 300; i++) {
			String subject=String.format("테스트 데이터입니다:[%03d]", i);
			String content="내용무";
			this.questionService.create(subject,content,null);
		}
	}
}
//	@Autowired // 의존성 주입(DI)
//	private QuestionRepository questionRepository;
//	
//	@Autowired
//	private AnswerRepository answerRepository;
//	
//	@Transactional
//	@Test // testJpa 메서드가 테스트 메서드
//	void testJpa() {
//		Optional<Question> oq=this.questionRepository.findById(2);
//		
//		assertTrue(oq.isPresent());
//		Question q=oq.get();
//		
//		List<Answer> answerList=q.getAnswerList();
//		
//		assertEquals(1,answerList.size());
//		assertEquals("네 자동으로 생성됩니다.",answerList.get(0).getContent());
		
//		Optional<Answer> oa=this.answerRepository.findById(1);
//		assertTrue(oa.isPresent());
//		Answer a=oa.get();
//		assertEquals(2,a.getQuestion().getId());
		
//		Optional<Question> oq=this.questionRepository.findById(2);
//		
//		assertTrue(oq.isPresent());
//		Question q=oq.get();
//		
//		Answer a=new Answer();
//		a.setContent("네 자동으로 생성됩니다.");
//		a.setQuestion(q); // 어떤 질문의 답변인지 알기 위해서 Question 객체 필요
//		a.setCreateDate(LocalDateTime.now());
//		this.answerRepository.save(a);
		
//		assertEquals(2, this.questionRepository.count());
//		Optional<Question> oq=this.questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question q=oq.get();
//		this.questionRepository.delete(q);
//		assertEquals(1,this.questionRepository.count());
		// 리포지터리의 count 메서드는 테이블 행의 개수를 리턴
		
//		Optional<Question> oq=this.questionRepository.findById(1);
//		
//		assertTrue(oq.isPresent()); // 괄호 안의 값이 참인지 테스트
//		Question q=oq.get();
//		q.setSubject("수정된 제목");
//		this.questionRepository.save(q);
//		Question q=this.questionRepository.findBySubjectAndContent(
//				"sbb가 무엇인가요?","sbb에 대해서 알고 싶습니다.");
//		assertEquals(1,q.getId());

//		응답 결과가 여러 건인 경우 리포지터리 메서드의 리턴 타입을
//		Question이 아닌 List<Question>으로 작성

//		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
//		
//		Question q=qList.get(0);
//		assertEquals("sbb가 무엇인가요?",q.getSubject());
		
		// findAll = SELECT * FROM QUESTION (유사)
		// assertEquals(기대값, 실제값) = 테스트에서 예상한 결과와 실제 결과가 동일한지를 확인
		// 기댓값과 실젯값이 동일한지를 조사
		// ( JPA 또는 데이터베이스에서 데이터를 올바르게 가져오는지를 확인 )
		
//		// q1, q2라는 질문 엔티티의 객체를 생성하고
//		// QuestionRepository를 이용하여 그 값을 데이터베이스에 저장
//		Question q1=new Question();
//		q1.setSubject("sbb가 무엇인가요?");
//		q1.setContent("sbb에 대해서 알고 싶습니다.");
//		q1.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q1); // 첫번째 질문 저장
//		
//		Question q2=new Question();
//		q2.setSubject("스프링부트 모델 질문입니다.");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q2); // 두번째 질문 저장
//	}
//}
