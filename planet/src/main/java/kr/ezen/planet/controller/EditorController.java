package kr.ezen.planet.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		return "editor";
	}

	@GetMapping("/postProduct")
	public String summernoteResult2(Model model) {
		return "redirect:/";
	}

	@PostMapping("/postProduct")
	public String postProduct(HttpServletRequest request, Authentication auth,@RequestParam("categoryid") String  categoryid , @RequestParam("price") String price,
			@ModelAttribute ProductVO productVO, RedirectAttributes redirectAttributes) {
		
		log.info("==============================" + categoryid);
		
		//log.info("--------------------" + category_id);
		
		productVO.setCategory_id(1);
		String email = null;
		if (auth != null) {
			Object prinipal = auth.getPrincipal();
			if (prinipal instanceof UserDetails) {
				email = ((UserDetails) prinipal).getUsername();
			} else {
				email =prinipal.toString();
			}
		}
		int member_id = memberService.findUserIdByEmail(email);
		log.info("=======================" + email);
		log.info("=======================" + member_id);
		productVO.setMember_id(member_id); // 사용자 ID 설정
		int cost = Integer.parseInt(price);
		log.info(price);

		productVO.setCost(cost);
		productService.insert(productVO); // 제품 정보를 데이터베이스에 저장

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

	@PostMapping("/fileupload")
	@ResponseBody
	public Map<String, Object> fileUpload(HttpServletRequest request,
			@RequestPart(value = "upload", required = false) MultipartFile upload) {
		// 반드시 받는 이름이 "upload"이어야 한다.
		// json 데이터로 등록
		// {"uploaded" : 1, "fileName" : "test.jpg", "url" : "/img/test.jpg"}
		// 이런 형태로 리턴이 나가야함.
		// --------------------------------------------------------------------------------------
		// 서버의 업로드될 경로 확인
		String uploadPath = request.getServletContext().getRealPath("/ckeditor/");
		// 파일 객체 생성
		File file2 = new File(uploadPath);
		// 폴더가 없다면 폴더를 생성해준다.
		if (!file2.exists()) {
			file2.mkdirs();
		}
		log.info("서버 실제 경로 : " + uploadPath);
		// --------------------------------------------------------------------------------------
		String saveName = "";
		String saveFileName = "";

		if (upload != null && upload.getSize() > 0) { // 파일이 넘어왔다면
			try {
				// 저장파일의 이름 중복을 피하기 위해 저장파일이름을 유일하게 만들어 준다.
				saveFileName = UUID.randomUUID() + "_" + upload.getOriginalFilename();
				// 파일 객체를 만들어 저장해 준다.
				File saveFile = new File(uploadPath, saveFileName);
				// 파일 복사
				FileCopyUtils.copy(upload.getBytes(), saveFile);
				saveName = request.getContextPath() + "/ckeditor/" + saveFileName;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/*
		 * VO를 만들었다면 CkeditorResultVO vo = new CkeditorResultVO();
		 * vo.setFileName(saveFileName); vo.setUploaded(1); vo.setUrl(saveName);
		 * log.info("리턴값 : " + vo); return vo;
		 */
		// VO가 없다면 맵으로 받아 처리
		Map<String, Object> map = new HashMap<>();
		map.put("fileName", saveFileName);
		map.put("uploaded", 1);
		map.put("url", saveName);
		return map;
	}
	// ---------------------------------------------------------------------------------------------------------------
}
