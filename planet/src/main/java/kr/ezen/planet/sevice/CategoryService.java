package kr.ezen.planet.sevice;

import java.util.List;

import kr.ezen.planet.vo.CategoryVO;

public interface CategoryService {

	List<CategoryVO> selectAll();

	int selectById(String name);

	String selectByName(int id);
	
}
