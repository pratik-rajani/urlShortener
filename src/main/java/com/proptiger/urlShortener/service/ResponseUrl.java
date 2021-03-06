package com.proptiger.urlShortener.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Service
@JsonInclude(Include.NON_NULL)
public class ResponseUrl {

	private String responseUrl;

	public String getResponseUrl() {
		return responseUrl;
	}

	public void setResponseUrl(String responseUrl) {
		this.responseUrl = responseUrl;
	}
}
