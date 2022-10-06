package jinn.spring.mvc.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import jinn.spring.mvc.vo.BoardVO;
import jinn.spring.mvc.vo.MemberVO;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO{

	// @Autowired log4j.xml bean 태그에 정의한 경우 생략가능
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleInsert;
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	//private RowMapper<MemberVO> memberMapper = BeanPropertyRowMapper.newInstance(MemberVO.class);
	
	public MemberDAOImpl(DataSource dataSource) {
		simpleInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("member")
			.usingColumns("userid","passwd","name","email");
		
		jdbcNameTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public int insertMember(MemberVO mvo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(mvo);
		
		return simpleInsert.execute(params);
	}

	@Override
	public MemberVO selectOneMember(String uid) {
		String sql = "select userid, name, email, regdate from member where userid = ?";
		
		Object[] params = {uid};

		RowMapper<MemberVO> memberMapper = (rs, num) -> {
			MemberVO m = new MemberVO();
			
			m.setUserid(rs.getString("userid"));
			m.setName(rs.getString("name"));
			m.setEmail(rs.getString("email"));
			m.setRegdate(rs.getString("regdate"));
			
			return m;
		};
		
		return jdbcTemplate.queryForObject(sql, params, memberMapper);
	}

	@Override
	public int selectOneMember(MemberVO m) {
		//count 쓰는 이유 한 줄씩 mno로 중복여부를 확인하기 위해서  *별표를 쓰면 모든 행의 모든 값을 확인하기 때문에 비효율적이다.
		String sql = "select count(mno) from member where userid = ? and passwd = ?";
		
		Object[] params = {m.getUserid(), m.getPasswd()};
		
		return jdbcTemplate.queryForObject(sql,params,Integer.class);
	}

	@Override
	public int selectCountUserid(String uid) {
		String sql = "select count(mno) cnt from member where userid = ?";
		
		Object[] param = new Object[] {uid};
		
		return jdbcTemplate.queryForObject(sql, param, Integer.class);
	}
	
	
	// 콜백 메서드 정의 : mapRow
//	private class MemberRowMapper implements RowMapper<MemberVO>{
//
//		@Override
//		public MemberVO mapRow(ResultSet rs, int num) throws SQLException {
//			MemberVO m = new MemberVO();
//			
//			m.setUserid(rs.getString("userid"));
//			m.setName(rs.getString("name"));
//			m.setEmail(rs.getString("email"));
//			m.setRegdate(rs.getString("regdate"));
//			
//			return m;
//		}
//		
//	}
//	@Override
//	public int insertMember(MemberVO mvo) {
//		
//		String sql = "insert into member" + "(userid,passwd,name,email)" + " value(?,?,?,?)";
//		
//		Object[] params = new Object[] {
//				mvo.getUserid(), mvo.getPasswd(), mvo.getName(), mvo.getEmail()};
//		return jdbcTemplate.update(sql, params);
//	}

}
