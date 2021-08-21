package org.huan.myk8s;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.huan.myk8s.dao.BookDAO;
import org.huan.myk8s.dto.BookDTO;
import org.huan.myk8s.entity.Book;
import org.huan.myk8s.exception.BookNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookDAOTest {
	@Autowired
	private BookDAO bookDAO;
	
	@Test()
	@DisplayName("findbook dao test ")
	void testFindbookDAO() {
		BookDTO res = bookDAO.findBook("05"); 
		//assertEquals(3, res.size());
		assertEquals("05", res.getId());
		assertEquals("My school is back", res.getTitle());
		
	
	}

	@Test()
	@DisplayName("findbook dao test ")
	void testFindbookDAO_notFound() {
		assertThrows(BookNotFoundException.class, () -> {
			bookDAO.findBook("06");
		  });
		
	
	}
	
	@Test
	@DisplayName("createBook dao test ")
	void testCreateBookDAO() {
		Book book = new Book();
		book.setBid("05");
		book.setTitle("My Summer Fun");
		book.setDescription("Ethan's book");
		bookDAO.createBook(book); 
		
		BookDTO res = bookDAO.findBook("05"); 
		assertEquals("05", res.getId());
		assertEquals("My Summer Fun", res.getTitle());
	}

	
	@Test
	@DisplayName("createBook dao test ")
	void testUpdateBookDAO() {
		Book book = new Book();
		book.setBid("05");
		book.setTitle("My school was back");
		book.setDescription("Ethan's book");
		bookDAO.updateBook(book); 
		//bookDAO.updateBook("05","My school is back","Ethan's book"); 
		
		BookDTO res = bookDAO.findBook("05"); 
		assertEquals("05", res.getId());
		assertEquals("My school was back", res.getTitle());
	}
	
	@Test
	void testDeleteBookDAO() {
		assertEquals(true,bookDAO.deleteBook("05"));
	}
	
}
