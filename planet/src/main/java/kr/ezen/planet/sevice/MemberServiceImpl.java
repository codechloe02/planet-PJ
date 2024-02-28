package kr.ezen.planet.sevice;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ezen.planet.dao.MemberDAO;
import kr.ezen.planet.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Service("memberService")
@Slf4j
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;

	@Override
	public MemberVO loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info(" : " + username + "으로 호출");
		MemberVO memberVO = null;
		try {
			memberVO = memberDAO.selectByEmail(username);
			if(memberVO==null) {
				throw new UsernameNotFoundException("지정 아이디가 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info(memberVO + "리턴");
        return memberVO;
	}

	@Override
	public void insert(MemberVO memberVO) {
		if (memberVO != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			memberVO.setPassword(passwordEncoder.encode(memberVO.getPassword()));
			memberVO.setRole("ROLE_USER");
			try {
				memberDAO.insert(memberVO);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void update(MemberVO memberVO) {
		if (memberVO != null) {
			try {
				memberDAO.update(memberVO);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void updatePassword(MemberVO memberVO) {

		if (memberVO != null) {
			try {
				memberDAO.updatePassword(memberVO);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteByEmail(String email) {
		try {
			memberDAO.deleteByEmail(email);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public MemberVO selectByNickname(String nickname) {
		MemberVO memberVO = null;
		try {
			memberVO = memberDAO.selectByNickname(nickname);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberVO;
	}

	@Override
	public MemberVO selectById(int id) {
		MemberVO memberVO = null;
		try {
			memberVO = memberDAO.selectById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberVO;
	}

	@Override
	public MemberVO selectByEmail(String email) {
		MemberVO memberVO = null;
		try {
			memberVO = memberDAO.selectByEmail(email);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberVO;
	}

	@Override
	public int mailCheck(String email) {
		int count = 0;
		try {
			count = memberDAO.mailCheck(email);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	@Override
	public int nicknameCheck(String nickname) {
		int count = 0;
		try {
			count = memberDAO.nicknameCheck(nickname);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	@Override
	public List<MemberVO> selectAll() {
		List<MemberVO> list = null;
		try {
			list = memberDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public String pwCheck(String email) {
		try {
			return memberDAO.pwCheck(email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return email;
	}



}
