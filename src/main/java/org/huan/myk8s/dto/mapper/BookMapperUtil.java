package org.huan.myk8s.dto.mapper;

import org.huan.myk8s.dto.BookDTO;
import org.huan.myk8s.entity.Book;

public class BookMapperUtil {
	public static Book toEntity(BookDTO dto) {
		Book book = new Book();
		book.setBid(dto.getId());
		book.setTitle(dto.getTitle());
		book.setDescription(dto.getDescription());
		return book;
	}

	public static BookDTO toDto(Book entity) {
		BookDTO dto = new BookDTO();
		dto.setId(entity.getBid());
		dto.setTitle(entity.getTitle());
		dto.setDescription(entity.getDescription());
		return dto;
	}
}
