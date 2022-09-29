package jinn.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

	@GetMapping("/list")
	public String join() {
		return "board/list";
	}
	
	@GetMapping("/view")
	public String login() {
		return "board/view";
	}
	
	@GetMapping("/write")
	public String myinfo() {
		return "board/write";
	}
	
}
