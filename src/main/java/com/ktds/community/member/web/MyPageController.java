package com.ktds.community.member.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community.constants.Member;
import com.ktds.community.member.vo.MemberVO;
import com.ktds.community.service.CommunityService;
import com.ktds.community.service.MemberService;
import com.ktds.community.vo.CommunityVO;

@Controller
public class MyPageController {

	private MemberService		memberService;
	private CommunityService 	communityService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	@RequestMapping("/mypage/communities")
	public ModelAndView viewMyCommunities(HttpSession session) {
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		
		List<CommunityVO> myCommunities = communityService.readMyCommunities(member.getId());
		
		ModelAndView view = new ModelAndView();
		view.setViewName("/member/mypage/myCommunities");
		view.addObject("myCommunities", myCommunities);
		
		return view;
	}
	@RequestMapping("/mypage/communities/delete")
	public String doDeleteMyCommunitiesAction( HttpSession session, @RequestParam List<Integer> delete) {
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		
		communityService.deleteCommunities(delete, member.getId());
		
		return "redirect:/mypage/communities";
	}
	
	
	
	
	
	
}