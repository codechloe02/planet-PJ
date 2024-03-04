package kr.ezen.planet.sevice;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import kr.ezen.planet.vo.MemberVO;

public interface MemberService extends UserDetailsService {
	void insert(MemberVO memberVO);

	void update(MemberVO memberVO);

	void updatePassword(MemberVO memberVO);

	void deleteByEmail(String email);

	MemberVO selectByNickname(String nickname);

	MemberVO selectById(int id);

	MemberVO selectByEmail(String email);

	int mailCheck(String email);

	int nicknameCheck(String nickname);

	List<MemberVO> selectAll();

	String pwCheck(String email);

	int findUserIdByEmail(String email);

}