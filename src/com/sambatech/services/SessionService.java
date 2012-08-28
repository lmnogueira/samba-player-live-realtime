package com.sambatech.services;
import java.util.Collection;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;
import com.sambatech.cluster.HazelCastSingleton;
import com.sambatech.constants.Constants;
import com.sambatech.models.Session;
import com.sambatech.models.SessionViews;

@Path("/session")
public class SessionService {
	
	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public String countSession(
			@DefaultValue("") @QueryParam("cb") String callback) {
	
		if(callback.equals(""))
			callback = "callback";
		
		//instanciando o client
		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(HazelCastSingleton.getInstance()
						.getClientConfig());
		//obtendo o mapa de sessions
		IMap<String, Session> sessionMap = client.getMap(Constants.SESSIONS);
						
		return callback + "({ \"count\" : \"" + sessionMap.size() + "\" });";
	}
	
	@GET
	@Path("/count/{namespace}")
	@Produces(MediaType.APPLICATION_JSON)
	public String countNamespace(@PathParam("namespace") String namespace,
			@DefaultValue("") @QueryParam("cb") String callback) {
		
		if(callback.equals(""))
			callback = "callback";
		
		//instanciando o client
		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(HazelCastSingleton.getInstance()
						.getClientConfig());
		//obtendo o mapa de sessions
		IMap<String, Session> sessionMap = client.getMap(Constants.SESSIONS);
				
		Collection<Session> sessionResult = (Collection<Session>) sessionMap.values(new
				  SqlPredicate("nameSpace LIKE "+ namespace +""));
		
		return callback + "({ \"count\" : \"" + sessionResult.size() + "\" });";
	}
	
	@GET
	@Path("/views/{namespace}")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewsByNamespace(@PathParam("namespace") String namespace,
			@DefaultValue("") @QueryParam("cb") String callback) {
		
		if(callback.equals(""))
			callback = "callback";
		
		//instanciando o client
		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(HazelCastSingleton.getInstance()
						.getClientConfig());
		//obtendo o mapa de sessions
		IMap<String, SessionViews> sessionMap = client.getMap(Constants.SESSIONS_VIEWS);
				
		SessionViews sessionResult = sessionMap.get(namespace);
		
		return callback + "({ \"views\" : \"" + sessionResult.getViews() + "\" });";
	}
	
	
	@GET
	@Path("/views/{namespace}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewsById(@PathParam("namespace") String namespace,@PathParam("id") String id,
			@DefaultValue("") @QueryParam("cb") String callback) {
		
		if(callback.equals(""))
			callback = "callback";
		
		//instanciando o client
		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(HazelCastSingleton.getInstance()
						.getClientConfig());
		//obtendo o mapa de sessions
		IMap<String, SessionViews> sessionMap = client.getMap(Constants.SESSIONS_VIEWS);
				
		SessionViews sessionResult = sessionMap.get(namespace + ":" + id);
		
		return callback + "({ \"views\" : \"" + sessionResult.getViews() + "\" });";
	}	
}
