package com.bitbosh.lovat.webgateway.api;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class ApiResponseUnitTest {

	@Test
	public void getEvents_returnsCorrectList_IfInjectedListNotNull() {
		List<String> expectedList = new ArrayList<String>();
		String expectedEntry = "test";
		expectedList.add(expectedEntry);
		ApiResponse response = new ApiResponse(expectedList);
		List<String> actualList = (List<String>) response.getList();
		String actualEntry = actualList.get(0);

		assertEquals(expectedList.size(), actualList.size());
		assertEquals(expectedEntry, actualEntry);
	}

	@Test
	public void getEvents_returnsNull_IfInjectedListNull() {
		List<String> expectedList = null;
		ApiResponse response = new ApiResponse(expectedList);
		List<String> actualList = (List<String>) response.getList();
		assertEquals(expectedList, actualList);
	}
}
