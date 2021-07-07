package www.dream.com.hashTag.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.framework.util.StringUtil;
import www.dream.com.hashTag.model.HashtagVO;
import www.dream.com.hashTag.model.IHashTagOpponent;
import www.dream.com.hashTag.persistence.HashTagMapper;
import www.dream.com.party.model.Party;

@Service
public class HashTagService {

	@Autowired
	private HashTagMapper hashTagMapper;
	/**
	 * @param hashTagOpponent : 상대
	 * @param mapOccur : 단어와 출현 빈도
	 */
	public void createHashTagAndMapping(IHashTagOpponent hashTagOpponent, 
			Map<String, Integer> mapOccur) {
		Set<String> setHashTag = mapOccur.keySet();

		if (setHashTag.isEmpty()) {
			// 게시글에서 단어가 나타나지 않으면 처리할 것이 없다
			return;
		}
		// 기존 사전에 등록되어있는 hashTag(단어)를찾음. setExisting에 출현빈도를 기록시킴
		Set<HashtagVO> setExisting = hashTagMapper.findExisting(setHashTag);
		// 기존에 있는 것들과 짝 지어 주어야한다.

		for (HashtagVO hashtag : setExisting) {
			// setExisting집합에서 단어의 개수를 헤아림Qo
			hashtag.setOccurCnt(mapOccur.get(hashtag.getHashtag()));
		}

		// setHashTag에 남은 것들은 신규 처리해야할 것들.
		for (HashtagVO hashtag : setExisting) {
			setHashTag.remove(hashtag.getHashtag());
		}

		Set<String> setNewHashTag = setHashTag;
		if (!setNewHashTag.isEmpty()) {
			// 새로운 단어를 HashTag 테이블에 등록해주기.
			// 한번에 여러개의 단어를 만들기. ids : where절에 들어올 단어들의 집합
			int[] ids = StringUtil.convertCommaSepString2IntArr(hashTagMapper.getIds(setNewHashTag.size()));
			// Set의 index번호
			int idx = 0;
			// 새롭게 생성한 단어셋 : setHT
			Set<HashtagVO> setHT = new HashSet<>();
			// 새로운 단어집합에서 id를 꺼내서 id의 몇번째꺼와 연결해주고 목록에 한번에 집어넣는다
			for (String hashTag : setNewHashTag) {
				// 배열이니까 단어의 idx번호를 증가시키면서 넣고, 단어를 넣는다
				HashtagVO newHashtag = new HashtagVO(ids[idx++], hashTag);
				// mapOccur에 데이터 등록
				newHashtag.setOccurCnt(mapOccur.get(hashTag));
				setHT.add(newHashtag);
			}
			hashTagMapper.createHashTag(setHT);
			// 새 단어를 단어집에 넣었으니 기존 단어가 된것
			setExisting.addAll(setHT);
		}

		// 한번에 insert를 해주는 고성능의 기능
		// 기존 단어 및 신규 단어와 짝짓기
		hashTagMapper.insertMapBetweenStringId(setExisting, hashTagOpponent);
	}
	
	public void deleteMap(IHashTagOpponent hashTagOpponent) {
		hashTagMapper.deleteMapBetweenStringId(hashTagOpponent);
	}

	/**
	 * 기존에 검색한 단어는 활용 횟수 올려주기 신규 단어는 단어 새롭게 만들고 횟수는1
	 * 
	 * @param curUser
	 * @param mapSearchWords
	 */
	public void mngHashTagAndMapping(Party curUser, Map<String, Integer> mapSearchWords) {
		Set<String> setHashTag = mapSearchWords.keySet();

		if (setHashTag.isEmpty()) {
			// 게시글에서 단어가 나타나지 않으면 처리할 것이 없다
			return;
		}
		// setPrevUsed : 테이블에 아예없는 신규 단어
		Set<HashtagVO> setPrevUsed = hashTagMapper.findPrevUsedHashTag(curUser, setHashTag);
		Set<HashtagVO> setExisting = hashTagMapper.findExisting(setHashTag);
		// 기존에 있는 것들과 짝 지어 주어야한다.

		for (HashtagVO hashtag : setExisting) {
			// setExisting집합에서 단어의 개수를 헤아림Qo
			hashtag.setOccurCnt(mapSearchWords.get(hashtag.getHashtag()));
		}

		// setHashTag에 남은 것들은 신규 처리해야할 것들.
		for (HashtagVO hashtag : setExisting) {
			setHashTag.remove(hashtag.getHashtag());
		}

		Set<String> setNewHashTag = setHashTag;
		if (!setNewHashTag.isEmpty()) {
			// 새로운 단어를 HashTag 테이블에 등록해주기.
			// 한번에 여러개의 단어를 만들기. ids : where절에 들어올 단어들의 집합
			int[] ids = StringUtil.convertCommaSepString2IntArr(hashTagMapper.getIds(setNewHashTag.size()));
			// Set의 index번호
			int idx = 0;
			// 새롭게 생성한 단어셋 : setHT
			Set<HashtagVO> setHT = new HashSet<>();
			// 새로운 단어집합에서 id를 꺼내서 id의 몇번째꺼와 연결해주고 목록에 한번에 집어넣는다
			for (String hashTag : setNewHashTag) {
				// 배열이니까 단어의 idx번호를 증가시키면서 넣고, 단어를 넣는다
				HashtagVO newHashtag = new HashtagVO(ids[idx++], hashTag);
				// mapOccur에 데이터 등록
				newHashtag.setOccurCnt(mapSearchWords.get(hashTag));
				setHT.add(newHashtag);
			}
			hashTagMapper.createHashTag(setHT);
			// 새 단어를 단어집에 넣었으니 기존 단어가 된것
			setExisting.addAll(setHT);
		}

		// 기존 활용 횟수 정보 없애기
		hashTagMapper.deleteMapBetweenOpponentStringId(setExisting, curUser);
		// 기존 단어 및 신규 단어와 짝짓기
		hashTagMapper.insertMapBetweenStringId(setExisting, curUser);

	}
}
