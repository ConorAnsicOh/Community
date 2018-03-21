package com.ktds.community.service;

import java.util.List;

import com.ktds.community.dao.CommunityDao;
import com.ktds.community.vo.CommunityVO;

public interface CommunityService {
	
	public List<CommunityVO> getAll();
	public CommunityVO getOne(int id);
	public CommunityVO recommend(int id);
	public CommunityVO refresh(int id);
	
	public int readMyCommunitiesCount(int userId);
	public List<CommunityVO> readMyCommunities(int userId);
	
	public boolean createCommunity(CommunityVO communityVO);
	public int incrementViewCount(int id);
	public boolean deleteView(int id);
	
	public boolean deleteCommunities(List<Integer> ids, int userId);
	
	public boolean updateCommunity(CommunityVO communityVO);

}
