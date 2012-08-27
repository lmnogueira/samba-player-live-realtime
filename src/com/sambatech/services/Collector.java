package com.sambatech.services;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;
import com.sambatech.cluster.HazelCastSingleton;
import com.sambatech.constants.Constants;
import com.sambatech.models.Event;
import com.sambatech.models.Session;
import com.sambatech.models.SessionInfo;

@Path("/collect")
public class Collector {

	@GET
	@Path("/event/{namespace}/{event}")
	@Produces(MediaType.TEXT_PLAIN)
	public String trackEvent(@PathParam("namespace") String namespace,
			@PathParam("event") String eventName,
			@DefaultValue("") @QueryParam("idMedia") String idMedia,
			@DefaultValue("") @QueryParam("idSession") String idSession) {

		Event event = new Event(idSession, eventName, namespace, idMedia,
				(Long) new Date().getTime());

		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(HazelCastSingleton.getInstance()
						.getClientConfig());

		Map<String, Event> mapEvents = client.getMap("events");
		mapEvents.put(event.getName(), event);

		return "Tracking Event! => " + mapEvents.get(eventName).getName();
	}

	@GET
	@Path("/session/create")
	@Produces(MediaType.TEXT_PLAIN)
	public String trackSessionInfo(@Context HttpServletRequest req,
			@DefaultValue("") @QueryParam("s") String sessionID, // session
			@DefaultValue("") @QueryParam("ph") String playerHash, // playerhash
			@DefaultValue("") @QueryParam("id") String idMedia, // idmedia
			@DefaultValue("") @QueryParam("sn") String streamName, // streamname
			@DefaultValue("") @QueryParam("g") String gender, // gender
			@DefaultValue("") @QueryParam("i") String interest // interest
	) {

		String remoteAddress = req.getRemoteAddr();
		// remoteAddress = req.getHeader( "X_FORWARDED_FOR" );

		Session session = new Session(sessionID, idMedia);
		SessionInfo sessionInfo = new SessionInfo(sessionID,
				(Long) new Date().getTime(), playerHash, idMedia, streamName,
				remoteAddress, gender, interest);

		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(HazelCastSingleton.getInstance()
						.getClientConfig());

		Map<String, Session> mapSessions = client.getMap(Constants.SESSIONS);
		mapSessions.put(session.getIdSession(), session);
		
		Map<String, Session> mapSessionsInfo = client.getMap(Constants.SESSIONS_INFO);

		IMap<String, Session> map = client.getMap("sessions");
		Collection<Session> sess = (Collection<Session>) map
				.values(new SqlPredicate("timeStamp >= 100"));

		return "Create Session:";
	}
	
	@GET
	@Path("/session/update")
	@Produces(MediaType.TEXT_PLAIN)
	public String trackSessionUpdate(@Context HttpServletRequest req,
			@DefaultValue("") @QueryParam("s") String sessionID, // session
			@DefaultValue("") @QueryParam("id") String idMedia // playerhash
	) {

		Session session = new Session(sessionID, idMedia);

		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(HazelCastSingleton.getInstance()
						.getClientConfig());

		Map<String, Session> mapSessions = client.getMap("sessions");

		mapSessions.put(session.getIdSession(), session);

		return "Session updated.";
	}
	
}
