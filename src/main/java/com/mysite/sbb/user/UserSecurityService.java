package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService{ // loadUserByUsername 메서드를 구현하도록 강제하는 인터페이스
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) // 사용자명(username)으로 스프링 시큐리티의 사용자(User) 객체를 조회하여 리턴
	throws UsernameNotFoundException{Optional<SiteUser>_siteUser=this.userRepository.findByusername(username);
	if (_siteUser.isEmpty()) { // 객체 조회
		throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
	}
	SiteUser siteUser=_siteUser.get();
	List<GrantedAuthority> authorities=new ArrayList<>();
	if ("admin".equals(username)) {
		authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
	} else {
		authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
	}
	return new User(siteUser.getUsername(),siteUser.getPassword(),authorities);
	}
}
