package kr.ezen.planet.sevice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ezen.planet.dao.ProductDAO;
import kr.ezen.planet.vo.CommonVO;
import kr.ezen.planet.vo.PagingVO;
import kr.ezen.planet.vo.ProductVO;
import lombok.RequiredArgsConstructor;

@Service("productService")
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;

	@Override
	public void insert(ProductVO productVO) {
		if (productVO != null) {
			try {
				productDAO.insert(productVO);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<ProductVO> selectAll() {
		List<ProductVO> list = null;
		try {
			list = productDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public PagingVO<ProductVO> selectProductList(CommonVO cv) {
		PagingVO<ProductVO> pv = null;
		try {
			int totalCount = productDAO.selectCount();
			// 페이지 계산 완료
			pv = new PagingVO<>(totalCount, cv.getCurrentPage(), cv.getSizeOfPage(), cv.getSizeOfBlock());
			HashMap<String, Integer> map = new HashMap<>();
			map.put("startNo", pv.getStartNo());
			map.put("endNo", pv.getEndNo());
			pv.setList(productDAO.selectProductList(map));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pv;
	}

	@Override
	public List<ProductVO> searchProducts(String keyword) {
		return null;
	}

	@Override
	public ProductVO selectByIdx(int id) {
		ProductVO productVO = null;
		try {
			productVO = productDAO.selectByIdx(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productVO;
	}

	@Override
	public void update(ProductVO productVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int productId) {
		// TODO Auto-generated method stub

	}

	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
