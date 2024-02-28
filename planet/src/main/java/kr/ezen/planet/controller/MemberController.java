package kr.ezen.planet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.ezen.planet.sevice.MailService;
import kr.ezen.planet.sevice.MemberService;
import kr.ezen.planet.vo.MailVO;
import kr.ezen.planet.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private MailService mailService;

	@GetMapping(value = { "/", "/index", "/home", "/main" })
	public String index(Model model) {
		return "index";
	}

	@GetMapping("/mypage")
	public String mypage() {
		return "mypage";
	}

	@PostMapping("/pwCheck")
	@ResponseBody
	public ResponseEntity<?> pwCheck(Authentication auth, @RequestParam("userpw") String password) {
		UserDetails user = (UserDetails) auth.getPrincipal();
		if (passwordEncoder.matches(password, user.getPassword())) {
			log.info("pw 재확인 완료..");
			return ResponseEntity.ok("memberEdit");
		} else {
			log.info("비번틀림");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 틀렸습니다."); // 비밀번호 불일치
		}
	}

	@GetMapping("/memberInfo")
	public String memberInfo(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName(); // 현재 인증된 사용자의 이메일을 가져옵니다.

		// 이메일을 사용하여 회원 정보를 조회합니다.
		MemberVO member = memberService.selectByEmail(email);

		// 조회된 회원 정보를 모델에 추가합니다.
		model.addAttribute("member", member);

		// memberInfo.html 뷰를 반환합니다.
		return "memberInfo";
	}

	@GetMapping("/memberEdit")
	public String editMemberForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		MemberVO member = memberService.selectByEmail(email);
		model.addAttribute("member", member);
		return "memberEdit";
	}

	@GetMapping("/newpassword")
	public String newpassword(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		MemberVO member = memberService.selectByEmail(email);
		model.addAttribute("member", member);
		return "newpassword";
	}

	@PostMapping("/newpassword")
	@ResponseBody
	public ResponseEntity<?> newPassword(Authentication auth, @RequestParam("password") String password,
			@RequestParam("newpassword") String newpassword) {
		UserDetails user = (UserDetails) auth.getPrincipal();
		if (passwordEncoder.matches(password, user.getPassword())) {
			MemberVO vo = new MemberVO();
			vo.setEmail(user.getUsername());
			vo.setPassword(passwordEncoder.encode(newpassword));
			memberService.updatePassword(vo);

			log.info("pw변경완료");
			return ResponseEntity.ok("/logout");
		} else {
			log.info("비번틀림");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 틀렸습니다."); // 비밀번호 불일치
		}
	}
	@PostMapping("/deletOk")
	@ResponseBody
	public ResponseEntity<?> newPassword(Authentication auth, @RequestParam("userpw") String password) {
		UserDetails user = (UserDetails) auth.getPrincipal();
		if (passwordEncoder.matches(password, user.getPassword())) {
			memberService.deleteByEmail(user.getUsername());
			log.info("삭제완료");
			return ResponseEntity.ok("/logout");
		} else {
			log.info("비번틀림");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 틀렸습니다."); // 비밀번호 불일치
		}
	}
	
	
	@PostMapping("/updateOk")
	public String updateOK(@ModelAttribute(value = "vo") MemberVO vo) {
		memberService.update(vo);
		return "redirect:/memberInfo";
	}

	@GetMapping(value = { "/login" })
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		if (error != null)
			model.addAttribute("error", "error");
		if (logout != null)
			model.addAttribute("logout", "logout");
		return "login";
	}

	@GetMapping("/join")
	public String joinForm(HttpSession session) {
		// 현재 로그인이 되어있는데 회원가입을 하려고 한다. 막아야 한다.
		if (session.getAttribute("user") != null) {
			// 강제로 로그아웃 시키고(생략 가능)
			session.removeAttribute("user"); // 세션에 회원정보만 지운다.
			session.invalidate(); // 세션자체를 끊고 다시 연결
			return "redirect:/";
		}
		return "join";
	}

	@GetMapping("/joinOk")
	public String joinOkGet() {
		return "redirect:/";
	}

	@PostMapping("/joinOk")
	public String joinOkPost(@ModelAttribute(value = "vo") MemberVO vo) {
		memberService.insert(vo);
		return "redirect:/login";
	}



	// @Autowired
	// private JdbcTemplate jdbcTemplate;

	@GetMapping("favicon.ico")
	public String favicon() {
		return "redirect:assets/favicon.ico"; // 실제 favicon.ico가 위치한 경로로 수정
	}

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// @GetMapping(value = { "/dbInit" })
	// public String dbInit() {
	// jdbcTemplate.update("update member set password=? where email=?",
	// passwordEncoder.encode("1234"),"planetad0202@gmail.com");

	// return "redirect:/";
	// }


	
	@GetMapping(value = "/join/emailCheck", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String mailCheck(@RequestParam(value = "email")String email) {
		return memberService.mailCheck(email)+"";
	}
	
	@GetMapping(value = "/join/nicknameCheck", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String nicknameCheck(@RequestParam(value = "nickname")String nickname) {
		return memberService.nicknameCheck(nickname)+"";
	}
	
	@GetMapping("/forgot-password")
	public String forgotPassword(){
		return "forgot-password";
	}
	
	@PostMapping("forgot-password")
	public String forgotPassword2(@RequestParam("email") String email){
		MailVO vo = mailService.creatMail(email);
		mailService.mailSend(vo);
		return "redirect:/login";
	}

}