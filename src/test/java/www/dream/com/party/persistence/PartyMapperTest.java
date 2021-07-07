package www.dream.com.party.persistence;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.party.model.ContactPoint;
import www.dream.com.party.model.Member;
import www.dream.com.party.model.Party;

@RunWith(SpringJUnit4ClassRunner.class)
//해당 위치에 있는 데이터를 바탕으로 데이터를 만들어라
@ContextConfiguration({"file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml",
	"file:src\\main\\webapp\\WEB-INF\\spring\\security-context.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PartyMapperTest {
	@Autowired
	private PasswordEncoder pwEncoder;
	@Autowired
	private PartyMapper partyMapper;

	//@Test
	public void test100GetList() {
		assertNotNull(partyMapper);
		try {
			partyMapper.getList().forEach(p -> {
				System.out.println(p);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void test200findPartyByUserId() {
		try {
			Party found = partyMapper.findPartyByUserId("admin");
			found.getAuthorityList().forEach(auth -> {
				System.out.println(auth);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test200partyRegister() {
		try {
			Member m = new Member();
			List<ContactPoint> cp = new ArrayList<>(); 
			m.setUserId("aa");
			m.setUserPwd("aa");
			m.setName("a");
			m.setBirthDate(new Date());
			m.setMale(false);
			cp.add(new ContactPoint("mobileNum", "010600"));
			cp.add(new ContactPoint("addr", "1234"));

			partyMapper.partyRegister(m, cp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//@Test
	public void test440SetPwd() {
		try {
			partyMapper.getList().forEach(p -> {
				p.setUserPwd(pwEncoder.encode("1234"));
				partyMapper.setPwd(p);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}