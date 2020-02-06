package com.proptiger.urlShortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;

import com.proptiger.urlShortener.dao.ShortUrlDao;
import com.proptiger.urlShortener.exception.NotFoundException;
import com.proptiger.urlShortener.model.LongUrl;
import com.proptiger.urlShortener.model.ShortUrl;

@Controller
public class CacheSupport {
	
	@Autowired
	private ShortUrlDao shortUrlDao;
	
	/* find long-URL in cache or database */
	
	@Cacheable(value = "UrlCache", unless = "#result == null")	// returns long-URL if available in cache
	public String findByShortUrl(String shortUrlString) throws NotFoundException {
		System.out.println("short-URL is not present in cache");
		/* short-URL is not present in cache */
		
		 LongUrl findLongUrl = null;
	 
		 ShortUrl shortUrl = shortUrlDao.findOne(shortUrlString);	// find short-URL from database 
		
		 if(shortUrl == null)
			 throw new NotFoundException("URL is not present!");
		 
		 return shortUrl.getLongUrl().getLongUrl();
	}

	/* delete short & long URLs from cache (if present) and database */
	
	@CacheEvict("UrlCache")
	public void deleteUrl(String shortUrl) {
		
		try {
			shortUrlDao.delete(shortUrl);	// delete URL from database
			System.out.println("Deleted short url '"+shortUrl+"' successfully!");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}	
		
	}
}
