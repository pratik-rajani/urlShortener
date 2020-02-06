package com.proptiger.urlShortener.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proptiger.urlShortener.dao.DailyReportDao;
import com.proptiger.urlShortener.dao.LongUrlDao;
import com.proptiger.urlShortener.dao.ShortUrlDao;
import com.proptiger.urlShortener.exception.NotFoundException;
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
	
	@Autowired
	private ResponseUrl						responseUrl;
	
	/* create a short-URL given long-URL */
	
	public ShortUrl createUrl(LongUrl longUrl) {
		
		/* If given long-URL is already present, returns short-URL */
		
		LongUrl longUrlIsPresent = longUrlDao.findByLongUrl(longUrl.getLongUrl());
		
		if(longUrlIsPresent != null)
			return	shortUrlDao.findByLongUrl(longUrlIsPresent); 
		
		/* Long-URL is not present */
		
		LongUrl longUrlSaved = longUrlDao.save(longUrl);	// save long-URL in database
		long id = longUrlSaved.getId();		
		String shortUrlString = generateShortUrl.generateShortUrlFromId(id);	// generate short-URL from id
		ShortUrl shortUrl = new ShortUrl();
		shortUrl.setShortUrl(shortUrlString);
		shortUrl.setLongUrl(longUrlSaved);
		shortUrlDao.save(shortUrl);	// save short-URL in database
			
		updateDailyReport.updateUrlCount("create");	// update daily report
		
		return shortUrl;	
	}

	/* get long-URL given short-URL */
	
	public ResponseUrl getLongUrl(String shortUrl) {
		
		String[] url = shortUrl.split("/");
		String shortUrlString = url[1];
		String shortUrlIsPresent = null;
		
		try {

			shortUrlIsPresent = cacheSupport.findByShortUrl(shortUrlString);			
			updateDailyReport.updateUrlCount("hit");	// if short-URL is present in database, update daily report 

		}
		catch (NotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		responseUrl.setResponseUrl(shortUrlIsPresent);
		return responseUrl;
	}
	
	/* delete short-URL and long-URL from database given short-URL */
	
	public void deleteUrl(String shortUrl) {
		
		String[] url = shortUrl.split("/");
		String shortUrlString = url[1];
		cacheSupport.deleteUrl(shortUrlString);
	}
	
	/* returns number of URLs created between given range of dates */
	
	public Long getCreatedUrlCountInRange(String startDateString, String endDateString) {
	  
		long urlCountInRange = 0;

		Date startDate = parseStringToDate(startDateString);	// parse given string to date 
		Date endDate = parseStringToDate(endDateString);
		
		if(!isValidDates(startDate, endDate))
			return -1L;
		
		urlCountInRange = dailyReportDao.countCreatedUrlByDateBetween(startDate, endDate).longValue();
		
		return urlCountInRange; 
	}
	
	/* returns number of URLs hit between given range of dates */
	
	public Long getHitUrlCountInRange(String startDateString, String endDateString) {
		  
		long urlCountInRange = 0;

		Date startDate = parseStringToDate(startDateString);
		Date endDate = parseStringToDate(endDateString);
		
		if(!isValidDates(startDate, endDate)) 
			return -1L;

		urlCountInRange = dailyReportDao.countHitUrlByDateBetween(startDate, endDate).longValue();
		
		return urlCountInRange; 
	}
	
	private Date parseStringToDate(String dateString) {
		
		Date date = null;
		
		try {
			 date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
		}
		catch (Exception exception) {
			System.err.println(exception);
		}
		return date;
	}
	
	private boolean isValidDates(Date startDate, Date endDate) {
		
		Date latestDate = dailyReportDao.findLatestDate();	// database does not have entry after latest date
		
		if(startDate.after(endDate))	// start date should be before end date
			return false;
		
		if(startDate.after(latestDate))	// start date should be before latest date
			return false;
		
		return true;
	}
}
