package com.ktds.community.member.web;

import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community.constants.Member;
import com.ktds.community.member.vo.MemberVO;
import com.ktds.community.service.CommunityService;
import com.ktds.community.service.MemberService;

@Controller
public class MemberController {
	
	
	private MemberService memberService;
	private CommunityService communityService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewLoginPage(HttpSession sesstion) { //세션을 바로 가지고 올 수 있음
		
		if( sesstion.getAttribute(Member.USER) != null ) {
			return "redirect:/";
		}//로그인이 필요한 페이지에 모두 넣어주면됨
		
		return "member/login";
	}
	
	@RequestMapping( value = "/regist", method = RequestMethod.GET )
	public String viewRegistPage() {
		return "member/regist";
	}
	
//	3-19 못따라간내용
//	프로세스 원은 탈퇴를 위한 페스워드 받아오는 페이지 작업.
	@RequestMapping("/delete/process1")
	public String viewVerifyPage()	{
		return "member/delete/process1";
	}
	
//	3-19 못따라간내용
//	프로세스 투는 작성한 글 전체를 보고 지우는 페이지.
	@RequestMapping("delete/process2")
	public ModelAndView viewDeleteMyCommunityPage(@RequestParam(required=false, defaultValue="") String password, HttpSession session) {
	
		System.out.println(password);
		if(password.length() ==0) {
			return new ModelAndView("error/404");
		}

//		아이디와 페스워드 정보를 가져온다.
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		member.setPassword(password);

		
		MemberVO verifyMember = memberService.readMember(member);
		if (verifyMember == null) {
			return new ModelAndView("redirect:/delete/process1");
		}
		
		//TODO 내가 작성한 게시글의 개수 가져오기.
		int myCommmunitiesCount = communityService.readMyCommunitiesCount(verifyMember.getId());
		
		ModelAndView view = new ModelAndView();
		view.setViewName("member/delete/process2");
		view.addObject("myCommunitiesCount", myCommmunitiesCount);
		// 랜덤 값을 토큰으로 보내준다. (중복이 절대 일어나지 않는 난수를 주는 방법.)
		// UUID는 nanoSeconds로 난수를 만들어낸다. (굉장히 안전한 난수.)
		String uuid = UUID.randomUUID().toString();
		//session에 uuid를 넣어뒀기때문에 언제든 꺼내서 볼 수 있다.
		session.setAttribute("__TOKEN__", uuid);
		view.addObject("token", uuid); 
		
		return view;
	}
	
	@RequestMapping( value = "/regist", method = RequestMethod.POST)
	public ModelAndView doRegistAction(@ModelAttribute("registForm") @Valid MemberVO memberVO, Errors errors) {
		
		if(errors.hasErrors()) {
			return new ModelAndView("member/regist");
		}
		
		if (memberService.createMember(memberVO)) {
			return new ModelAndView("redirect:/login");
		}
		
		return new ModelAndView("redirect:/login");
	}
	
	
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String doLoginAction( MemberVO memberVO, HttpServletRequest request) {
		System.out.println("여기까지왔드앙 여기가 실행이 안돼");
		HttpSession session = request.getSession();
	      
	    MemberVO loginMember = memberService.readMember(memberVO);
	      
//	    System.out.println(loginMember.getId());
	    if ( loginMember != null ) {
	       session.setAttribute(Member.USER, loginMember);
	       System.out.println(loginMember.getId());
	       return "redirect:/";
	         
	    }
	    return "redirect:/login";
	/*	if( memberVO.getId() == null || memberVO.getId().length() == 0 ) {
			session.setAttribute("status", "emptyId");
			return new ModelAndView("redirect:/login");
		}
		
		if( memberVO.getPassword() == null || memberVO.getPassword().length() == 0 ) {
			session.setAttribute("status", "emptyPassword");
			return new ModelAndView("redirect:/login");
		}
	*/
	
//	MemberVO loginMeZmber = memberService.readMember(memberVO);
//		
//		if( loginMember != null) {
//			session.setAttribute(Member.USER, loginMember);
//			return "redirect:/";
//		}
//		return "redirect:/login";
//		
		
/*//		FIXME DB에 계정이 존재하지 않을 경우로 변경	
		if( memberVO.getEmail().equals("admin") &&
			memberVO.getPassword().equals("1234")) {
			
			memberVO.setNickname("관리자");
			
			session.setAttribute(Member.USER, memberVO);//인터페이스 만들어준거
			//세션에 로그인 저장 상태유지
			
			session.removeAttribute("status");
			//로그인에 성공하면 세션을 지워줌 
			
			return new ModelAndView("redirect:/");
		}
		
		
		if( error.hasErrors() ) {
			ModelAndView view = new ModelAndView();
			view.setViewName("member/login");
			return view;
		}
	
		//로그인이 실패했을때
		session.setAttribute("status", "fail");
		
//		return new ModelAndView("redirect:/login");
		return new ModelAndView("redirect:/login");
	
		*/
	
	}
	
	@RequestMapping("/logout")
	public String doLogoutAction(HttpSession session) {
		
		//삭제는 하나의 키만 지우는 것
		//세션 소멸 - 세션자체를 날림
		
		session.invalidate();
		return "redirect:/login";
		
		
		
	}
	
	@RequestMapping("/account/delete/{deleteFlag}")
	public String doDeleteAction(@RequestParam(required=false, defaultValue="") String token, @PathVariable String deleteFlag, HttpSession session)	{
		
		String sessionToken = (String) session.getAttribute("__TOKEN__");
//		null의 의미는 process1을 통과하지 않은것. 다음 token이 같지 않은경우는 임의의 token을 누군가 일부러 집어넣고 온경우.
		if (sessionToken == null || !sessionToken.equals(token)) {
			return "error/404";
		}
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		if(member==null)	{
			return	"redirect:/login";
		}
//		nullExeption을 미리 체크하고 넘긴다. (member)값이 null이 될 수 있으므로.
		int id = member.getId();
		
		if(memberService.deleteMember(id, deleteFlag))	{
			session.invalidate();
		}
		
		return	"member/delete/delete";
	}

}
