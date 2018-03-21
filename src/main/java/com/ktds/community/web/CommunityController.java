package com.ktds.community.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.View;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community.constants.Member;
import com.ktds.community.member.vo.MemberVO;
import com.ktds.community.service.CommunityService;
import com.ktds.community.vo.CommunityVO;
import com.ktds.util.DownloadUtil;

import javafx.scene.input.KeyCombination.ModifierValue;

@Controller      
public class CommunityController {

	private CommunityService communityService;

	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;   
	}

	@RequestMapping("/") // 젤 첫화면이 리스트 페이지
	public ModelAndView viewListPage() {

		ModelAndView view = new ModelAndView();
		// WEB-INF/view/community/list.jsp
		view.setViewName("community/list");

		List<CommunityVO> communityList = communityService.getAll();

		view.addObject("communityList", communityList);

		return view;
	}

	// @GetMapping("/write")
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String viewWritePage() {

		return "/community/write";

	}

	// @PostMapping("/write") //spring 4.3부터 쓸수 있음
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public ModelAndView doWrite(@ModelAttribute("writeFrom") @Valid CommunityVO communityVO, Errors error,
			HttpServletRequest request) { // 클래스에 맞게 스프링이 가져옴 그래서 이름을 똑같이 해줘야함

		if (error.hasErrors()) {
			ModelAndView view = new ModelAndView();
			view.setViewName("community/write");
			view.addObject("communityVO", communityVO);
			return view;
		}

		String requestIp = request.getRemoteAddr();
		communityVO.setRequestIP(requestIp);
		communityVO.save();
		boolean isSuccess = communityService.createCommunity(communityVO);

		if (isSuccess) {

			return new ModelAndView("redirect:/"); // 해당 url로 이동

		}
		return new ModelAndView("redirect:/write");
	}

	@RequestMapping("/view/{id}")
	public ModelAndView viewViewPage(@PathVariable int id) {

		ModelAndView view = new ModelAndView();
		view.setViewName("community/view");

		CommunityVO community = communityService.refresh(id);

		view.addObject("community", community);

		return view;
	}

	@RequestMapping("/recommend/{id}")
	public String recommend(@PathVariable int id) {

		communityService.recommend(id);

		return "redirect:/view/" + id;
	}

	@RequestMapping("/read/{id}")
	public String read(@PathVariable int id) {

		if (communityService.incrementViewCount(id) > 0) {
			return "redirect:/view/" + id;
		}
		return "redirect:/";
	}

	@RequestMapping("/get/{id}")
	public void download(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {

		CommunityVO community = communityService.getOne(id);
		String filename = community.getDisplayFilename();

		DownloadUtil download = new DownloadUtil("D:\\uploadFiles/" + filename);

		try {
			download.download(request, response, filename);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);// 감탄코드
		}

	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id, HttpSession session) {
		// primitiveType long >> int = (int)숫자 ,, 같은 계열일때 명시할 수 있다.
		// referenceType은 상속이나 구현관계에 명시할 수 있다.
		// 아래 member와 세션의 getAttribute된 정보는 상속관계므로 명시형변환 가능
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);

		CommunityVO community = communityService.getOne(id);

		boolean isMyCommunity = member.getId() == community.getUserId();

		if (isMyCommunity && communityService.deleteView(id)) {
			return "redirect:/";
		}

		if (communityService.deleteView(id)) {
			return "redirect:/";
		}
		return "redirect:/view/" + id;
	}

	@RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
	public ModelAndView viewModifyPage(@PathVariable int id, HttpSession session) {

		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		CommunityVO community = communityService.getOne(id);

		// null의 경우는 발생하지 않는다.
		// if ( member == null) {
		//
		// }

		int userId = member.getId();

		if (userId != community.getUserId()) {
			return new ModelAndView("error/404");
		}

		ModelAndView view = new ModelAndView();

		view.setViewName("community/write");
		view.addObject("communityVO", community);
		view.addObject("mode", "modify");

		return view;
	}

	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	public String doModifyAction(@PathVariable int id, HttpSession session, HttpServletRequest request,
			@ModelAttribute("writeForm") @Valid CommunityVO communityVO, Errors errors) {

		// 1. 이 글이 내것이 맞는지?
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		CommunityVO originalVO = communityService.getOne(id);

		if (member.getId() != originalVO.getUserId()) {
			return "error/404";
		}

		if (errors.hasErrors()) {
			return "redirect:/modify/" + id;
		}
		// 여기까지 1번.

		// 수정하기 5단계 해보자.
		CommunityVO newCommunity = new CommunityVO();

		newCommunity.setId(originalVO.getId()); // 게시글의 번호.
		newCommunity.setUserId(member.getId()); // 게시글을 사용한 사람 번호.

		boolean isModify = false;
		// 1. ip 변경하기.
		String ip = request.getRemoteAddr();
		if ( !ip.equals( originalVO.getRequestIp() )) {
			newCommunity.setRequestIp( communityVO.getRequestIp());
			isModify = true;
		}
		
		//2. 제목 변경 확인.
		if ( !originalVO.getTitle().equals( communityVO.getTitle() ) ) {
			newCommunity.setTitle( communityVO.getTitle() );
			isModify = true;
		}
		
		//3. 내용 변경 확인.
		if ( !originalVO.getBody().equals( communityVO.getBody() ) ) {
			newCommunity.setBody( communityVO.getBody() );
			isModify = true;
		}
		
		//4. 파일 변경 확인.
		if ( communityVO.getDisplayFilename().length() > 0 ) {
			File file = new File("D:\\uploadFiles/" + communityVO.getDisplayFilename());
			file.delete();
			communityVO.setDisplayFilename("");
		}
		else {
			communityVO.setDisplayFilename( originalVO.getDisplayFilename() );
		}
		
		communityVO.save();
		if ( !originalVO.getDisplayFilename().equals( communityVO.getDisplayFilename() ) ) {
			newCommunity.setDisplayFilename( communityVO.getDisplayFilename() );
			isModify = true;
		}
		
		//5. 파일 변경이 없다면 확인.
		if ( isModify ) {
				communityService.updateCommunity(communityVO);
		}
		
		
		return "redirect:/view/" + id;
	}

	// @RequestMapping("/read/{id}")
	// public ModelAndView read(@PathVariable int id, HttpSession session) {
	//
	// if( session.getAttribute(Member.USER) == null) {
	// return new ModelAndView("redirect:/login");
	// }
	//
	// ModelAndView view = new ModelAndView();
	// view.setViewName("community/view");
	// CommunityVO community = communityService.refresh(id);
	//
	// view.addObject("community",community);
	//
	//
	//
	// return view;

	// }

}
