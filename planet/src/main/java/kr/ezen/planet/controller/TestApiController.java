package kr.ezen.planet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ezen.planet.sevice.TestService;

@Configuration
@RestController
@RequestMapping(value = "/test")
public class TestApiController {

	@Autowired
	private TestService testService;
	
	@GetMapping("/today")
	public String today() {
		return "서버 시간 : " + testService.today();
	}
	/*
	// 아이디 중복확인에 사용할 메서드 추가
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/userIdCheck")
	public int userIdCheck(@RequestParam(value = "username")String username) {
		return memberService.selectCountByUsername(username);
	}
	*/
}
