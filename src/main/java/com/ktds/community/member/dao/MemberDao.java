package com.ktds.community.member.dao;

import com.ktds.community.member.vo.MemberVO;

public interface MemberDao {
	
	public int insertMember(MemberVO memberVO);
	
	public MemberVO selectMember(MemberVO memberVO);

	public int delete(int id);

	public boolean deleteMember(int userId);
}
