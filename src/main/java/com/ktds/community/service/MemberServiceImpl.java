package com.ktds.community.service;

import javax.validation.Valid;

import com.ktds.community.dao.CommunityDao;
import com.ktds.community.member.dao.MemberDao;
import com.ktds.community.member.vo.MemberVO;

public class MemberServiceImpl implements MemberService {

	private MemberDao memberDao;
	private CommunityDao communityDao;
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public void setCommunityDao(CommunityDao communityDao) {
		this.communityDao = communityDao;
	}
	
	@Override
	public boolean createMember(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return memberDao.insertMember(memberVO) > 0 ;
	}

	@Override
	public MemberVO readMember( MemberVO memberVO) {
		return memberDao.selectMember(memberVO);
	}

	@Override
	public boolean deleteMember(int id, String deleteFlag) {
		
		if (deleteFlag.equals("y")) {
			communityDao.deleteMyCommunities(id);			
		}
		
		return memberDao.delete(id) > 0;
	}

	@Override
	public boolean removeMember(int id) {
		return memberDao.deleteMember(id);
	}
	
	

}
