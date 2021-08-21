package org.huan.myk8s.dao;

import java.util.List;

import org.huan.myk8s.dto.BookDTO;
import org.huan.myk8s.entity.Book;

public interface BookDAO {
	public BookDTO findBook(String id);
	public void createBook(Book book);
	public void updateBook(Book book);
	public boolean deleteBook(String id);
	public void updateBook(String string, String string2, String string3);
	public List<BookDTO> findBookIDlessThan(String id, Integer pageNo, Integer pageSize);
	public List<BookDTO> findBooksWithkeywords(String keywords, Integer pageNo, Integer pageSize);
}
