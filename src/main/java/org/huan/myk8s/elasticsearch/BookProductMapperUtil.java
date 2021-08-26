package org.huan.myk8s.elasticsearch;

import org.huan.myk8s.dto.BookDTO;

public class BookProductMapperUtil {
	public static BookProduct toProduct(BookDTO dto) {
		BookProduct bookProduct = new BookProduct();
		bookProduct.setId(dto.getId());
		bookProduct.setTitle(dto.getTitle());
		bookProduct.setDescription(dto.getDescription());
		return bookProduct;
	}

	public static BookDTO toDto(BookProduct bookProduct) {
		BookDTO dto = new BookDTO();
		dto.setId(bookProduct.getId());
		dto.setTitle(bookProduct.getTitle());
		dto.setDescription(bookProduct.getDescription());
		return dto;
	}
}
