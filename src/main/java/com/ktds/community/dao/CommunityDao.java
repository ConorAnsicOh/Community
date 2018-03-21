package com.ktds.community.dao;

import java.util.List;

import com.ktds.community.vo.CommunityVO;

public interface CommunityDao {

	public List<CommunityVO> selectAll();

	public CommunityVO selectOne(int id);

	public int selectMyCommunitiesCount(int userId);
	
	public List<CommunityVO> selectMyCommunities(int userId);
	
	public int increamentViewCount(int id);
	
	public int insertCommunity(CommunityVO communityVO);
	
	public int increamentRecommendCount(int id);

	public int deleteCommunity(int id);
	
	//내가 작성한거 한번에 지우기, 포스트 id위치와 유저 id를 보내서 내것만 지우기.
	public int deleteCommunities(List<Integer> ids, int userId);
	
	public int deleteMyCommunities(int userId);
	
	public int updateCommunity(CommunityVO communityVO);

	
}
