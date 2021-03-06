package com.sambatech.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import com.sambatech.cluster.HazelCastSingleton;
import com.sambatech.constants.Constants;
import com.sambatech.models.SessionInfo;
import com.sambatech.models.SessionViews;

public class Processor {
    private volatile boolean running = true;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public Processor(int nThreads) {
    	final HazelcastInstance client = HazelcastClient.newHazelcastClient(HazelCastSingleton.getInstance().getClientConfig());
        final ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        final IMap<String, SessionInfo> sessionInfo = client.getMap(Constants.SESSIONS_INFO);
        final IQueue<String> sessionInfoQueue = client.getQueue(Constants.SESSIONS_INFO);
        for (int i = 0; i < nThreads; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                	Long timerSleep = 5000L;
                	
                    while (running) {
//                        Transaction transaction = Hazelcast.getTransaction();
                        try {
                        	
//                          transaction.begin();
                            String sessionId = sessionInfoQueue.poll();
                            
                            if(sessionId != null){                           	
                            	SessionInfo session= sessionInfo.get(sessionId);
                            	processSessionViews(session,client);
                                sessionInfo.remove(sessionId);
                                timerSleep = 5000L;
                            } else {
                            	logger.log(Level.INFO, "Processor thread sleeping: " + timerSleep);
								Thread.sleep(timerSleep);                            	
                            	timerSleep = timerSleep + 5000L;
                            }

                            //transaction.commit();
                            
                        } catch (Exception e) {
                            logger.log(Level.INFO, "Processor transaction rollback: ", e);
                            //transaction.rollback();
                        }
                    }
                }
            });
        }
        logger.info("Processor started with " + nThreads + " threads.");
    }

    private void processSessionViews(SessionInfo session, HazelcastInstance client) {
    	IMap<String, SessionViews> sessionViews = client.getMap(Constants.SESSIONS_VIEWS);
    	
    	Lock lock = client.getLock(session.getNameSpace() + ":" + session.getIdMedia());
    	lock.lock();
    	try {
    		SessionViews sessionView = sessionViews.get(session.getNameSpace() + ":" + session.getIdMedia());
    		
    		if(sessionView == null){
    			sessionView = new SessionViews(session.getNameSpace(), session.getIdMedia(),1L);
    		} else {
    			sessionView.setViews((Long)sessionView.getViews() + 1);
    		}
    		
    		sessionViews.put(session.getNameSpace() + ":" + session.getIdMedia(), sessionView);
    		
    	} finally {
    	   lock.unlock();
    	}
    	
    	lock = client.getLock(session.getNameSpace());
    	lock.lock();
    	try {
    		SessionViews sessionView = sessionViews.get(session.getNameSpace());
    		
    		if(sessionView == null){
    			sessionView = new SessionViews(session.getNameSpace(), "",1L);
    		} else {
    			sessionView.setViews((Long)sessionView.getViews() + 1);
    		}
    		
    		sessionViews.put(session.getNameSpace(), sessionView);
    		
    	} finally {
    	   lock.unlock();
    	}    	   	
    	
    }
}