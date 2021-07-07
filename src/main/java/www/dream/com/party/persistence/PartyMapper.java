package www.dream.com.party.persistence;

import java.util.List;

import www.dream.com.party.model.Party;
import www.dream.com.party.model.Party1;

/**
 * Mybatis를 활용하여 Party 종류의 객체를 관리하는 인터페이스
 *
 */
public interface PartyMapper {
	//함수 정의 순서	ListReadCreateUpdateDelete : LRCUD
	//목록 조회		
	
	public List<Party> getList();	//인자없으면 몽땅조회
	//개별 객체 조회
	public Party findPartyByUserId(String userId); 
	//Insert
	public void joinMembership(Party party); //회원가입
	//Update
	//Delete
	public void setPwd(Party p);
}


