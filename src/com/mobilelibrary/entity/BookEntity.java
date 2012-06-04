package com.mobilelibrary.entity;

public class BookEntity {
	
	public String getBookText() {
		return bookText;
	}
	public void setBookText(String bookText) {
		this.bookText = bookText;
	}
	public String getBookImageUrl() {
		return bookImageUrl;
	}
	public void setBookImageUrl(String bookImageUrl) {
		this.bookImageUrl = bookImageUrl;
	}
	//图书的标题
	private String bookText;
	//图书图片的url地址
	private String bookImageUrl;


}
