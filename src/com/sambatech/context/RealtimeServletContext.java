package com.sambatech.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sambatech.cluster.HazelCastSingleton;
 
public class RealtimeServletContext implements ServletContextListener{
	RealtimeServletContext context;
	public void contextInitialized(ServletContextEvent contextEvent) {
		//context = contextEvent.getServletContext();
		//context.setAttribute("TEST", "TEST_VALUE");
		HazelCastSingleton.getInstance();
	}
	public void contextDestroyed(ServletContextEvent contextEvent) {
		//context = contextEvent.getServletContext();
		//System.out.println("Context Destroyed");
	}
}
