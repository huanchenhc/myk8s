package org.huan.myk8s.entity;

import java.sql.Timestamp;

public class Book {

	private String bid;
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
	
	public Book() {}
	public Book(String id, String title, String description) {
		
		this.bid = id;
		this.title = title;
		this.description = description;
	}
	

	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
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
