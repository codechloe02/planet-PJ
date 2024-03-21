package kr.ezen.planet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ezen.planet.sevice.CategoryService;
import kr.ezen.planet.sevice.MemberService;
import kr.ezen.planet.sevice.ProductService;
import kr.ezen.planet.vo.CategoryVO;
import kr.ezen.planet.vo.CommonVO;
import kr.ezen.planet.vo.MemberVO;
import kr.ezen.planet.vo.ProductVO;

@Controller
@Configuration
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CategoryService categoryService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST }) //
	public String products(@ModelAttribute(value = "cv") CommonVO cv, Model model) {
		model.addAttribute("pv", productService.selectProductList(cv));
		List<CategoryVO> categoryList = categoryService.selectAll();
		model.addAttribute("categoryList", categoryList);
		return "index";
	}

	// 게시글 상세 페이지로 이동하는 메서드

	@GetMapping("/product/{id}")
	public String productDetail(@PathVariable("id") int id, Model model) {
		// 여기서 id에 해당하는 게시글 정보를 가져와서 모델에 담아서 postDetail.html로 전달
		ProductVO productVO = productService.selectByIdx(id);
		MemberVO memberVO = memberService.selectById(productVO.getMember_id());
		model.addAttribute("product", productVO);
		model.addAttribute("member", memberVO);
		return "view";
	}

	@GetMapping("/inform")
	public String inform() {
		return "inform";
	}

}