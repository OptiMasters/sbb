package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration // 스프링의 환경 설정 파일
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티 제어를 받도록 만듬
@EnableMethodSecurity(prePostEnabled=true) // 로그인 여부를 판별할 때 사용한 @PreAuthorize 애너테이션을 사용하기 위해 반드시 필요한 설정
public class SecurityConfig {
	@Bean           // > 모든 요청 URL에 이 클래스가 필터로 적용되어 URL별로 특별한 설정을 할 수 있음				// > 다음은 인증되지 않은 모든 페이지의 요청을 허락
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {							// > 로그인하지 않더라도 모든 페이지에 접근
		http.authorizeHttpRequests((authorizeHttpRequests)->authorizeHttpRequests.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()).csrf((csrf)->csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))).headers((headers)->headers.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))).formLogin((formLogin)->formLogin.loginPage("/user/login").defaultSuccessUrl("/")).logout((logout)->logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")).logoutSuccessUrl("/").invalidateHttpSession(true));
		return http.build();																			
	}
	@Bean
	PasswordEncoder passwordEncoder() { // BCryptPasswordEncoder의 인터페이스
		return new BCryptPasswordEncoder();
	}
	@Bean	// > 사용자 인증 시 앞에서 작성한 UserSecurityService와 PasswordEncoder를 내부적으로 사용하여 인증과 권한 부여 프로세스를 처리
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
	throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
}
