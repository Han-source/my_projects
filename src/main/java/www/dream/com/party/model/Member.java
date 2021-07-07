package www.dream.com.party.model;

import java.util.ArrayList;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 유저입장
 *
 */
@Data
@NoArgsConstructor
public class Member extends Party {
	private static List<AuthorityVO> listAuthority = new ArrayList<AuthorityVO>();
	static {
		listAuthority.add(new AuthorityVO("user"));
	}
	@Override
	public List<AuthorityVO> getAuthorityList() {
		return listAuthority;
	}
	public Member(String userId) {
		super(userId);
	}
	
	
}
