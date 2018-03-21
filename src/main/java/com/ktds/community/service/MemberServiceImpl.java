package com.ktds.community.service;

import javax.validation.Valid;

import com.ktds.community.dao.CommunityDao;
import com.ktds.community.member.dao.MemberDao;
import com.ktds.community.member.vo.MemberVO;
import com.ktds.util.SHA256Util;

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
		
		String salt = SHA256Util.generateSalt();
		memberVO.setSalt(salt);
		
		String password = memberVO.getPassword();
		password = SHA256Util.getEncrypt(password, salt);
		memberVO.setPassword(password);
		
		return memberDao.insertMember(memberVO) > 0 ;
	}

	@Override
	public MemberVO readMember( MemberVO memberVO) {
		
//		1.사용자의 ID로 SALT 가져오기
		String salt = memberDao.selectSalt(memberVO.getEmail());
		if(salt == null) {
			salt = "";
		}
//		2.SALT로 암호화 하기
		String password = memberVO.getPassword();
		password = SHA256Util.getEncrypt(password, salt);
		memberVO.setPassword(password);
		
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

	@Override
	public boolean readCountMemberEmail(String email) {
		return memberDao.selectCountMemberEamil(email) > 0;
	}

	@Override
	public boolean readCountMemberNickname(String nickname) {
		return memberDao.selectCountMemberNickname(nickname) > 0;
	}
	
	

}
