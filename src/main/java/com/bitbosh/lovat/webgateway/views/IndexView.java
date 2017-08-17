package com.bitbosh.lovat.webgateway.views;

import io.dropwizard.views.View;

public class IndexView extends View {

	private final String indexViewHtml;

	public IndexView(String indexViewHtml) {
		super("Index.mustache");
		this.indexViewHtml = indexViewHtml;
	}

	public String getIndexViewHtml() {
		return indexViewHtml;
	}
}
