package com.sambatech.services;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.sambatech.cluster.HazelCastSingleton;
import com.sambatech.constants.Constants;

@Path("/session")
public class SessionService {

	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public String trackEvent(
			@DefaultValue("") @QueryParam("cb") String callback) {

		if(callback.equals(""))
			callback = "callback";
		
		//instanciando o client
		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(HazelCastSingleton.getInstance()
						.getClientConfig());

		//obtendo o mapa de sessions
		IMap<String, String> sessionMap = client.getMap(Constants.SESSIONS);
		
		return callback + "({ \"count\" : \"" + sessionMap.size() + "\" });";
	}
	
}
