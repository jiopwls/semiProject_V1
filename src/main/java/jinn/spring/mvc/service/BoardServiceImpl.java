package jinn.spring.mvc.service;

import java.util.List;

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

	@Override
	public List<BoardVO> readBoard(String fkey, String fval, int snum) {
		
		return bdao.selectBoard(fkey, fval, snum);
	}

	@Override
	public BoardVO readOneBoard(String b_no) {
		
		return bdao.selectOneBoard(b_no);
	}

	@Override
	public int readCountBoard(String fkey, String fval) {
		
		return bdao.selectCountBoard(fkey, fval);
	}

	@Override
	public int readCountBoard() {
		
		return 0;
	}

	@Override
	public boolean removeBoard(String b_no) {
		boolean isDelete = false;
		
		if (bdao.deleteBoard(b_no) > 0) isDelete = true;
		
		return isDelete;
	}

	@Override
	public boolean modifyBoard(BoardVO bvo) {
		boolean isUpdate = false;
		
		if (bdao.updateBoard(bvo) > 0) isUpdate = true;
		
		return isUpdate;
		
	}

}
