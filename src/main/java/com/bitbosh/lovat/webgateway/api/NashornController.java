package com.bitbosh.lovat.webgateway.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.script.ScriptException;

import jdk.nashorn.api.scripting.NashornScriptEngine;

public class NashornController {

	private final NashornScriptEngine nashorn;

	public NashornController(NashornScriptEngine nashorn) {
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

	public String renderReactJsComponent(String functionName) {
		try {
			Object html = nashorn.invokeFunction(functionName);
			return String.valueOf(html);
		} catch (Exception e) {
			throw new IllegalStateException("failed to render react component", e);
		}
	}

	public String renderReactJsComponent(String functionName, List<Object> props) {
		try {
			Object html = nashorn.invokeFunction(functionName, props);
			return String.valueOf(html);
		} catch (Exception e) {
			throw new IllegalStateException("failed to render react component", e);
		}
	}

	public String renderReactJsComponent(String functionName, List<Object> propsOne, List<Object> propsTwo) {
		try {
			Object html = nashorn.invokeFunction(functionName, propsOne, propsTwo);
			return String.valueOf(html);
		} catch (Exception e) {
			throw new IllegalStateException("failed to render react component", e);
		}
	}

	private Reader read(String path) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(path);
		if (in == null)
			System.out.println("InputString in is null with path " + path);
		return new InputStreamReader(in);
	}

	public String renderReactJsComponent(String functionName, List<Object>... props) {
		try {
			Object html = nashorn.invokeFunction(functionName, props);
			return String.valueOf(html);
		} catch (Exception e) {
			throw new IllegalStateException("failed to render react component", e);
		}
	}
}