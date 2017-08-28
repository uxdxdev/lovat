package com.bitbosh.lovat.webgateway.core;

import java.io.IOException;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageDecoder implements Decoder.Text<Message> {

	@Override
	public Message decode(String messageString) throws DecodeException {
		ObjectMapper mapper = new ObjectMapper();
		Message message = new Message();
		try {
			message = mapper.readValue(messageString, Message.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public boolean willDecode(String s) {		
		return (s != null);
	}

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
