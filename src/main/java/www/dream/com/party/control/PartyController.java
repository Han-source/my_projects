package www.dream.com.party.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import www.dream.com.party.model.Member;
import www.dream.com.party.service.PartyService;

@Controller
@RequestMapping("/party/*")
public class PartyController implements AuthenticationSuccessHandler, AccessDeniedHandler{
	@Autowired
	private PartyService partyService;
	
	@GetMapping(value = "list")
	public void getList(Model model) {
		model.addAttribute("listParty", partyService.getList());
	}
	
	
	
	@GetMapping(value="membership")
	public void membership() {
		
	}
	
	@PostMapping( value="membership")
	public String membership(Member member) {
		
		partyService.membershipForUser(member);
		return null;
	}
	
	@GetMapping("customLogin")
	public void loginInput(String error, String logout, Model model) {
		if (error != null) {
			model.addAttribute("error", "로그인 실패 계정을 다시 확인해주세요");
		}
		if (logout != null) {
			model.addAttribute("logout", "로그아웃 되었습니다.");
		}
	}
	
	@GetMapping("customLogout")
	public void processLogout() {
		
	}
	
	@PostMapping("customLogout")
	public void processLogoutPost() {
		
	}
	
	@GetMapping("showCurUser")
	public void showCurUser() {
		
	}
	/**
	 * 로그인 성공 시 각 사용자의 권한 유형에 따라 개인화 된 화면을 연동 시켜주기 위한 기능을 이곳에서 개발 합니다.
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		List<String> roleNames = new ArrayList<>();
		authentication.getAuthorities().forEach(authrity -> {
			roleNames.add(authrity.getAuthority());
		});
		
		if (roleNames.contains("manager")) {
			response.sendRedirect("/party/showCurUser");
			return;
		}
		
		if (roleNames.contains("admin")) {
			response.sendRedirect("/post/listBySearch?boardId=1");
			return;
		}
		if (roleNames.contains("manager")) {
			response.sendRedirect("/post/listBySearch?boardId=2");
			return;
		}
		response.sendRedirect("/post/listBySearch?boardId=3");
	}
	
	//access denie가 되면 해당 부분이 돌아감
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.sendRedirect("/party/accessError");
	}
	
}
