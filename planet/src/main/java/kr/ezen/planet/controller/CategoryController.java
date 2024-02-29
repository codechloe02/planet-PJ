package kr.ezen.planet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.ezen.planet.sevice.CategoryService;
import kr.ezen.planet.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Controller
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("list")
	public String catelist(Model model) {
		List<CategoryVO> list = categoryService.selectAll();
		log.info("하하하하");
		model.addAttribute("list", list);
		return "list";
	}

}
