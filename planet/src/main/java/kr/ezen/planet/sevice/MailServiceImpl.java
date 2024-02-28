package kr.ezen.planet.sevice;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ezen.planet.vo.MailVO;
import kr.ezen.planet.vo.MemberVO;

@Service("mailservice")
@Transactional
public class MailServiceImpl implements MailService {

	@Autowired
	MemberService memberService;
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public String getTempPW() {
		String uuid = UUID.randomUUID().toString();
		String str = uuid.replace("-", "").substring(0, 8);
		return str;

	}

	@Override
	public MailVO creatMail(String email) {
		String str = getTempPW();
		MailVO vo = new MailVO();
		vo.setAddress(email);
		vo.setTitle("planet 임시 비밀번호 안내드립니다.");
		vo.setMessage("안녕하세요. planet 입니다. 회원님의 임시 비밀번호 는   " + str + "    입니다.");
		updatePassword(str, email);
		return vo;

	}

	@Override
	public void mailSend(MailVO vo) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(vo.getAddress());
		message.setSubject(vo.getTitle());
		message.setText(vo.getMessage());
		message.setFrom("planetad0202@gmail.com");
		mailSender.send(message);
	}

	@Override

	public boolean updatePassword(String str, String email) {
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword = encoder.encode(str);
			MemberVO vo = memberService.selectByEmail(email);
			if (vo == null) {
				return false;
			}
			vo.setPassword(encodedPassword);
			memberService.updatePassword(vo);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
