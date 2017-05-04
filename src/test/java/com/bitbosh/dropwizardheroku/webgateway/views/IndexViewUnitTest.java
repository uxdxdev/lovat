package com.bitbosh.dropwizardheroku.webgateway.views;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class IndexViewUnitTest {

	@Test
	public void viewReturnsCorrectComponentAfterConstruction() {
		String expectedComponent = "testComponent";
		List<Object> props = new ArrayList<Object>();
		IndexView view = new IndexView(expectedComponent, props);
		String actualComponent = view.getEventsComponent();
		assertEquals(expectedComponent, actualComponent);
	}
	
	@Test
	public void viewReturnsCorrectPropsAfterConstruction() {
		String testComponent = "testComponent";
		List<Object> expectedProps = new ArrayList<Object>();		
		IndexView view = new IndexView(testComponent, expectedProps);
		List<Object> actualProps = view.getEventsList();
		assertEquals(expectedProps, actualProps);
	}
}
