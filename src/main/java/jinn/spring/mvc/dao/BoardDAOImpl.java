package jinn.spring.mvc.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Repository("bdao")
public class BoardDAOImpl implements BoardDAO{

	
	@Autowired //log4j.xml bean 태그에 정의한 경우 생략가능
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleInsert;
	private NamedParameterJdbcTemplate jdbcNamedTemplate;
	
	private RowMapper<BoardVO> boardMapper = BeanPropertyRowMapper.newInstance(BoardVO.class);
	
	
	
	public BoardDAOImpl(DataSource dataSource) {
		simpleInsert = new SimpleJdbcInsert(dataSource)
				.withTableName("board")
				.usingColumns("title","userid","contents");
				
		jdbcNamedTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public int insertBoard(BoardVO bvo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(bvo);
		
		return simpleInsert.execute(params);
	}
	
	@Override
	public List<BoardVO> selectBoard(String fkey, String fval, int snum) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("select b_no, title, userid, regdate, views from board ");
		
		if(fkey.equals("title")) sql.append(" where title like :fval ");
		else if(fkey.equals("userid")) sql.append(" where userid like :fval ");
		else if(fkey.equals("contents")) sql.append(" where contents like :fval ");
		
		sql.append(" order by b_no desc limit :snum , 20");
		
		Map<String, Object> params = new HashMap<>();
		params.put("snum", snum);
		params.put("fval", "%" + fval +"%");
		
		return jdbcNamedTemplate.query(sql.toString(), params, boardMapper);
	}
	
	@Override
	public BoardVO selectOneBoard(String b_no) {
		//본문 글에 대한 조회수 증가
		String sql = "update board set views = views + 1 where b_no = ?";
		Object[] param = {b_no};
		jdbcTemplate.update(sql, param);
		
		//본문 글 가져오기
		sql = "select * from board where b_no = ?";
		return jdbcTemplate.queryForObject(sql, param, boardMapper);
	}
//	@Override
//	public int insertBoard(BoardVO bvo) {
//		String sql = "insert into board" + "(title,userid,contents)" + "value(?,?,?)";
//		
//		Object[] params = new Object[] {
//				bvo.getTitle(), bvo.getUserid(), bvo.getContents()
//		};
//		return jdbcTemplate.update(sql, params);
//	}

	
	// 동적 질의문
	// 조건에 따라 실행할 질의문의 형태가 바뀌는 것
	// 제목으로 검색 : select * from board where title = ?
	// 작성자로 검색 : select * from board where userid = ?
	// 본문으로 검색 : select * from board where contents = ?
	
	// 테이블명, 컬럼명은 매개변수화 할 수 없다. 
	// -> select * from ? where ? = ? (실행 x)
	@Override
	public int selectCountBoard(String fkey, String fval) {
		
		 StringBuilder sql = new StringBuilder();
		
		sql.append("select ceil(count(b_no)/20) pages from board ");
		
		if (fkey.equals("title")) sql.append(" where title like :fval");
		else if (fkey.equals("userid")) sql.append(" where userid like :fval");
		else if (fkey.equals("contents")) sql.append(" where contents like :fval");
		
		Map<String, Object> params = new HashMap<>();
		params.put("fval", "%"+fval+"%");

		return jdbcNamedTemplate.queryForObject(sql.toString(), params, Integer.class);
	}

		@Override
		public int deleteBoard(String b_no) {
			
			String sql = "delete from board where b_no = ?";
			
			Object[] param = new Object[] {b_no};
			
			return jdbcTemplate.update(sql, param);
		}


}
