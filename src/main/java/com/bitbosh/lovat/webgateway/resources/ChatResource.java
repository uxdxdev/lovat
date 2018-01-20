package com.bitbosh.lovat.webgateway.resources;

import com.bitbosh.lovat.webgateway.auth.CustomAuthenticator;
import com.bitbosh.lovat.webgateway.core.Message;
import com.bitbosh.lovat.webgateway.core.MessageDecoder;
import com.bitbosh.lovat.webgateway.core.MessageEncoder;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

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
        Random rand = new Random();
        int pickedNumber = rand.nextInt(1000) + 1;
        if (loggedInUsername.equals("anon")) {
            loggedInUsername += "#" + pickedNumber;
        }
        users.put(session.getId(), loggedInUsername);
        this.session = session;
        chatConnections.add(this);
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
        users.remove(session.getId());
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
