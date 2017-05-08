package com.bitbosh.dropwizardheroku.webgateway.views;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class IndexViewUnitTest {

	@Test
	public void viewReturnsCorrectComponentAfterConstruction() {
		String expectedComponent = "testComponent";
		IndexView view = new IndexView(expectedComponent);
		String actualComponent = view.getEventsComponent();
		assertEquals(expectedComponent, actualComponent);
	}
}
