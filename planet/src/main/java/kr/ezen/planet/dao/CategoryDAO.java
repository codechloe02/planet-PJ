package kr.ezen.planet.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.ezen.planet.vo.CategoryVO;

@Mapper
public interface CategoryDAO {
	List<CategoryVO> selectAll() throws SQLException;

	int selectById(String name) throws SQLException;

	String selectByName(int id) throws SQLException;

}
