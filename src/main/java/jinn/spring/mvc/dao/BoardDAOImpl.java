package jinn.spring.mvc.dao;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

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

	
	//@Autowired log4j.xml bean 태그에 정의한 경우 생략가능
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
	public List<BoardVO> selectBoard() {
		String sql = "select b_no, title, userid, regdate,views from board order by b_no desc";
		
		return jdbcNamedTemplate.query(sql, Collections.emptyMap(), boardMapper);
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

}
