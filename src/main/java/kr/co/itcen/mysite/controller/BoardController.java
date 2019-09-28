package kr.co.itcen.mysite.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;

@RequestMapping("board/")
@Controller
public class BoardController {
	//DI를 위한 Annotation

	@Autowired
	private BoardService boardService;

	@RequestMapping("writeform")
	public String writeform() {
		return "";
	}
	@RequestMapping("insert")
	public String insert() {
		return "";
	}
	@RequestMapping("contentsform")
	public String contentsform() {
		return "";
	}
	@RequestMapping("modifyform")
	public String modifyform() {
		return "";
	}
	@RequestMapping("update")
	public String update(@RequestParam String no, @RequestParam String title, @RequestParam String content, Model model) {
		Boolean isSuccess = new BoardDao().update(Long.parseLong(no),title,content);

		if(!isSuccess) {
			System.out.println("fail");
		}

		BoardVo vo = new BoardDao().get(Long.parseLong(no));
		model.addAttribute(vo);

		return "/WEB-INF/views/board/contentsform.jsp";
	}
	@RequestMapping("delete")
	public String delete() {
		return "";
	}
	@RequestMapping("list")
	public String list(@RequestParam(value="keyword", defaultValue="") String keyword, 
			@RequestParam(value="currentPage", defaultValue="1") Integer currentPage, 
			@RequestParam(value="currentPageBlock", defaultValue="1") Integer currentPageBlock,
			Model model) {
		
		Map map = boardService.getListMap(keyword, currentPage, currentPageBlock);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("currentPageBlock", map.get("currentPageBlock"));
		model.addAttribute("firstPage", map.get("firstPage"));
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("pageRows", map.get("pageRows"));
		model.addAttribute("keyword", keyword);
		return "board/list";

	}
}
