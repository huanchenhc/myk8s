package org.huan.myk8s.domain;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.huan.myk8s.dao.impl.BookDAOService;
import org.huan.myk8s.dto.BookDTO;
import org.huan.myk8s.dto.mapper.BookMapperUtil;
import org.huan.myk8s.exception.FieldDetailWithFixer;
import org.huan.myk8s.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
	@Autowired
	private BookDAOService bookDAOService;
	
	public BookDTO retrieveBookID(String id) {
		BookDTO book = bookDAOService.findBook(id);
		if(book !=null) {
			return book;
		} else {
			return null;
		}
	}

	@Transactional
	public void createBook(final BookDTO book) {
		validateBook(book);
		bookDAOService.createBook(BookMapperUtil.toEntity(book));
	}
	
	
	private void validateBook(BookDTO book) {
		List<FieldDetailWithFixer> invalidFields = new ArrayList<FieldDetailWithFixer>();
		FieldDetailWithFixer validID = validStringField(book.getId(), "bid", true, 2, 10);
		if(validID != null) invalidFields.add(validID);
		FieldDetailWithFixer validTitle= validStringField(book.getTitle(), "title", true, 1, 200);
		if(validTitle != null) invalidFields.add(validTitle);	
		
		if(!invalidFields.isEmpty()) {
			throw new InvalidRequestException("BOOK-0001", "Request is invalid.", invalidFields);
		}		
	}
	
	private FieldDetailWithFixer validStringField(String value, String fieldName, boolean required, int min, int max) {
		if(isNull(value)) {
			if(required)
				return new FieldDetailWithFixer(fieldName, fieldName + " cannot be null.");
		}else {
			value = value.trim();
			if(min > 0 && value.length() < min)
				return new FieldDetailWithFixer(fieldName, fieldName + " should have at least " + min + " characters.");
			else if(max > 0 && value.length() > max)
				return new FieldDetailWithFixer(fieldName, fieldName + " should have at most " + max + " characters.");

		}
		return null;
	}
	
	private boolean isNull(String value) {
		return value ==null || value.trim().length() == 0;
	}
	
	@Transactional
	public void updateBook(final BookDTO book) {
		bookDAOService.updateBook(BookMapperUtil.toEntity(book));
	}	

	@Transactional
	public boolean deleteBook(final String id) {
		return bookDAOService.deleteBook(id);
	}

	public List<BookDTO> retrieveBookIDLessThanWithPagination(String id, Integer pageNo,
			Integer pageSize) {
		List<BookDTO> findBookIDLessThan = bookDAOService.findBookIDlessThan(id,pageNo,pageSize);
		if(findBookIDLessThan != null) {
			return findBookIDLessThan;
		} else {
			return new ArrayList<BookDTO>();
		}
	}

	public List<BookDTO> retrieveBooksWithkeywords(String keywords, Integer pageNo, Integer pageSize) {
		List<BookDTO> findBooksWithkeywords = bookDAOService.findBooksWithkeywords(keywords,pageNo,pageSize);
		if(findBooksWithkeywords != null) {
			return findBooksWithkeywords;
		} else {
			return new ArrayList<BookDTO>();
		}
	}
	
}
