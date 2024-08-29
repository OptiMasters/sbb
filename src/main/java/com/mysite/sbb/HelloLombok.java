package com.mysite.sbb;

import lombok.Getter;
//import lombok.Setter;
//import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
//@Setter
@Getter
public class HelloLombok {
	private final String hello;
	private final int lombok;
	// 뒤에 따라오는 자료형과 변수 등을 변경할 수 없게 만드는 키워드
	
	public HelloLombok(String hello, int lombok) {
		this.hello=hello;
		this.lombok=lombok;
	}
	
	public static void main(String[] args) {
		HelloLombok helloLombok=new HelloLombok("헬로",5);
//		helloLombok.setHello("헬로");
//		helloLombok.setLombok(5);
		
		System.out.println(helloLombok.getHello());
		System.out.println(helloLombok.getLombok());
	}
}
// 컴파일 단계에서 롬복이 대신 생성해 주므로 Getter와 Setter 메서드가 실제로 포함
// Getter: 내부의 멤버변수에 저장된 값을 외부로 리턴하며,
// 메개변수는 없고, 리턴값만 있는 메서드로 정의
// Setter: 외부로부터 데이터를 전달받아 멤버변수에 저장하며,
// 매개변수만 있고, 리턴값은 없는 메서드로 정의