package com.proptiger.urlShortener.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proptiger.urlShortener.dao.DailyReportDao;
import com.proptiger.urlShortener.dao.LongUrlDao;
import com.proptiger.urlShortener.dao.ShortUrlDao;
import com.proptiger.urlShortener.model.DailyReport;
import com.proptiger.urlShortener.model.LongUrl;
import com.proptiger.urlShortener.model.ShortUrl;

@Service
public class UrlService {

	@Autowired
	private LongUrlDao 						longUrlDao;
	
	@Autowired
	private ShortUrlDao 					shortUrlDao;
	
	@Autowired
	private DailyReportDao					dailyReportDao;
	
	@Autowired
	private CacheSupport 					cacheSupport;
	
	@Autowired
	private GenerateShortUrl 				generateShortUrl;
	
	@Autowired
	private UpdateDailyReport				updateDailyReport;
	
	/** create a short-URL given long-URL **/
	
	public String createUrl(LongUrl longUrl) {
		
		/** If given long-URL is already present, returns short-URL **/
		
		try {

			LongUrl longUrlIsPresent = longUrlDao.findByLongUrl(longUrl.getLongUrl());
			return	shortUrlDao.findByLongUrl(longUrlIsPresent).getShortUrl(); 
		}
		catch (Exception e) {
			System.err.println("Error Message: " + e);
		}
		
		/** Long-URL is not present **/
		
		LongUrl longUrlSaved = longUrlDao.save(longUrl);	// save long-URL in database
		long id = longUrlSaved.getId();
		ShortUrl shortUrl = new ShortUrl();
		String shortUrlString = generateShortUrl.generateShortUrlFromId(id);	// generate short-URL from id
		shortUrl.setShortUrl(shortUrlString);
		shortUrl.setLongUrl(longUrlSaved);
		shortUrlDao.save(shortUrl);	// save short-URL in database
			
		updateDailyReport.updateUrlCount("create");	// update daily report
		
		return shortUrl.getShortUrl();	
	}

	/** get long-URL given short-URL **/
	
	public String getLongUrl(String shortUrl) {
		
		String[] url = shortUrl.split("/");
		String shortUrlString = url[1];
		String shortUrlIsPresent = cacheSupport.findByShortUrl(shortUrlString);
		
		if(shortUrlIsPresent != null) 
			updateDailyReport.updateUrlCount("hit");	// if short-URL is present in database, update daily report 
		else 
			shortUrlIsPresent = "Short URL is not present!";
		
		return shortUrlIsPresent;
	}
	
	/** delete short-URL and long-URL from database given short-URL **/
	
	public void deleteUrl(String shortUrl) {
		
		String[] url = shortUrl.split("/");
		String shortUrlString = url[1];
		cacheSupport.deleteUrl(shortUrlString);
	}
	
	/** returns number of URLs created between given range of dates **/
	
	public BigDecimal getCreatedUrlCountInRange(String startDateString, String endDateString) {
	  
		BigDecimal urlCountInRange = null;

		Date startDate = parseStringToDate(startDateString);	// parse given string to date 
		Date endDate = parseStringToDate(endDateString);
	
		urlCountInRange = dailyReportDao.countCreatedUrlByDateBetween(startDate, endDate);
		
		return urlCountInRange; 
	}
	
	/** returns number of URLs hit between given range of dates **/
	
	public BigDecimal getHitUrlCountInRange(String startDateString, String endDateString) {
		  
		BigDecimal urlCountInRange = null;

		Date startDate = parseStringToDate(startDateString);
		Date endDate = parseStringToDate(endDateString);

		urlCountInRange = dailyReportDao.countHitUrlByDateBetween(startDate, endDate);
		
		return urlCountInRange; 
	}
	
	public Date parseStringToDate(String dateString) {
		
		Date date = null;
		
		try {
			 date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
		}
		catch (Exception exception) {
			System.err.println(exception);
		}
		return date;
	}
}
