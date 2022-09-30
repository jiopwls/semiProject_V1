package jinn.spring.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jinn.spring.mvc.dao.BoardDAO;
import jinn.spring.mvc.vo.BoardVO;

@Service("bsrv")
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDAO bdao;
	
	@Override
	public boolean newBoard(BoardVO bvo) {
		
		boolean isInsert = false;
		
		//게시글 등록 성공시
		if(bdao.insertBoard(bvo) > 0) isInsert = true;
		
		return isInsert;
	}

}
