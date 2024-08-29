package com.mysite.sbb.answer;

import org.springframework.data.jpa.repository.JpaRepository;

// JPA가 제공하는 인터페이스 중 하나로 CRUD 작업을 처리하는 메서드들을 이미 내장
public interface AnswerRepository extends JpaRepository<Answer,Integer>{
}
