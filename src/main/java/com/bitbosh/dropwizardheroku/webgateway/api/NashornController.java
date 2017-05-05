package com.bitbosh.dropwizardheroku.webgateway.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.script.ScriptException;

import jdk.nashorn.api.scripting.NashornScriptEngine;

public class NashornController {
              
	private final NashornScriptEngine nashorn;
	
	public NashornController(NashornScriptEngine nashorn){
		this.nashorn = nashorn;
		try {
			nashorn.eval(read("assets/js/react.min.js"));
			nashorn.eval(read("assets/js/react-dom.min.js"));
			nashorn.eval(read("assets/js/react-dom-server.min.js"));
			nashorn.eval(read("assets/js/babel.min.js"));
			nashorn.eval(read("assets/js/axios.min.js"));
			nashorn.eval(read("assets/js/nashorn-polyfill.js"));
        	nashorn.eval(read("assets/js/bundle.js"));        	
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
	}

    public String renderReactJsComponent(String functionName, List<Object> props) {
        try {            
        	Object html = nashorn.invokeFunction(functionName, props);
            return String.valueOf(html);
        }
        catch (Exception e) {
        	e.printStackTrace();
            throw new IllegalStateException("failed to render react component", e);
        }
    }

    private Reader read(String path) {
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        return new InputStreamReader(in);
    }
}