package com.sambatech.models;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

public class Session implements Serializable{

	private static final long serialVersionUID = 9074032403519256313L;
	
	private String  nameSpace;
	private String  idMedia;
	
	public Session(String nSpace, String idMedia){
		this.nameSpace = nSpace;
		this.idMedia = idMedia;
	}
	
	public String getNameSpace() {
		return nameSpace;
	}

	public void setIdSession(String nSpace) {
		this.nameSpace = nSpace;
	}
	
	public String getIdMedia() {
		return idMedia;
	}

	public void setIdMedia(String idMedia) {
		this.idMedia = idMedia;
	}	
}