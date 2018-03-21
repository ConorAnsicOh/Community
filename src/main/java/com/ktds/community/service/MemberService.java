package com.ktds.community.service;

import javax.validation.Valid;

import com.ktds.community.member.vo.MemberVO;

public interface MemberService {

	public boolean createMember(MemberVO memberVO);

	public MemberVO readMember(MemberVO memberVO);

	public boolean deleteMember(int id, String deleteFlag);
	
	public boolean removeMember(int id);
	
}
