package com.ktds.community.service;

import java.util.ArrayList;
import java.util.List;

import com.ktds.community.dao.CommunityDao;
import com.ktds.community.vo.CommunityVO;

public class CommunityServiceImpl implements CommunityService {

	private CommunityDao communityDao;
	
	
	
	public void setCommunityDao(CommunityDao communityDao) {
		this.communityDao = communityDao;
	}//new 안써줌 bean에서 가지고옴

//의존 - 멤버변수 추가 생성 추가

	@Override
	public List<CommunityVO> getAll() {
		return communityDao.selectAll();
	}

	@Override
	public boolean createCommunity(CommunityVO communityVO) {
		
		String body = communityVO.getBody();
		//\n --> <br/>
		body = body.replace("\n", "<br/>");
		communityVO.setBody(body);
		
//		
//		if( filter(body) || filter(communityVO.getTitle()) ) {
//			
//			return false;
//		}
//		
		
		
		int insertCount = communityDao.insertCommunity(communityVO);
		
		
		return insertCount > 0;
	}

	@Override
	public CommunityVO getOne(int id) {
		if( communityDao.increamentViewCount(id) > 0 ) {
			return communityDao.selectOne(id); 
			
		}
		return null;
	}

	@Override
	public CommunityVO recommend(int id) {
		if( communityDao.increamentRecommendCount(id) > 0 ) {
			return  communityDao.selectOne(id);
		}
		return null;
	}

	@Override
	public CommunityVO refresh(int id) {
		return communityDao.selectOne(id);
	}

	public boolean filter(String str) {
		
		List<String> blackList = new ArrayList<String>();
		blackList.add("욕");
		blackList.add("씨");
		blackList.add("발");
		blackList.add("씨");
		blackList.add("1식");
		blackList.add("종간나세끼");
		blackList.add("2식");
		
		//str  ==>  남편은 2식이에요. 
		
		String[] spliteString = str.split(" "); //스페이스 한칸을 기준으로 짜른다.
		
		for( String word : spliteString ) {
		
			//return blackList.contains(word); //wor에 있는 글자가 blackList에 있냐
			
			for( String blackString : blackList ) {
			
				if( word.contains(blackString)) {
					return true;
				}
				
			} 
			
		}
		
		return true;
	}

	@Override
	public int incrementViewCount(int id) {
		
		if ( communityDao.increamentViewCount(id) > 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public boolean deleteView(int id) {
		return  communityDao.deleteCommunity(id) > 0;
	}

	@Override
	public boolean updateCommunity(CommunityVO communityVO) {
		return communityDao.updateCommunity(communityVO) > 0;
	}

	@Override
	public int readMyCommunitiesCount(int userId) {
		return communityDao.selectMyCommunitiesCount(userId);
	}

	@Override
	public List<CommunityVO> readMyCommunities(int userId) {
		return communityDao.selectMyCommunities(userId);
	}

	@Override
	public boolean deleteCommunities(List<Integer> ids, int userId) {
		return communityDao.deleteCommunities(ids, userId) > 0;
	}

}
