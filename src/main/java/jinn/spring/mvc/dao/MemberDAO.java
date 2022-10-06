package jinn.spring.mvc.dao;

import jinn.spring.mvc.vo.MemberVO;

public interface MemberDAO {

	int insertMember(MemberVO mvo);

	MemberVO selectOneMember(String uid);

	int selectOneMember(MemberVO m);

	int selectCountUserid(String uid);

}
