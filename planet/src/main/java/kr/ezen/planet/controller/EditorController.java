package kr.ezen.planet.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Controller
@Slf4j
public class EditorController {



	// 파일 업로드 처리 함
	@GetMapping("/summernote2")
	public String summernote2(Model model) {
		model.addAttribute("today", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd(E) hh:mm:ss")));
		return "summernote2";
	}

	@GetMapping("/summernoteResult2")
	public String summernoteResult2(Model model) {
		model.addAttribute("today", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd(E) hh:mm:ss")));
		return "redirect:/summernote2";
	}

	@PostMapping("/summernoteResult2")
	public String summernoteResult2(@RequestParam Map<String, String> param, Model model) {
		// MultipartFile 이것이 파일을 알아서 받아준다.
		model.addAttribute("map", param);
		return "summernoteResult2";
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
	public Map<String, Object> fileUpload(HttpServletRequest request,  @RequestPart(value = "upload", required = false) MultipartFile upload) {
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
		VO를 만들었다면
		CkeditorResultVO vo = new CkeditorResultVO();
		vo.setFileName(saveFileName);
		vo.setUploaded(1);
		vo.setUrl(saveName);
		log.info("리턴값 : " + vo);
		return vo;
		*/
		// VO가 없다면 맵으로 받아 처리
		 Map<String, Object> map = new HashMap<>();        
		 map.put("fileName", saveFileName);        
		 map.put("uploaded", 1);
		 map.put("url", saveName);
		 return map;
	}	
	//---------------------------------------------------------------------------------------------------------------
}
