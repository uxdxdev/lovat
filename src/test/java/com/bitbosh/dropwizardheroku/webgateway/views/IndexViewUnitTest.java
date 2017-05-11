package com.bitbosh.dropwizardheroku.webgateway.views;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class IndexViewUnitTest {

	@Test
	public void viewReturnsCorrectComponentAfterConstruction() {
		String expectedCreateEventComponent = "testCreateEventComponent";
		String expectedEventListComponent = "testEventsListComponent";
		
		IndexView view = new IndexView(expectedCreateEventComponent, expectedEventListComponent);
		String actualCreateEventComponent = view.getCreateEventFormComponent();
		String actualEventsListComponent = view.getEventsListComponent();
		assertEquals(expectedCreateEventComponent, actualCreateEventComponent);
		assertEquals(expectedEventListComponent, actualEventsListComponent);
	}
}
