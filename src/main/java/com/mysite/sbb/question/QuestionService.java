package com.mysite.sbb.question;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.DataNotFoundException;

import com.mysite.sbb.answer.Answer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
// id값에 해당하는 질문 데이터가 없을 경우에는 예외 클래스 실행
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;;

// questionRepository 객체는 @RequiredArgsConstructor에 의해 생성자 방식으로 주입
@RequiredArgsConstructor
@Service
public class QuestionService {
	private final QuestionRepository questionRepository;
	
	private Specification<Question> search(String kw){ // 검색어를 가리키는 kw를 입력받아 쿼리의 조인문과 where문을 Specification 객체로 생성하여 리턴하는 메서드
		return new Specification<>() {
			private static final long serialVersionUID=1L;
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true);//중복을 제거
				Join<Question,SiteUser> u1=q.join("author",JoinType.LEFT);
				Join<Question,Answer>a=q.join("answerList",JoinType.LEFT);
				Join<Answer, SiteUser>u2=a.join("author",JoinType.LEFT);
				return cb.or(cb.like(q.get("subject"), "%"+kw+"%"),//제목)
					   cb.like(q.get("content"), "%"+kw+"%"),// 내용
					   cb.like(u1.get("username"),"%"+kw+"%"),// 질문작성자
					   cb.like(a.get("content"), "%"+kw+"%"),// 답변 내용
					   cb.like(u2.get("username"),"%"+kw+"%")); // 답변작성자
			}
		};
	}
	
	public List<Question> getList(){
		return this.questionRepository.findAll();
	}
	public Question getQuestion(Integer id) {
		Optional<Question> question=this.questionRepository.findById(id);
		if (question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}
	public Page<Question> getList(int page, String kw){
		List<Sort.Order> sorts=new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate")); // 내림차순
		Pageable pageable=PageRequest.of(page, 10,Sort.by(sorts));// 게시물 최신순 조회
		
		Specification<Question> spec=search(kw);
		return this.questionRepository.findAll(spec, pageable);
	}
	public void create(String subject, String content, SiteUser user) {
		Question q=new Question();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		this.questionRepository.save(q);
	}
	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}
	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	} // 로그인한 사용자를 질문 엔티티에 추천인으로 저장
}
