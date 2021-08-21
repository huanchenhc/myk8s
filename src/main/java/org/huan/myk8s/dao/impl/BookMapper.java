package org.huan.myk8s.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.huan.myk8s.dto.BookDTO;
import org.springframework.jdbc.core.RowMapper;

public class BookMapper implements RowMapper<BookDTO> {
	
	@Override
	public BookDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BookDTO o = new BookDTO();
		o.setId(rs.getString("bid"));
		o.setTitle(rs.getString("title"));
		o.setDescription(rs.getString("description"));
	    return o;
	}
}
