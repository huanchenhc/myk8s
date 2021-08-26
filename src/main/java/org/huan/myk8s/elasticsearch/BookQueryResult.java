package org.huan.myk8s.elasticsearch;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;


@Document
public class BookQueryResult {
	@Id
	private String query;
	private List<BookProduct> bookProducts;
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public List<BookProduct> getBookProducts() {
		return bookProducts;
	}
	public void setBookProducts(List<BookProduct> bookProducts) {
		this.bookProducts = bookProducts;
	}
	
}
