package org.huan.myk8s.elasticsearch;



import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "productindex")
public class BookProduct {
	@Id
	private String id;

	@Field(type = FieldType.Text, name = "ISBN;")
	private String ISBN;;

	@Field(type = FieldType.Text, name = "LID")
	private long LID;

	@Field(type = FieldType.Date, name = "updateTime")
	private Date updateTime;

	@Field(type = FieldType.Text, name = "title")
	private String title;

	@Field(type = FieldType.Text, name = "description")
	private String description;

	@Field(type = FieldType.Text, name = "PicUrl;")
	private String PicUrl;;

	@Field(type = FieldType.Keyword, name = "cname")
	private String cname;

	@Field(type = FieldType.Text, name = "loanable;")
	private boolean loanable;

	@Field(type = FieldType.Text, name = "forSale;")
	private boolean forSale;;

	@Field(type = FieldType.Text, name = "isPrivate")
	private boolean isPrivate;

	@Field(type = FieldType.Text, name = "bookValue")
	private double bookValue;

	@Field(type = FieldType.Text, name = "bookState")
	private boolean bookState;

	public BookProduct() {}
	public BookProduct(String id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
