package com.proptiger.urlShortener.service;

import org.springframework.stereotype.Service;

@Service
public class ResponseUrl {

	private String responseUrl;

	public String getResponseUrl() {
		return responseUrl;
	}

	public void setResponseUrl(String responseUrl) {
		this.responseUrl = responseUrl;
	}
}
