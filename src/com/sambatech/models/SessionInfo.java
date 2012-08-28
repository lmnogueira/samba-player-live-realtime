package com.sambatech.models;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

public class SessionInfo implements Serializable{

	private static final long serialVersionUID = 9074032403519256313L;
	
	private String  idSession;
	private Long    timeStamp;
	private String  playerHash;
	private String  streamName;
	private String  ipAddress;
	private String  idMedia;
	private String  gender;
	private String  interest;
	
	public SessionInfo(String idSession, Long timeStamp, String playerHash, String idMedia, String streamName, String ipAddress, String gender, String interest){
		this.idSession = idSession;
		this.timeStamp = timeStamp;
		this.playerHash = playerHash;
		this.idMedia = idMedia;
		this.streamName = streamName;
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
	
	public String getPlayerHash() {
		return playerHash;
	}

	public void setPlayerHash(String playerHash) {
		this.playerHash = playerHash;
	}

	public String getIdMedia() {
		return idMedia;
	}

	public void setIdMedia(String idMedia) {
		this.idMedia = idMedia;
	}

	public String getStreamName() {
		return streamName;
	}

	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}
	
}