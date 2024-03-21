package kr.ezen.planet.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.ezen.planet.vo.ProductVO;

@Mapper
public interface ProductDAO {

	void insert(ProductVO productVO) throws SQLException;

	List<ProductVO> selectAll() throws SQLException;

	// 2. 수정
	void update(ProductVO Product) throws SQLException;

	// 3. 삭제
	void delete(int id) throws SQLException;

	// 4. 1페이지 분량 얻기 (목록보기)
	List<ProductVO> selectProductList(Map<String, Integer> map) throws SQLException;

	// 5. 1개 얻기
	ProductVO selectByIdx(int id) throws SQLException;

	// 6. 전체 얻기
	int selectCount() throws SQLException;

	// 7. 검색
	List<ProductVO> searchProducts(String keyword) throws SQLException;

}
