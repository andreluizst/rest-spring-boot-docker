package br.com.restsb.data.model;

import java.io.Serializable;

public class Greeting implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final long id;
	private final String content;
	
	
	public Greeting(long id, String content) {
		super();
		this.id = id;
		this.content = content;
	}
	
	public long getId() {
		return id;
	}
	public String getContent() {
		return content;
	}
	
	
}
