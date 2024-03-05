package kr.ezen.planet.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import kr.ezen.planet.vo.ProductVO;

@Mapper
public interface ProductDAO {

	void insert(ProductVO productVO) throws SQLException;



}
