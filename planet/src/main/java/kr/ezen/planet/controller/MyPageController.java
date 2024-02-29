package kr.ezen.planet.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.ezen.planet.sevice.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Controller
public class MyPageController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/mypage")
	public ModelAndView myPage(Principal principal) {
	    ModelAndView modelAndView = new ModelAndView("mypage");
	    Long memberId = memberService.selectById(principal.getName());
	    modelAndView.addObject("posts", postService.getPostsByMemberId(memberId));
	    modelAndView.addObject("goods", goodService.getGoodsByMemberId(memberId));
	    modelAndView.addObject("trades", tradeService.getTradesByMemberId(memberId));
	    modelAndView.addObject("comments", commentService.getCommentsByMemberId(memberId));
	    modelAndView.addObject("reputations", reputationService.getReputationsByMemberId(memberId));
	    return modelAndView;
	}
	
	
	
}
