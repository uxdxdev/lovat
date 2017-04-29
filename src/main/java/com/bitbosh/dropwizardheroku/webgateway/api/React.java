package com.bitbosh.dropwizardheroku.webgateway.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.NashornScriptEngine;

public class React {
              
	private final NashornScriptEngine nashorn;
	
	public React(NashornScriptEngine nashorn){
		this.nashorn = nashorn;
		try {
			// remote js file loading
        	nashorn.eval("load('https://cdnjs.cloudflare.com/ajax/libs/react/0.14.1/react.js')");
        	nashorn.eval("load('https://cdnjs.cloudflare.com/ajax/libs/react/0.14.1/react-dom.js')");
        	nashorn.eval("load('https://cdnjs.cloudflare.com/ajax/libs/babel-standalone/6.24.0/babel.js')");
        	nashorn.eval("load('https://cdnjs.cloudflare.com/ajax/libs/axios/0.16.1/axios.js')");        	        	
        	nashorn.eval("load('http://localhost:8081/js/events.js')");
        	
        	// local js file loading
        	nashorn.eval(read("assets/js/nashorn-polyfill.js"));
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
	}

    public String renderComponent(List<Object> events) {
        try {
            //Object html = engineHolder.get().invokeFunction("renderEventsComponentServer", events);
        	Object html = nashorn.invokeFunction("renderEventsComponentServer", events);
            return String.valueOf(html);
        }
        catch (Exception e) {
            throw new IllegalStateException("failed to render react component", e);
        }
    }

    private Reader read(String path) {
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        return new InputStreamReader(in);
    }
}