package com.office.library.admin.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {
	
	@Autowired
	AdminMemberService adminMemberService;
	
	/*
	 * 회원 가입
	 */
//	@RequestMapping(value = "/createAccountForm", method = RequestMethod.GET)
	@GetMapping("/createAccountForm")
	public String createAccountForm() {
		System.out.println("[AdminMemberController] createAccountForm()");
		
		String nextPage = "admin/member/create_account_form";
		
		return nextPage;
		
	}
	
	/*
	 * 회원 가입 확인
	 */
//	@RequestMapping(value = "/createAccountConfirm", method = RequestMethod.POST)
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberController] createAccountConfirm()");
		
		String nextPage = "admin/member/create_account_ok";
		
		int result = adminMemberService.createAccountConfirm(adminMemberVo);
		
		if (result <= 0)
			nextPage = "admin/member/create_account_ng";
		
		return nextPage;
		
	}
	
	/*
	 * 로그인
	 */
//	@RequestMapping(value = "/loginForm", method = RequestMethod.GET)
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("[AdminMemberController] loginForm()");
		
		String nextPage = "admin/member/login_form";
		
		return nextPage;
		
	}
	
	/*
	 * 로그인 확인
	 */
//	@RequestMapping(value = "/loginConfirm", method = RequestMethod.POST)
	@PostMapping("/loginConfirm")
	public String loginConfirm(AdminMemberVo adminMemberVo, HttpSession session) {
		System.out.println("[AdminMemberController] loginConfirm()");
		
		String nextPage = "admin/member/login_ok";
		
		AdminMemberVo loginedAdminMemberVo = adminMemberService.loginConfirm(adminMemberVo);
		
		if (loginedAdminMemberVo == null) {
			nextPage = "admin/member/login_ng";
			
		} else {
			session.setAttribute("loginedAdminMemberVo", loginedAdminMemberVo);
			session.setMaxInactiveInterval(60 * 30);
			
		}
		
		return nextPage;
		
	}
	
	/*
	 * 로그아웃 확인
	 */
//	@RequestMapping(value = "/logoutConfirm", method = RequestMethod.GET)
	@GetMapping("/logoutConfirm")
	public String logoutConfirm(HttpSession session) {
		System.out.println("[AdminMemberController] logoutConfirm()");
		
		String nextPage = "redirect:/admin";
		
//		session.removeAttribute("loginedAdminMemberVo");
		session.invalidate();
		
		return nextPage;
		
	}
	
	/*
	 * 관리자 목록(Model 사용)
	 */
//	@RequestMapping(value = "/listupAdmin", method = RequestMethod.GET)
//	public String listupAdmin(Model model) {
//		System.out.println("[AdminMemberController] modifyAccountConfirm()");
//	
//		String nextPage = "admin/member/listup_admins";
//		
//		List<AdminMemberVo> adminMemberVos = adminMemberService.listupAdmin();
//		
//		model.addAttribute("adminMemberVos", adminMemberVos);
//		
//		return nextPage;
//		
//	}
	
	/*
	 * 관리자 목록(ModelAndView 사용)
	 */
	@RequestMapping(value = "/listupAdmin", method = RequestMethod.GET)
	public ModelAndView listupAdmin() {
		System.out.println("[AdminMemberController] modifyAccountConfirm()");
	
		String nextPage = "admin/member/listup_admins";
		
		List<AdminMemberVo> adminMemberVos = adminMemberService.listupAdmin();
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(nextPage);					
		modelAndView.addObject("adminMemberVos", adminMemberVos);	
		
		return modelAndView;								
		
	}
	
	/*
	 * 관리자 승인
	 */
	@RequestMapping(value = "/setAdminApproval", method = RequestMethod.GET)
	public String setAdminApproval(@RequestParam("a_m_no") int a_m_no) {
		System.out.println("[AdminMemberController] setAdminApproval()");
		
		String nextPage = "redirect:/admin/member/listupAdmin";
		
		adminMemberService.setAdminApproval(a_m_no);
		
		return nextPage;
		
	}
	
	/*
	 * 회원정보 수정
	 */
	@GetMapping("/modifyAccountForm")
	public String modifyAccountForm(HttpSession session) {
		System.out.println("[AdminMemberController] modifyAccountForm()");
		
		String nextPage = "admin/member/modify_account_form";
		
		AdminMemberVo loginedAdminMemberVo = (AdminMemberVo) session.getAttribute("loginedAdminMemberVo");
//		if (loginedAdminMemberVo == null)
//			nextPage = "redirect:/admin/member/loginForm";
		
		return nextPage;
		
	}
	
	/*
	 * 회원정보 수정 확인
	 */
//	@RequestMapping(value = "/modifyAccountConfirm", method = RequestMethod.POST)
	@PostMapping("/modifyAccountConfirm")
	public String modifyAccountConfirm(AdminMemberVo adminMemberVo, HttpSession session) {
		System.out.println("[AdminMemberController] modifyAccountConfirm()");
		
		String nextPage = "admin/member/modify_account_ok";
		
		int result = adminMemberService.modifyAccountConfirm(adminMemberVo);
		
		if (result > 0) {
			AdminMemberVo loginedAdminMemberVo = adminMemberService.getLoginedAdminMemberVo(adminMemberVo.getA_m_no());
			
			session.setAttribute("loginedAdminMemberVo", loginedAdminMemberVo);
			session.setMaxInactiveInterval(60 * 30);
			
		} else {
			nextPage = "admin/member/modify_account_ng";
			
		}
		
		return nextPage;
		
	}
	
	/*
	 * 비밀번호 찾기
	 */
//	@RequestMapping(value = "/findPasswordForm", method = RequestMethod.GET)
	@GetMapping("/findPasswordForm")
	public String findePasswordForm() {
		System.out.println("[AdminMemberController] findPasswordForm()");
		
		String nextPage = "admin/member/find_password_form";
		
		return nextPage;
		
	}
	
	/*
	 * 비밀번호 찾기 확인
	 */
//	@RequestMapping(value = "/findPasswordConfirm", method = RequestMethod.POST)
	@PostMapping("/findPasswordConfirm")
	public String findPasswordConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberController] findPasswordConfirm()");
		
		String nextPage = "admin/member/find_password_ok";
		
		int result = adminMemberService.findPasswordConfirm(adminMemberVo);
		
		if (result <= 0)
			nextPage = "admin/member/find_password_ng";
		
		return nextPage;
		
	}
	
}