package kr.ezen.planet.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import kr.ezen.planet.sevice.CategoryService;
import kr.ezen.planet.sevice.MemberService;
import kr.ezen.planet.sevice.ProductService;
import kr.ezen.planet.vo.CategoryVO;
import kr.ezen.planet.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Controller
@Slf4j
public class EditorController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ProductService productService;

	// 파일 업로드 처리 함
	@GetMapping("/editor")
	public String summernote2(Model model) {
		List<CategoryVO> categoryList = categoryService.selectAll();
		model.addAttribute("categoryList", categoryList);
		log.info("----------------------" + categoryList);
		return "editor";
	}

	@GetMapping("/postProduct")
	public String summernoteResult2(Model model) {
		return "redirect:/";
	}

	@PostMapping("/postProduct")
	public String postProduct(HttpServletRequest request, Authentication auth,
			@RequestParam("categoryid") String categoryid, @RequestParam("price") String price,
			@ModelAttribute ProductVO productVO, @RequestPart(value = "file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		String uploadPath = request.getServletContext().getRealPath("/static/upload/");
		// 파일 객체 생성
		File file2 = new File(uploadPath);
		// 폴더가 없다면 폴더를 생성해준다.
		if (!file2.exists()) {
			file2.mkdirs();
		}
		log.info("서버 실제 경로 : " + uploadPath);
		if (file != null && file.getSize() > 0) { // 파일이 넘어왔다면
			try {
				// 저장파일의 이름 중복을 피하기 위해 저장파일이름을 유일하게 만들어 준다.
				String saveFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
				// 파일 객체를 만들어 저장해 준다.
				File saveFile = new File(uploadPath, saveFileName);
				// 파일 복사
				FileCopyUtils.copy(file.getBytes(), saveFile);
				productVO.setImg(uploadPath+saveFileName);
			} catch (Exception e) {
			}
		} else {
			productVO.setImg("");
		}

		log.info("Category ID: " + categoryid);

		int category = Integer.parseInt(categoryid);
		productVO.setCategory_id(category);

		String email = null;
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal instanceof UserDetails) {
				email = ((UserDetails) principal).getUsername();
			} else {
				email = principal.toString();
			}
		}

		int member_id = memberService.findUserIdByEmail(email);
		log.info("Email: " + email);
		log.info("Member ID: " + member_id);

		productVO.setMember_id(member_id);
		int cost = Integer.parseInt(price);
		productVO.setCost(cost);

		productService.insert(productVO);

		return "redirect:/"; // 저장 후 리다이렉션할 페이지 경로
	}

	@PostMapping("/imageUpload")
	@ResponseBody
	public String imageUpload(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file) {
		// MultipartFile 이것이 파일을 알아서 받아준다.

		// --------------------------------------------------------------------------------------
		// 서버의 업로드될 경로 확인
		String uploadPath = request.getServletContext().getRealPath("/summernote/");
		// 파일 객체 생성
		File file2 = new File(uploadPath);
		// 폴더가 없다면 폴더를 생성해준다.
		if (!file2.exists()) {
			file2.mkdirs();
		}
		log.info("서버 실제 경로 : " + uploadPath);
		// --------------------------------------------------------------------------------------
		String saveName = "";
		// 파일받기 : 파일은 MultipartFile로 구분해서 받아준다.
		if (file != null && file.getSize() > 0) { // 파일이 넘어왔다면
			try {
				// 저장파일의 이름 중복을 피하기 위해 저장파일이름을 유일하게 만들어 준다.
				String saveFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
				// 파일 객체를 만들어 저장해 준다.
				File saveFile = new File(uploadPath, saveFileName);
				// 파일 복사
				FileCopyUtils.copy(file.getBytes(), saveFile);
				saveName = request.getContextPath() + "/summernote/" + saveFileName;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return saveName;
	}

}
