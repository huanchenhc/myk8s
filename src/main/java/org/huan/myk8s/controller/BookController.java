package org.huan.myk8s.controller;

import org.huan.myk8s.domain.BookService;
import org.huan.myk8s.dto.BookDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
}
