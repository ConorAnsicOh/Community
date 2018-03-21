package com.ktds.community.member.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.community.member.vo.MemberVO;

public class MemberDaoImplForOracle extends SqlSessionDaoSupport implements MemberDao{

	@Override
	public int insertMember(MemberVO memberVO) {
		return getSqlSession().insert("MemberDao.insertMember", memberVO);
	}

	@Override
	public MemberVO selectMember(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("MemberDao.selectMember", memberVO );
	}

	@Override
	public int delete(int id) {
		return getSqlSession().delete("MemberDao.deleteMember",id);
	}

	@Override
	public boolean deleteMember(int userId) {
		return getSqlSession().selectOne("MemberDao.deleteMember", userId);
	}
	
	
}
