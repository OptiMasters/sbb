package com.mysite.sbb.user;

import java.util.Optional;

import com.mysite.sbb.DataNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	public SiteUser create(String username,String email, String password) {
		SiteUser user=new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(user); // User 데이터를 생성하는 create 메서드를 추가
		return user;
	}
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser=this.userRepository.findByusername(username);
		if (siteUser.isPresent()) {
			return siteUser.get(); // 최종적으로 값을 끌어올려면 get() 메서드
		} else {
			throw new DataNotFoundException("siteuser not found");
		}
	}
}
