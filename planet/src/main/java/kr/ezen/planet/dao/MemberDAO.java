package kr.ezen.planet.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.ezen.planet.vo.MemberVO;

@Mapper
public interface MemberDAO {
	void insert(MemberVO memberVO) throws SQLException;

	void update(MemberVO memberVO) throws SQLException;

	void updatePassword(MemberVO memberVO) throws SQLException;

	void deleteByEmail(String email) throws SQLException;

	MemberVO selectByNickname(String nickname) throws SQLException;

	MemberVO selectById(int id) throws SQLException;

	MemberVO selectByEmail(String email) throws SQLException;

	int mailCheck(String email) throws SQLException;

	int nicknameCheck(String nickname) throws SQLException;

	List<MemberVO> selectAll() throws SQLException;
	
	String pwCheck(String email) throws SQLException;

}
