package kr.ezen.planet.sevice;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ezen.planet.dao.ProductDAO;
import kr.ezen.planet.vo.ProductVO;

@Service("productervice")
@Transactional
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

}
