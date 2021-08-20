package org.huan.myk8s.dto;

import java.sql.Timestamp;

public class BookDTO {
	private String id;
	private String ISBN;
	private long LID;
	private Timestamp updateTime;	
	private String title;
	private String description;
	private String PicUrl;
	private String cname;
	private boolean loanable;
	private boolean forSale;
	private boolean isPrivate;
	private double bookValue;
	private boolean bookState;
	
	public BookDTO() {}
	public BookDTO(String id, String title, String description) {
		
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
	
	@Override
	public String toString() {
		return "BookDTO{" +
				"id='" + id + "'" +
				", title='" + title + "'" +
				", description='" + description + "'" +
				"}";
	}
}
