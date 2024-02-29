package kr.ezen.planet.sevice;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ezen.planet.dao.CategoryDAO;
import kr.ezen.planet.vo.CategoryVO;

@Service("categoryservice")
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;

	@Override
	public List<CategoryVO> selectAll() {
		List<CategoryVO> list = null;
		try {
			list = categoryDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public int selectById(String name) {
		int id = 0;
		try {
			id = categoryDAO.selectById(name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public String selectByName(int id) {
		String name = "";
		try {
			name = categoryDAO.selectByName(id);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return name;
	}

}
