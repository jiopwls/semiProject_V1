package jinn.spring.mvc.dao;

import jinn.spring.mvc.vo.MemberVO;

public interface MemberDAO {

	int insertMember(MemberVO mvo);

	MemberVO selectOneMember();

}
