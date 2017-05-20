package com.bitbosh.dropwizardheroku.webgateway.views;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class IndexViewUnitTest {

	@Test
	public void viewReturnsCorrectComponentAfterConstruction() {
		String expectedHtml = "<dev>testHtml</div>";
		
		IndexView view = new IndexView(expectedHtml);
		String actualHtml = view.getIndexViewHtml();
		assertEquals(expectedHtml, actualHtml);		
	}
}
