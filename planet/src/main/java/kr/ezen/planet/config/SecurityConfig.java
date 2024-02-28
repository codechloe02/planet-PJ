package kr.ezen.planet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import kr.ezen.planet.sevice.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// csrf 토큰을 사용하지 않겠다.
		http.csrf(AbstractHttpConfigurer::disable);
		// 인증 설정
		http.authorizeHttpRequests((authorizeRequests) -> {
			authorizeRequests
					// 지정 주소에 대한 권한 설정 : .permitAll() 은 권한이 없다.(누구나 접근 가능)
					.requestMatchers("/", "/home", "/index", "/main", "/join", "/login","/test/**").permitAll()
					// 회원가입 폼과 회원가입 완료는 누구나 접근 가능
					.requestMatchers("/join", "/joinOk","/join/**").permitAll()
					// 지정 주소에 대한 권한 설정 **은 하위폴더 포함 모두
					.requestMatchers("/css/**", "/js/**", "/images/**", "/upload/**").permitAll()
					.requestMatchers("/mypage","/memberUpdate").hasRole("USER")
					// 지정 주소에 대한 권한 설정 : hasRole(권한)은 지정 권한이 있는 사용자만 접근이 가능하다.
					.requestMatchers("/admin", "/admin/**").hasRole("ADMIN").requestMatchers("/dba", "/dba/**")
					.hasAnyRole("ADMIN")
					// 그 이외의 요청은 인증된 사용자만 접근이 가능하다.
					.anyRequest().authenticated();
		});

		http.formLogin((form) ->  {
			// 로그인 주소
			form.loginPage("/login").permitAll()
					// 로그인 폼의 name속성값이 username과 password가 아니라면 변경해준다.
					.usernameParameter("username").passwordParameter("password")
					// 로그인 성공후 이동할 주소 지정
					.defaultSuccessUrl("/")
					// 로그인 성공시 처리할 내용을 추가로 지정 가능하다.
					.successHandler(new LoginSuccessHandler());
		});
		// 로그아웃시 처리 내용 지정
		http.logout((logout) ->
		// 권한 설정
		logout.permitAll()
				// 로그아웃후 이동할 주소 지정
				.logoutSuccessUrl("/")
				// 세션 정보를 지울지 여부
				.invalidateHttpSession(true));
		// 완성해서 리턴(객체 등록)
		return http.build();
	}

	@Bean("passwordEncoder")
	BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();

	}

	@Autowired
	private MemberService memberService;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService).passwordEncoder(new BCryptPasswordEncoder());
	}
}