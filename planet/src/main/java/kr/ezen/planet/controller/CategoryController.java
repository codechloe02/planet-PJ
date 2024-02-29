package kr.ezen.planet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.ezen.planet.sevice.CategoryService;
import kr.ezen.planet.vo.CategoryVO;

@Configuration
@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categorylist")
	public List<CategoryVO> categorylist() {
		return categoryService.selectAll();
	}

}
