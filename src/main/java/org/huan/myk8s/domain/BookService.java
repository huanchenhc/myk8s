package org.huan.myk8s.domain;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.huan.myk8s.dao.impl.BookDAOService;
import org.huan.myk8s.dto.BookDTO;
import org.huan.myk8s.dto.mapper.BookMapperUtil;
import org.huan.myk8s.exception.FieldDetailWithFixer;
import org.huan.myk8s.exception.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.ExecutableFindByIdOperation.ExecutableFindById;
import org.springframework.data.couchbase.core.ExecutableUpsertByIdOperation.ExecutableUpsertById;
import org.springframework.data.couchbase.core.query.N1QLExpression;
import org.springframework.data.couchbase.core.query.N1QLQuery;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.QueryCriteria;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.json.JsonObject;


@Service
public class BookService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BookDAOService bookDAOService;
	@Autowired
	private CouchbaseTemplate couchbaseTemplate;
	
	public BookDTO retrieveBookID(String id, boolean withCache) {
		if(withCache) {
			// Collection collection = bucket.defaultCollection();
			ExecutableFindById<BookDTO> findById = couchbaseTemplate.findById(BookDTO.class);
			BookDTO book = findById.one(id);
			if (book != null) {
				logger.info("Returned book " + id + " from couchbase cache.");
				return book;
			} 
		}		
		// access database
			BookDTO bookAtDB = bookDAOService.findBook(id);
			if (bookAtDB != null) {
				if(withCache) {
					ExecutableUpsertById<BookDTO> upsertById = couchbaseTemplate.upsertById(BookDTO.class);
					upsertById.one(bookAtDB);					
				}
				logger.info("Returned book " + id + " from database.");
				return bookAtDB;
			}
		
	return null;
	}

	@Transactional
	public void createBook(final BookDTO book) {
		validateBook(book);
		bookDAOService.createBook(BookMapperUtil.toEntity(book));
	}

	private void validateBook(BookDTO book) {
		List<FieldDetailWithFixer> invalidFields = new ArrayList<FieldDetailWithFixer>();
		FieldDetailWithFixer validID = validStringField(book.getId(), "bid", true, 2, 10);
		if (validID != null)
			invalidFields.add(validID);
		FieldDetailWithFixer validTitle = validStringField(book.getTitle(), "title", true, 1, 200);
		if (validTitle != null)
			invalidFields.add(validTitle);

		if (!invalidFields.isEmpty()) {
			throw new InvalidRequestException("BOOK-0001", "Request is invalid.", invalidFields);
		}
	}

	private FieldDetailWithFixer validStringField(String value, String fieldName, boolean required, int min, int max) {
		if (isNull(value)) {
			if (required)
				return new FieldDetailWithFixer(fieldName, fieldName + " cannot be null.");
		} else {
			value = value.trim();
			if (min > 0 && value.length() < min)
				return new FieldDetailWithFixer(fieldName, fieldName + " should have at least " + min + " characters.");
			else if (max > 0 && value.length() > max)
				return new FieldDetailWithFixer(fieldName, fieldName + " should have at most " + max + " characters.");

		}
		return null;
	}

	private boolean isNull(String value) {
		return value == null || value.trim().length() == 0;
	}

	@Transactional
	public void updateBook(final BookDTO book) {
		bookDAOService.updateBook(BookMapperUtil.toEntity(book));
	}

	@Transactional
	public boolean deleteBook(final String id) {
		return bookDAOService.deleteBook(id);
	}

	public List<BookDTO> retrieveBookIDLessThanWithPagination(String id, Integer pageNo, Integer pageSize) {
		List<BookDTO> findBookIDLessThan = bookDAOService.findBookIDlessThan(id, pageNo, pageSize);
		if (findBookIDLessThan != null) {
			return findBookIDLessThan;
		} else {
			return new ArrayList<BookDTO>();
		}
	}
	
	public List<BookDTO> retrieveBooksWithkeywordsFromCache(String keywords, Integer pageNo, Integer pageSize) {
		/*String str = "SELECT META().id as bid, title,description FROM myk8s._default WHERE description LIKE CONCAT('%',$keywords,'%')";
		JsonObject placeHolder = JsonObject.create().put("keywords", keywords);
		N1QLExpression n1qlExpression = N1QLExpression.s(str);
		N1QLQuery n1qlQuery = new N1QLQuery(n1qlExpression);
		n1qlQuery
				
				.parameterized(placeHolder, placeHolder);
		ExecutableFindByQuery<BookDTO> findByQuery = couchbaseTemplate.findByQuery(BookDTO.class);  
		Query query = new Query();
		query.
	
		query.setNamedParameters(placeHolder);
		findByQuery.
		.matching(query ) ;*/
		Query specialUsers = new Query(QueryCriteria.where("description").like(keywords));
		 final List<BookDTO> findBookIDLessThan = couchbaseTemplate.findByQuery(BookDTO.class).matching(specialUsers).all();
		
		
		if (findBookIDLessThan != null) {
			return findBookIDLessThan;
		} else {
			return new ArrayList<BookDTO>();
		}
	}
	
	

	public List<BookDTO> retrieveBooksWithkeywords(String keywords, Integer pageNo, Integer pageSize) {
		List<BookDTO> findBooksWithkeywords = bookDAOService.findBooksWithkeywords(keywords, pageNo, pageSize);
		if (findBooksWithkeywords != null) {
			return findBooksWithkeywords;
		} else {
			return new ArrayList<BookDTO>();
		}
	}

}
