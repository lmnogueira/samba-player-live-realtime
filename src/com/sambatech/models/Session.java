package com.sambatech.models;

import java.io.Serializable;

public class Session implements Serializable{

	private static final long serialVersionUID = 9074032403519256313L;
	
	private String  nameSpace;
	private String  idMedia;
	
	public Session(String nameSpace, String idMedia){
		this.nameSpace = nameSpace;
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
}