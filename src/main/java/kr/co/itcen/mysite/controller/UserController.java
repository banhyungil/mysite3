package kr.co.itcen.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.exception.UserDaoException;
import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute UserVo vo, HttpSession session, Model model) {
		UserVo userVo = userService.getSessionUser(vo);
		//로그인 실패시
		if(userVo == null) {
			model.addAttribute("result", "fail");
			return "user/login";
		}
		
		//로그인 처리
		session.setAttribute("authUser", userVo);
		return "redirect:/";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		//접근 제어(ACL)
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser != null) {
			session.removeAttribute("authUser");
			session.invalidate();
		}
		
		return "redirect:/";
	}
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute UserVo vo) {
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(HttpSession session) {
		
		if( session == null ) {
			return "redirect:/";
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		
		session.setAttribute("authUser", userService.getUser(authUser));
		return "user/update";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@ModelAttribute UserVo vo, HttpSession session) {
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		vo.setNo(authUser.getNo());
		System.out.println(vo.toString());
		userService.update(vo);
		
		session.setAttribute("authUser", vo);
		return "redirect:/";
	}
	
	//Controller 마다 둬야하므로 번거롭다
//	//AOP 기술이 적용된 Annotaion
//	@ExceptionHandler(UserDaoException.class)
//	public String handleException() {
//		return "error/exception";
//	}
}
