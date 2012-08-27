package com.sambatech.models;

import java.io.Serializable;

public class Event implements Serializable {
	private static final long serialVersionUID = 5509078285868903229L;
	private String idSession;
	private String name;
	private String nameSpace;
	private String idMedia;
	private Long timeStamp;
	
	public Event(String idSession, String name, String nameSpace, String idMedia, Long timeStamp) {
		this.idSession = idSession;
		this.name = name;
		this.nameSpace = nameSpace;
		this.idMedia = idMedia;
		this.timeStamp = timeStamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getIdMedia() {
		return idMedia;
	}

	public void setIdMedia(String idMedia) {
		this.idMedia = idMedia;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getIdSession() {
		return idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}
}