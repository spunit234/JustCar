package com.edios.cdf.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class SessionManager implements HttpSessionListener {

    public static Map<String, HttpSession> sessions = new HashMap<String, HttpSession>(); 
   
    @Override
    public void sessionCreated(HttpSessionEvent event) {
    	System.out.println("Session Created "+event.getSession().getId());
        sessions.put(event.getSession().getId(), event.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    	System.out.println("Destroyed Event "+event.getSession().getId());
        sessions.remove(event.getSession().getId());
    }

    public static Map<String, HttpSession> getSessions() {
		return sessions;
	}

	public static void setSessions(Map<String, HttpSession> sessions) {
		SessionManager.sessions = sessions;
	}

	public static boolean invalidate(String sessionId) {
        HttpSession session = sessions.get(sessionId);
        System.out.println("Session invalidate "+sessionId);
        System.out.println("Session invalidate value "+session.getId());
        if (session != null) {
            session.invalidate();
            sessions.remove(sessionId);
            return true;
        } else {
            return false;
        }
    }
	
}