package com.file.ops.dto;

import java.io.Serializable;
import java.util.Date;

public class FileDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2462414510779266382L;
	
	private long id;
	private String name;
	private String type;
	private Date createdTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
