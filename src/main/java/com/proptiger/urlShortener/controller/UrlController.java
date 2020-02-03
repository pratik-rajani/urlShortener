package com.proptiger.urlShortener.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proptiger.urlShortener.model.LongUrl;
import com.proptiger.urlShortener.model.ShortUrl;
import com.proptiger.urlShortener.service.UrlService;

@Controller
public class UrlController {

	@Autowired
	private UrlService urlService;

	@RequestMapping(value = "/ping")
	@ResponseBody
	public String ping() {
		return "pong";
	}

	@RequestMapping(value = "v0/url", method = RequestMethod.POST)
	@ResponseBody
	public String createUrl(@RequestBody LongUrl longUrl) {
		return urlService.createUrl(longUrl);
	}

	@RequestMapping(value = "v0/url", method = RequestMethod.GET)
	@ResponseBody
	public String getLongUrl(@RequestParam(required = true) String shortUrl) {
		return urlService.getLongUrl(shortUrl);
	}
	
	@RequestMapping(value = "v0/url/delete")
	@ResponseBody
	public void deleteUrl(@RequestParam(required = true) String shortUrl) {
		urlService.deleteUrl(shortUrl);
	}
	
	@RequestMapping(value = "v0/url/createcount", method = RequestMethod.GET)
	@ResponseBody 
	public BigDecimal getUrlCreatedCountInRange(@RequestParam(required = true) String startDate, @RequestParam String endDate) { 
		return urlService.getCreatedUrlCountInRange(startDate, endDate); 
	}
	 
	@RequestMapping(value = "v0/url/hitcount", method = RequestMethod.GET)
	@ResponseBody 
	public BigDecimal getUrlHitCountInRange(@RequestParam(required = true) String startDate, @RequestParam String endDate) { 
		return urlService.getHitUrlCountInRange(startDate, endDate); 
	}
}
