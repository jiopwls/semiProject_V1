package jinn.spring.mvc.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import jinn.spring.mvc.vo.BoardVO;

@Repository("bdao")
public class BoardDAOImpl implements BoardDAO{

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleInsert;
	
	public BoardDAOImpl(DataSource dataSource) {
		simpleInsert = new SimpleJdbcInsert(dataSource)
				.withTableName("board")
				.usingColumns("title","userid","contents");
	}
	
	@Override
	public int insertBoard(BoardVO bvo) {
		String sql = "insert into board" + "(title,userid,contents)" + "value(?,?,?)";
		
		Object[] params = new Object[] {
				bvo.getTitle(), bvo.getUserid(), bvo.getContents()
		};
		return jdbcTemplate.update(sql, params);
	}

}
