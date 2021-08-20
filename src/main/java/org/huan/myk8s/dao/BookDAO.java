package org.huan.myk8s.dao;

import java.util.List;

import org.huan.myk8s.dto.BookDTO;
import org.huan.myk8s.entity.Book;

public interface BookDAO {
	public BookDTO findBook(String id);
	public void createBook(Book book);
	public boolean updateBook(Book book);
	public boolean deleteBook(String id);
}
