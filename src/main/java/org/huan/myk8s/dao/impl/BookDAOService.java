package org.huan.myk8s.dao.impl;

import java.util.List;

import org.huan.myk8s.dao.BookDAO;
import org.huan.myk8s.dto.BookDTO;
import org.huan.myk8s.entity.Book;
import org.huan.myk8s.exception.BookNotFoundException;
import org.huan.myk8s.exception.DupblicatedBookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class BookDAOService implements BookDAO{
	
	@Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
	
	private final String SQL_INSERT = "insert into Book(bid,title, description) values(:bid,:title, :description)";
	private final String SQL_UPDATE = "update Book set title = :title, description = :description where bid = :bid";
	private final String SQL_DELETE = "delete from Book where bid = :bid";
	@Override
	public BookDTO findBook(String id) {
		String query = "SELECT bid,title,description FROM book WHERE book.bid = :bid";
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("bid", id);
		//List<BookDTO> book =  jdbcTemplate.query(query, namedParameters, new BookMapper());
		BookDTO book =  jdbcTemplate.queryForObject(query, namedParameters, new BookMapper());
		if (book == null) throw new BookNotFoundException("001", "Book not find");
		return book;
	}
	
	@Override
	public List<BookDTO> findBookIDlessThan(String id, Integer pageNo, Integer pageSize) {
		String query = "SELECT bid, title,description FROM book WHERE book.bid <= :bid ORDER BY bid asc LIMIT :limit OFFSET :offset";
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("bid", id).addValue("limit", pageSize).addValue("offset", pageSize*pageNo);;
		List<BookDTO> books =  jdbcTemplate.query(query, namedParameters, new BookMapper());
		if (books.size() == 0) throw new BookNotFoundException("001", "Book not find");
		return books;
	}
	
	@Override
	public List<BookDTO> findBooksWithkeywords(String keywords, Integer pageNo, Integer pageSize) {
		String query = "SELECT bid, title,description FROM book WHERE description LIKE CONCAT('%',:keywords,'%') ORDER BY bid asc LIMIT :limit OFFSET :offset";
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("keywords", keywords).addValue("limit", pageSize).addValue("offset", pageSize*pageNo);		
		List<BookDTO> books =  jdbcTemplate.query(query, namedParameters, new BookMapper());
		if (books.size() == 0) throw new BookNotFoundException("001", "Book not find");
		return books;
	}
	
	@Override
	public void createBook(Book book) {
		//yHolder keyHolder = new GeneratedKeyHolder();	
		String query = "SELECT count(bid) FROM book WHERE book.bid = :bid";
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("bid", book.getBid());
		SqlRowSet queryForRowSet = jdbcTemplate.queryForRowSet(query, namedParameters);
		if(queryForRowSet.next() && queryForRowSet.getInt(1) == 1) {
			throw new DupblicatedBookException("002", "Book id has existed");
		} else {
			BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(book);
			jdbcTemplate.update(SQL_INSERT, beanPropertySqlParameterSource);
		}		 
	}
	
	
	@Override
	public void updateBook(Book book) {
		BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(book);
		jdbcTemplate.update(SQL_UPDATE,beanPropertySqlParameterSource);

	}
	
	@Override
	public void updateBook(String id, String title, String description) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("bid", id).addValue("title", title).addValue("description", description);
		jdbcTemplate.update(SQL_UPDATE,namedParameters);

	}
	
	@Override
	public boolean deleteBook(String id) {
		return jdbcTemplate.update(SQL_DELETE, new MapSqlParameterSource().addValue("bid", id)) > 0;

	}




	
	
	
}
