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
import com.hazelcast.core.IQueue;
import com.hazelcast.query.SqlPredicate;
import com.hazelcast.util.Base64;
import com.sambatech.cluster.HazelCastSingleton;
import com.sambatech.constants.Constants;
import com.sambatech.models.Event;
import com.sambatech.models.Session;
import com.sambatech.models.SessionInfo;

@Path("/collect")
public class Collector {

	private static final String PIXEL_B64  = "R0lGODlhAQABAPAAAAAAAAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
	private static final byte[] PIXEL_BYTES = Base64.decode(PIXEL_B64.getBytes());
	
	@GET
	@Path("/event")
	@Produces("image/gif")
	public byte[] trackEvent(
			@DefaultValue("") @QueryParam("ns") String namespace,
			@DefaultValue("") @QueryParam("en") String eventName,
			@DefaultValue("") @QueryParam("id") String idMedia,
			@DefaultValue("") @QueryParam("s") String idSession) {

		Event event = new Event(idSession + ":" + eventName, eventName, namespace, idMedia,
				(Long) new Date().getTime());

		//instanciando o client
		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(HazelCastSingleton.getInstance()
						.getClientConfig());

		//obtendo o mapa de eventos
		Map<String, Event> mapEvents = client.getMap("events");
		mapEvents.put(event.getIdSession(), event);

		//adicionando o events na FILA
		IQueue<String> eventQueue = client.getQueue(Constants.EVENTS);
		eventQueue.offer(event.getIdSession());		
		
		return PIXEL_BYTES;
	}

	@GET
	@Path("/session/create")
	@Produces("image/gif")
	public byte[] trackSessionInfo(@Context HttpServletRequest req,
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

		//instanciando o client
		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(HazelCastSingleton.getInstance()
						.getClientConfig());

		//obtendo o mapa de sessions
		Map<String, Session> mapSessions = client.getMap(Constants.SESSIONS);
		mapSessions.put(session.getIdSession(), session);

		//obtendo o mapa de sessionsInfo
		Map<String, SessionInfo> mapSessionsInfo = client
				.getMap(Constants.SESSIONS_INFO);
		mapSessionsInfo.put(sessionInfo.getIdSession(), sessionInfo);

		//adicionando a sessionID na FILA
		IQueue<String> sessionQueue = client.getQueue(Constants.SESSIONS_INFO);
		sessionQueue.offer(session.getIdSession());
		
		/*
		 * Map<String, Session> mapSessionsInfo = client
		 * .getMap(Constants.SESSIONS_INFO);
		 * 
		 * IMap<String, Session> map = client.getMap("sessions");
		 * Collection<Session> sess = (Collection<Session>) map .values(new
		 * SqlPredicate("timeStamp >= 100"));
		 */
		
		return PIXEL_BYTES;
	}

	@GET
	@Path("/session/update")
	@Produces("image/gif")
	public byte[] trackSessionUpdate(@Context HttpServletRequest req,
			@DefaultValue("") @QueryParam("s") String sessionID, // session
			@DefaultValue("") @QueryParam("id") String idMedia // playerhash
	) {

		Session session = new Session(sessionID, idMedia);

		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(HazelCastSingleton.getInstance()
						.getClientConfig());

		Map<String, Session> mapSessions = client.getMap("sessions");

		mapSessions.put(session.getIdSession(), session);

		return PIXEL_BYTES;
	}

}
