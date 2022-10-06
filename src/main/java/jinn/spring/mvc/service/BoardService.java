package jinn.spring.mvc.service;

import java.util.List;

import jinn.spring.mvc.vo.BoardVO;

public interface BoardService {

	boolean newBoard(BoardVO bvo);

	List<BoardVO> readBoard(String fkey, String fval, int snum);

	BoardVO readOneBoard(String b_no);

	int readCountBoard(String fkey, String fval);

	int readCountBoard();
	
	boolean removeBoard(String b_no);

	boolean modifyBoard(BoardVO bvo);
}
