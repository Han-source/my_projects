package www.dream.com.hashTag.persistence;

import java.util.Set;

import org.apache.ibatis.annotations.Param;

import www.dream.com.hashTag.model.HashtagVO;
import www.dream.com.hashTag.model.IHashTagOpponent;

public interface HashTagMapper {
	/**
	 * setHashTag로 받은 단어 중 에 기존에 관리하고 있는 단어 집합 찾기
	 * @param setHashTag isEmpty() false일때만 불러주세요
	 * @return
	 */
	public Set<HashtagVO> findExisting(@Param("setHashTag") Set<String> setHashTag);
	/**
	 * 예전에 사용도 되지 않았고, 단어집에 없을때 새롭게 넣어야함.
	 * @param hashTagOpponent
	 * @param curSearch
	 * @return
	 */
	public Set<HashtagVO> findPrevUsedHashTag(@Param("opponent") IHashTagOpponent hashTagOpponent, @Param("curSearch") Set<String> curSearch);

	/**
	 * HashtagVO의 id를 지정한 개수만큼 Sequence를 통하여 한번에 왕창 만들기. 성능
	 * @param cnt
	 * @return
	 */
	public String getIds(int cnt);
	public int createHashTag(@Param("setNewHashtag") Set<HashtagVO> newHashtag);

	/**
	 * Hashtag와 Post 사이의 관계 정보 다중 입력하기(고성능), update도 해야함
	 * @param setExisting : Table에 존재하는 단어의 Id
	 * @param opponentId : 상대방 ID
	 * @param opponentType : 상대방 Type
	 * @return
	 */
	public int insertMapBetweenStringId(@Param("setExisting") Set<HashtagVO> setExisting, @Param("opponent") IHashTagOpponent opponentType);
	public void deleteMapBetweenStringId(@Param("opponent") IHashTagOpponent hashTagOpponent);
	//mybatis는 오버로딩 지원 안함
	public void deleteMapBetweenOpponentStringId(@Param("setExisting") Set<HashtagVO> setExisting, @Param("opponent") IHashTagOpponent hashTagOpponent);

}
