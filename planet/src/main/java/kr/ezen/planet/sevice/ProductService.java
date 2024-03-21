package kr.ezen.planet.sevice;

import java.util.List;

import kr.ezen.planet.vo.CommonVO;
import kr.ezen.planet.vo.PagingVO;
import kr.ezen.planet.vo.ProductVO;

public interface ProductService {

	void insert(ProductVO productVO);

	List<ProductVO> selectAll();

	PagingVO<ProductVO> selectProductList(CommonVO cv);

	List<ProductVO> searchProducts(String keyword);

	ProductVO selectByIdx(int id);

	void update(ProductVO productVO);

	void delete(int productId);

	public int selectCount();
}
