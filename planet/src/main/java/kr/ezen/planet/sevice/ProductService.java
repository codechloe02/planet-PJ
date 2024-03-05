package kr.ezen.planet.sevice;

import java.util.List;

import kr.ezen.planet.vo.ProductVO;

public interface ProductService {

	void insert(ProductVO productVO);

	List<ProductVO> selectAll();
}
