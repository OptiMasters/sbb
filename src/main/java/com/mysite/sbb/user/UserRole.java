package com.mysite.sbb.user;

import lombok.Getter;

@Getter // 상수는 값을 변경할 필요가 없으므로 @Setter 없이
public enum UserRole { // enum 자료형(열거 자료형)으로 작성
	ADMIN("ROLE_ADMIN"), // 상수
	USER("ROLE_USER"); // 상수
	
	UserRole(String value){
		this.value=value;
	}
	private String value;
}
