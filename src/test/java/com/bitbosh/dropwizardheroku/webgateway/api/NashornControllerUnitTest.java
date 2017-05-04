package com.bitbosh.dropwizardheroku.webgateway.api;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.script.AbstractScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import mockit.Mock;
import mockit.MockUp;

public class NashornControllerUnitTest {

	@Test
	public void nashornInvokeFunctionMethodCalledDuringReactJsComponentRendering()
			throws NoSuchMethodException, ScriptException {
		String expectedHtml = "<div>test</div>";
		List<Object> props = new ArrayList<Object>();

		new MockUp<AbstractScriptEngine>() {

			@Mock
			public Object eval(String script) throws ScriptException {
				return null;
			}

			@Mock
			public Object eval(Reader reader) throws ScriptException {
				return null;
			}

		};

		new MockUp<NashornScriptEngine>() {

			@Mock
			public Object invokeFunction(String name, Object[] args) {
				return expectedHtml;
			}

		};

		new MockUp<InputStreamReader>() {

			@Mock
			public void $init(InputStream in) {
			}
		};

		NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
		NashornController nashornController = new NashornController(engine);

		String actualHtml = nashornController.renderReactJsComponent("testFunction", props);
		assertEquals(expectedHtml, actualHtml);
	}

	@Test(expected = IllegalStateException.class)
	public void illegalStateExceptionThrownIfReactJsFunctionNull() {

		new MockUp<AbstractScriptEngine>() {

			@Mock
			public Object eval(String script) throws ScriptException {
				return null;
			}

			@Mock
			public Object eval(Reader reader) throws ScriptException {
				return null;
			}

		};

		new MockUp<InputStreamReader>() {

			@Mock
			public void $init(InputStream in) {
			}
		};

		NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
		NashornController react = new NashornController(engine);
		List<Object> props = new ArrayList<Object>();
		react.renderReactJsComponent(null, props);
	}

	@Test(expected = IllegalStateException.class)
	public void illegalStateExceptionThrownIfReactJsPropsNull() {

		new MockUp<AbstractScriptEngine>() {

			@Mock
			public Object eval(String script) throws ScriptException {
				return null;
			}

			@Mock
			public Object eval(Reader reader) throws ScriptException {
				return null;
			}

		};

		new MockUp<InputStreamReader>() {

			@Mock
			public void $init(InputStream in) {
			}
		};

		NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
		NashornController react = new NashornController(engine);
		List<Object> props = new ArrayList<Object>();
		react.renderReactJsComponent(null, props);
	}

	@Test(expected = RuntimeException.class)
	public void runtimeExceptionThrownIfNashornEngineNull() {
		NashornController react = new NashornController(null);
	}

	@Test(expected = RuntimeException.class)
	public void scriptExceptionThrownIfJsScriptInvalid() {
		
		new MockUp<AbstractScriptEngine>() {

			@Mock
			public Object eval(String script) throws ScriptException {
				return null;
			}

			@Mock
			public Object eval(Reader reader) throws ScriptException {
				throw new ScriptException("test");
			}

		};
		
		new MockUp<InputStreamReader>() {
			@Mock
			public void $init(InputStream in) {				
			}
		};

		NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
		NashornController nashornController = new NashornController(engine);
	}

}
