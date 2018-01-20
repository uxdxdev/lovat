package com.bitbosh.lovat.webgateway.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.bitbosh.lovat.webgateway.auth.CustomAuthenticator;
import com.bitbosh.lovat.webgateway.core.Message;
import com.bitbosh.lovat.webgateway.core.MessageDecoder;
import com.bitbosh.lovat.webgateway.core.MessageEncoder;

@ServerEndpoint(value = "/chat",
		decoders = MessageDecoder.class, 
		encoders = MessageEncoder.class)
public class ChatResource {
	
	private Session session;
	private static final Set<ChatResource> chatConnections = new CopyOnWriteArraySet<>();
	private static HashMap<String, String> users = new HashMap<>();
	
	@OnOpen
	public void onOpen(Session session) {

	    String loggedInUsername = CustomAuthenticator.getLoggedInUsername();
		this.session = session;
		chatConnections.add(this);
		users.put(session.getId(), loggedInUsername);
	}
	
	@OnMessage
	public void onMessage(Session session, Message message) {
		message.setFrom(users.get(session.getId()));		
		broadcast(message);
	}
	
	@OnClose
	public void onClose(Session session){
		chatConnections.remove(this);
		Message message = new Message();
		message.setFrom(users.get(session.getId()));
		message.setContent("Disconnected!");
		broadcast(message);
	}
	
	@OnError
	public void onError(Session session, Throwable throwable) {
		System.out.println("onError");
	}
	
	private static void broadcast(Message message) {
		chatConnections.forEach(endpoint -> {
			synchronized (endpoint) {
				try {
					endpoint.session.getBasicRemote().sendObject(message);
				} catch (IOException | EncodeException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
