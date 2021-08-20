package org.huan.myk8s.dao.impl;

import org.huan.myk8s.dao.BookDAO;
import org.huan.myk8s.dto.BookDTO;
import org.huan.myk8s.entity.Book;
import org.huan.myk8s.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class BookDAOService implements BookDAO{
	
	@Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
	
	private final String SQL_INSERT = "insert into Book(bid,title, description) values(:bid,:title, :description)";
	private final String SQL_UPDATE = "update Book title = :title, description = :description where bid = :bid";
	private final String SQL_DELETE = "delete from Book where bid = :bid";
	@Override
	public BookDTO findBook(String id) {
		String query = "SELECT bid,title FROM book WHERE book.bid = :bid";
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("bid", id);
		//List<BookDTO> book =  jdbcTemplate.query(query, namedParameters, new BookMapper());
		BookDTO book =  jdbcTemplate.queryForObject(query, namedParameters, new BookMapper());
		if (book == null) throw new BookNotFoundException("001", "Book not find");
		return book;
	}
	
	@Override
	public void createBook(Book book) {
		//yHolder keyHolder = new GeneratedKeyHolder();		
		BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(book);
		jdbcTemplate.update(SQL_INSERT, beanPropertySqlParameterSource);

	}
	
	
	@Override
	public boolean updateBook(Book book) {
		book.setTitle("bbb"); //TODO
		jdbcTemplate.update(SQL_UPDATE, new BeanPropertySqlParameterSource(book));
		return jdbcTemplate.update("SQL_UPDATE", new BeanPropertySqlParameterSource(book)) > 0;

	}
	
	@Override
	public boolean deleteBook(String id) {
		return jdbcTemplate.update(SQL_DELETE, new MapSqlParameterSource().addValue("bid", id)) > 0;

	}
	
	
	
}
