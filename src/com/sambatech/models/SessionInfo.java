package com.sambatech.models;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

public class SessionInfo implements Serializable{

	private static final long serialVersionUID = 9074032403519256313L;
	
	private String  idSession;
	private String  nameSpace;
	private Long    timeStamp;
	private String  ipAddress;
	private String  idMedia;
	private String  gender;
	private String  interest;
	
	public SessionInfo(String idSession, Long timeStamp, String nameSpace, String idMedia, String ipAddress, String gender, String interest){
		this.idSession = idSession;
		this.timeStamp = timeStamp;
		this.nameSpace = nameSpace;
		this.idMedia = idMedia;
		this.ipAddress = ipAddress;
		this.gender = gender;
		this.interest = interest;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}	
	
	public String getIdSession() {
		return idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}
	
	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
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

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}
	
}