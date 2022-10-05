package jinn.spring.mvc.dao;

import java.util.List;

import jinn.spring.mvc.vo.BoardVO;

public interface BoardDAO {

	int insertBoard(BoardVO bvo);

	List<BoardVO> selectBoard(String fkey, String fval, int snum);

	BoardVO selectOneBoard(String b_no);

	int selectCountBoard(String fkey, String fval);

	int deleteBoard(String b_no);


}
