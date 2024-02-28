package kr.ezen.planet.sevice;

import kr.ezen.planet.vo.MailVO;

public interface MailService {

	String getTempPW();
	MailVO creatMail(String email);
	void mailSend(MailVO vo);
	boolean updatePassword(String str, String email);
}
