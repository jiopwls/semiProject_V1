package jinn.spring.mvc.service;

import java.util.List;

import jinn.spring.mvc.vo.BoardVO;

public interface BoardService {

	boolean newBoard(BoardVO bvo);

	List<BoardVO> readBoard();
}
