package org.huan.myk8s.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.huan.myk8s.controller.util.HeaderUtil;
import org.huan.myk8s.domain.BookService;
import org.huan.myk8s.dto.BookDTO;
import org.huan.myk8s.exception.BookNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class BookController {
	@Autowired
	BookService bookService;
	
    private final Logger log = LoggerFactory.getLogger(BookController.class);
    
	@GetMapping("/books/{id}")
	public BookDTO retrieveBook(@PathVariable  String id)  {
		return bookService.retrieveBookID(id);
	}
    
	@GetMapping("/books")
	public BookDTO retrieveBookbyPra(@RequestParam  String id)  {
		return bookService.retrieveBookID(id);
	}
	
	
	@GetMapping("/bookspagination")
	public List<BookDTO> retrieveAllUsersWithPagination(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam String id)  {
		return bookService.retrieveBookIDLessThanWithPagination(id, pageNo, pageSize);
	}
	
	@GetMapping("/bookskeywords")
	public List<BookDTO> retrieveAllUsersWithkeywords(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam String keywords)  {
		return bookService.retrieveBooksWithkeywords(keywords, pageNo, pageSize);
	}
	
	@PostMapping("/books")
	public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO book){
		log.debug("Creating book with " + book.toString());
		bookService.createBook(book);
		
		if( true ) {
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(book.getId()).toUri();
				return ResponseEntity.created(location)
			            .headers(HeaderUtil.createEntityCreationAlert("Book", book.getId()))
			            .body(book);
		}else
			return ResponseEntity.internalServerError().build();
	}
	
	@PutMapping("/books/{id}")
	public ResponseEntity<Object> updateBook(@PathVariable String id, @Valid  @RequestBody BookDTO book){
		book.setId(id);
		bookService.updateBook(book);
		if(true) {
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(book.getId()).toUri();
				
				return ResponseEntity.created(location).build();
		}else
			return ResponseEntity.internalServerError().build();
	}
	
	@DeleteMapping("/books/{id}")
	public void deleteBook(@PathVariable String id) {
		if(!bookService.deleteBook(id)) 
			throw new BookNotFoundException("USER0001", "id-"+ id);
	}
	
	
	
	
}
