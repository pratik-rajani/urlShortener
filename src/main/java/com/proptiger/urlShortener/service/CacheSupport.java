package com.proptiger.urlShortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;

import com.proptiger.urlShortener.dao.ShortUrlDao;
import com.proptiger.urlShortener.model.ShortUrl;

@Controller
public class CacheSupport {
	
	@Autowired
	private ShortUrlDao shortUrlDao;
	
	/** find long-URL in cache or database **/
	
	@Cacheable(value = "UrlCache", unless = "#result == null")	// returns long-URL if available in cache
	public String findByShortUrl(String shortUrlString) {
		
		/** short-Url is not present in cache **/
		
		 String findLongUrl = null;
		 
		 try {
			 
			 ShortUrl shortUrl = shortUrlDao.findOne(shortUrlString);	// find short-URL from database 
			 findLongUrl = shortUrl.getLongUrl().getLongUrl();
		 }
		 catch (Exception e) {
			System.err.println("Error Message: " + e);
		}
		return findLongUrl;
	}

	/** delete short & long URLs from cache (if present) and database **/
	
	@CacheEvict("UrlCache")
	public void deleteUrl(String shortUrl) {
		
		try {
			shortUrlDao.delete(shortUrl);	// delete URL from database
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}
}
