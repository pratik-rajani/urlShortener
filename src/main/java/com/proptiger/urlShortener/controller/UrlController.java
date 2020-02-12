package com.proptiger.urlShortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proptiger.urlShortener.model.DailyReport;
import com.proptiger.urlShortener.model.LongUrl;
import com.proptiger.urlShortener.model.ShortUrl;
import com.proptiger.urlShortener.service.ResponseUrl;
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
	public ResponseUrl createUrl(@RequestBody LongUrl longUrl) {
		return urlService.createUrl(longUrl);
	}

	@RequestMapping(value = "v0/url", method = RequestMethod.GET)
	@ResponseBody
	public ResponseUrl getLongUrl(@RequestParam(required = true) String shortUrl) {
		return urlService.getLongUrl(shortUrl);
	}
	
	@RequestMapping(value = "v0/url", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteUrl(@RequestParam(required = true) String shortUrl) {
		return urlService.deleteUrl(shortUrl);
	}
	
	@RequestMapping(value = "v0/url/dailyreport", method = RequestMethod.GET)
	@ResponseBody 
	public DailyReport dailyReportInRange(@RequestParam(required = true) String startDate, @RequestParam String endDate) { 
		return urlService.dailyReportInRange(startDate, endDate); 
	}
}
