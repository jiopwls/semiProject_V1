package jinn.spring.mvc.service;

import jinn.spring.mvc.vo.MemberVO;

public interface MemberService {

	boolean newMember(MemberVO mvo);

	MemberVO readOneMember();

}
