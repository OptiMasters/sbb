package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

// JPA가 제공하는 인터페이스 중 하나로 CRUD 작업을 처리하는 메서드들을 이미 내장
public interface QuestionRepository extends JpaRepository<Question,Integer>{
	Question findBySubject(String subject);
	Question findBySubjectAndContent(String subject, String content);
	
	List<Question> findBySubjectLike(String subject);
	Page<Question> findAll(Pageable pageable);
	Page<Question> findAll(Specification<Question> spec, Pageable pageable); //Specification과 Pageable 객체를 사용하여 DB에서 Question 엔티티를 조회한 결과를 페이징하여 반환
}
