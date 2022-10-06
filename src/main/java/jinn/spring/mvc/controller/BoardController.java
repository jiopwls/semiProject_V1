package jinn.spring.mvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jinn.spring.mvc.service.BoardService;
import jinn.spring.mvc.utils.RecaptchaUtils;
import jinn.spring.mvc.vo.BoardVO;

@Controller
public class BoardController {
	
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	// DI 받을 변수가 둘 이상이므로 생성자로 DI받도록 재정의
	//@Autowired private BoardService bsrv;
	//@Autowired private RecaptchaUtils grcp;
	private BoardService bsrv;
	private RecaptchaUtils grcp;
	
	@Autowired 
	public BoardController(BoardService bsrv, RecaptchaUtils grcp) {
		this.bsrv = bsrv;
		this.grcp = grcp;
	}
	/* 
	 페이징 처리
	 페이지 당 게시물 수 perPage: 25개
	 총 페이지 수 : 전체 게시물 수 / 페이지 당 게시물 수
	 총 페이지 수 pages : ceil(getTotalPage / perPage)
	 예) 2 = 50/25, 3 = 51/25  
	
	
	 페이지 별 읽어올 게시글 범위
	 총 게시글이 55건이라 할때
	 1page : 1 ~ 25
	 2page : 26 ~ 50
	 3page : 51 ~ 55
	 ipage : m번째 ~ n번째까지
	 m = ( i - 1 ) * 25 + 1  
	
	 
	 현재 페이지에 따라 보여줄 페이지 블럭 결정
	 ex) 총 페이지 수가 27일 때
	 cpg = 1 -> 1 2 3 4 5 6 7 8 9 10
	 cpg = 4 -> 1 2 3 4 5 6 7 8 9 10
	 cpg = 10 -> 1 2 3 4 5 6 7 8 9 10
	 cpg = 11 -> 11 12 13 14 15 16 17 18 19 20
	 cpg = 26 -> 21 22 23 24 25 26 27
	 
	 cpg = n : ?+0 ?+1 ?+2 .... ?+9
	 stpgn = ((cpg - 1) / 10) * 10 + 1
	 
	 */
	
	@GetMapping("/list")
	public String list(Model m, String cpg, String fkey, String fval) {
		
		int perPage = 20;
		if (cpg == null || cpg.equals("")) cpg = "1";
		if (fkey == null) fkey = "";
		
		int cpage = Integer.parseInt(cpg);
		int snum = ( cpage- 1) * perPage;
		int stpgn = ((cpage - 1) / 10) * 10 + 1;
		
		m.addAttribute("pages", bsrv.readCountBoard(fkey, fval));
		m.addAttribute("bdlist", bsrv.readBoard(fkey, fval, snum));
		m.addAttribute("stpgn", stpgn);
		
		//m.addAttribute("fqry", "%fkey=" + fkey + "%fval=" + fval);
		//m.addAttribute("cpg", cpage);
		
		return "board/list";
	}
	
	@GetMapping("/view")
	public ModelAndView view(ModelAndView mv, String b_no) {
		
		mv.setViewName("board/view");
		mv.addObject("bd", bsrv.readOneBoard(b_no));
		
		
		return mv;
	}
	
	// 로그인 안했다면 -> redirect:/login
	// 로그인 했다면 -> board/write
	@GetMapping("/write")
	public String write(HttpSession sess) {
		String returnPage = "board/write";
		
		if(sess.getAttribute("m") == null) {
			returnPage = "redirect:/login";
		}

		return returnPage;
	}
	
	
	 // captcha 작동원리
    // captcha 사용시 클라이언트가 생성한 키와
    // 서버에 설정해 둔 (비밀)키 등을
    // google의 siteverify에서 비교해서
    // 인증에 성공하면 list로 redirect하고,
    // 그렇지 않으면 다시 write로 return함
	// 질의를 위한 질의문자열은 다음과 같이 작성
	// ?secret=비밀키&response=클라이언트응답키
	@PostMapping("/write")
	public String writeok(BoardVO bvo, String gcaptcha, RedirectAttributes rda) throws ParseException, IOException {
		
		String returnPage = "redirect:/write";
		//LOGGER.info(gcaptcha)
		
		if (grcp.checkCaptcha(gcaptcha)) {
			bsrv.newBoard(bvo);
			returnPage = "redirect:/list";
		} else {
			rda.addFlashAttribute("bvo", bvo);
			rda.addFlashAttribute("msg", "리캡챠 확인이 실패했어요.");
		}

		return returnPage;
	}
	
	@GetMapping("/del")
	public String del(HttpSession sess, String b_no) {
		String returnPage = "redirect:/list";
		
		if(sess.getAttribute("m") == null)
			returnPage = "redirect:/login";
		else
			bsrv.removeBoard(b_no);
		
		return returnPage;
	}
	
	@GetMapping("/upd")
	public String modify(HttpSession sess, String b_no, Model m) {
		String returnPage = "board/update";
		
		if(sess.getAttribute("m") == null)
			returnPage = "redirect:/login";
		else
			m.addAttribute("bd",bsrv.readOneBoard(b_no));
		
		return returnPage;
	}
	
	@PostMapping("/upd")
	public String modify_ok(HttpSession sess, BoardVO bvo ) {
		String returnPage = "redirect:/view?b_no=" + bvo.getB_no();
		
		if(sess.getAttribute("m") == null)
			returnPage = "redirect:/login";
		else
			bsrv.modifyBoard(bvo);
		
		return returnPage;
	}
}
