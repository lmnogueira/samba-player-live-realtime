package com.sambatech.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import com.sambatech.cluster.HazelCastSingleton;
import com.sambatech.constants.Constants;
import com.sambatech.models.Event;

public class Processor {
    private volatile boolean running = true;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public Processor(int nThreads) {
    	final HazelcastInstance client = HazelcastClient.newHazelcastClient(HazelCastSingleton.getInstance().getClientConfig());
        final ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        final IMap<String, Event> map = client.getMap(Constants.EVENTS);
        final IQueue<String> queue = client.getQueue(Constants.EVENTS);
        for (int i = 0; i < nThreads; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                	int timeSleep = 0;
                	                	
                    while (running) {
//                        Transaction transaction = Hazelcast.getTransaction();
                        try {
                        	
//                          transaction.begin();
                            String reportId = queue.take();
                            
                            if(reportId.length() > 0){
                            	logger.log(Level.INFO, reportId.toString());
                            }
                            //CrashReport report = map.get(reportId);
                            //process(report.getJSON());
                            //map.put(reportId, report);
                            //transaction.commit();
                            
                        } catch (Exception e) {
                            logger.log(Level.INFO, "Processor transaction rollback: ", e);
//                            transaction.rollback();
                        }
                    }
                }
            });
        }
        logger.info("Processor started with " + nThreads + " threads.");
    }

    private void process(Map<String, Object> map) {
        System.out.println("processing...");
    }

    public static void main(String[] args) {
        Processor processor = new Processor(5);
    }
}