package com.mysite.sbb.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true) // 유일한 값만 저장할 수 있음 (중복저장불가) > DB에 유니크 인덱스로 생성
	private String username;
	
	private String password;
	
	@Column(unique=true) // 유일한 값만 저장할 수 있음 (중복저장불가) > DB에 유니크 인덱스로 생성
	private String email;
}
