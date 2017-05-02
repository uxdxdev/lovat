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
			// remote js file loading
        	nashorn.eval("load('https://cdnjs.cloudflare.com/ajax/libs/react/15.5.4/react.js')");
        	nashorn.eval("load('https://cdnjs.cloudflare.com/ajax/libs/react/15.5.4/react-dom.js')");
        	nashorn.eval("load('https://cdnjs.cloudflare.com/ajax/libs/react/15.5.4/react-dom-server.js')");
        	nashorn.eval("load('https://cdnjs.cloudflare.com/ajax/libs/babel-standalone/6.24.0/babel.js')");
        	nashorn.eval("load('https://cdnjs.cloudflare.com/ajax/libs/axios/0.16.1/axios.js')");        	        	        	
        	
        	
        	// local js file loading
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