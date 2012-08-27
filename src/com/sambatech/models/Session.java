package com.sambatech.models;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

public class Session implements Serializable{

	private static final long serialVersionUID = 9074032403519256313L;
	
	private String  idSession;
	private String  idMedia;
	
	public Session(String idSession, String idMedia){
		this.idSession = idSession;
		this.idMedia = idMedia;
	}
	
	public String getIdSession() {
		return idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}
	
	public String getIdMedia() {
		return idMedia;
	}

	public void setIdMedia(String idMedia) {
		this.idMedia = idMedia;
	}	
}