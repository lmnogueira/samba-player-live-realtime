package com.sambatech.models;

import java.io.Serializable;

public class SessionViews implements Serializable{

	private static final long serialVersionUID = 9074032403519256313L;
	
	private String  nameSpace;
	private String  idMedia;
	private Long    views;
	
	public SessionViews(String nameSpace, String idMedia, Long views){
		this.nameSpace = nameSpace;
		this.views = views;
		this.idMedia = idMedia;
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

	public Long getViews() {
		return views;
	}

	public void setViews(Long views) {
		this.views = views;
	}	
}