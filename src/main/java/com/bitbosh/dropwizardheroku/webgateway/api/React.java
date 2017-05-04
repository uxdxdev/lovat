package com.bitbosh.dropwizardheroku.webgateway.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.script.ScriptException;

import com.bitbosh.dropwizardheroku.webgateway.core.Microservice;

import jdk.nashorn.api.scripting.NashornScriptEngine;

public class React {
              
	private final NashornScriptEngine nashorn;
	
	public React(NashornScriptEngine nashorn){
		this.nashorn = nashorn;
		try {
			nashorn.eval(read("assets/js/react.min.js"));
			nashorn.eval(read("assets/js/react-dom.min.js"));
			nashorn.eval(read("assets/js/react-dom-server.min.js"));
			nashorn.eval(read("assets/js/babel.min.js"));
			nashorn.eval(read("assets/js/axios.min.js"));        	        	        	        	        	 
        	nashorn.eval(read(Microservice.kEventsUiComponentUrl));
        	nashorn.eval(read("assets/js/nashorn-polyfill.js"));
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
	}

    public String renderComponent(String component, List<Object> props) {
        try {            
        	Object html = nashorn.invokeFunction(component, props);
            return String.valueOf(html);
        }
        catch (Exception e) {
            throw new IllegalStateException("failed to render react component", e);
        }
    }

    public Reader read(String path) {
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        return new InputStreamReader(in);
    }
    
    String convertReaderToString(Reader reader) throws IOException {
    	BufferedReader str = new BufferedReader(reader);
        return str.toString();
    }
}