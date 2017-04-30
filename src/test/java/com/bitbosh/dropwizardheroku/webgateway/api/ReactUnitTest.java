package com.bitbosh.dropwizardheroku.webgateway.api;

import static org.junit.Assert.assertEquals;

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

public class ReactUnitTest {

	@Test
	public void renderComponent_returnsCorrectHtml_IfInvokedUsingValidScriptEngine() throws NoSuchMethodException, ScriptException {
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
			public Object invokeFunction(String name, Object[] args){
				return expectedHtml;
			}

		};

		NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");

		React react = new React(engine);
		String actualHtml = react.renderComponent("testFunction", props);
		assertEquals(expectedHtml, actualHtml);
	}

	@Test(expected = IllegalStateException.class)
	public void renderComponent_throwsIllegalStateException_IfRenderComponentFunctionFails() {

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
		
		NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
		React react = new React(engine);		
		react.renderComponent(null, null);
	}
}
