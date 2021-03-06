package www.dream.com.party.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import www.dream.com.framework.springSecurityAdapter.CustomUser;
import www.dream.com.party.model.ContactPoint;
import www.dream.com.party.model.Party;
import www.dream.com.party.persistence.PartyMapper;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PartyService implements UserDetailsService{
	@Autowired
	private PartyMapper partyMapper;
	public List<Party> getList(){
		return partyMapper.getList();
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Party loginParty = partyMapper.findPartyByUserId(username);
		
		return loginParty == null? null : new CustomUser(loginParty);
	}
	
	
	public void partyRegister(Party party, List<ContactPoint> contactPoint) {
		partyMapper.partyRegister(party, contactPoint);
	}
	
//	public void membershipForUser(Party party) {
//
//		partyMapper.joinMembership(party);
//		
//		
//	}
}
