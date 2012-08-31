package com.sambatech.cluster;

import com.hazelcast.client.ClientConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelCastSingleton {

	private static HazelCastSingleton instance;
	private HazelcastInstance hazelcastInstance;
	private ClientConfig config;
	
	private HazelCastSingleton() {
		this.hazelcastInstance = Hazelcast.newHazelcastInstance(null);
		
		ClientConfig config = new ClientConfig();
		config.getGroupConfig().setName("dev").setPassword("dev-pass");
		config.addAddress("localhost", "localhost:5702");
	}	

	public HazelcastInstance getHazelcastInstance() {
		return hazelcastInstance;
	}
	
	public ClientConfig getClientConfig(){
		return config;
	}

	public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance;
	}

	public static HazelCastSingleton getInstance() {
		if (instance == null)
			instance = new HazelCastSingleton();
		return instance;
	}
}