package jinn.spring.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jinn.spring.mvc.service.BoardService;
import jinn.spring.mvc.vo.BoardVO;

@Controller
public class BoardController {
	
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BoardService bsrv;
	
	@GetMapping("/list")
	public String list(Model m) {
		m.addAttribute("bdlist", bsrv.readBoard());
		
		
		return "board/list";
	}
	
	@GetMapping("/view")
	public String view() {
		return "board/view";
	}
	
	@GetMapping("/write")
	public String write() {
		return "board/write";
	}
	
	@PostMapping("/write")
	public String writeok(BoardVO bvo) {
		LOGGER.info("게시글 화면 호출 {}",bvo);
		
		if(bsrv.newBoard(bvo))
			LOGGER.info("게시글 등록 성공");
		
		return "redirect:/list";
	}
	
}
