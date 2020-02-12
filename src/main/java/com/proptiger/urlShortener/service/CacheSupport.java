package com.proptiger.urlShortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;

import com.proptiger.urlShortener.dao.LongUrlDao;
import com.proptiger.urlShortener.dao.ShortUrlDao;
import com.proptiger.urlShortener.exception.NotFoundException;
import com.proptiger.urlShortener.model.LongUrl;
import com.proptiger.urlShortener.model.ShortUrl;

@Controller
public class CacheSupport {
	
	@Autowired
	private ShortUrlDao shortUrlDao;
	
	/* find long-URL in cache or database */
	
	@Cacheable(value = "UrlCache", key = "#shortUrlString", unless = "#result == null")	// returns long-URL if available in cache
	public String findByShortUrl(String shortUrlString, String domain) throws NotFoundException {
		System.out.println("short-URL is not present in cache");
		/* short-URL is not present in cache */
			
		 ShortUrl shortUrl = shortUrlDao.findByShortUrl(shortUrlString);	// find short-URL from database 
		 
		 if(shortUrl == null || !shortUrlString.equals(shortUrl.getShortUrl()))
			 throw new NotFoundException("URL is not present!");
		 
		 LongUrl longUrl = shortUrl.getLongUrl();
		 
		 if(!domain.equals(longUrl.getDomain()))
			 throw new NotFoundException("Domain does not match!");
		 
		 return longUrl.getLongUrl();
	}

	/* delete short & long URLs from cache (if present) and database */
	
	@CacheEvict(value = "UrlCache", key = "#shortUrlString")
	public String deleteUrl(String shortUrlString, String domain) {
		
		  try { 
			  ShortUrl shortUrl = shortUrlDao.findOne(shortUrlString);
			  if(!domain.equals(shortUrl.getLongUrl().getDomain()))
				  return "Domain does not match!";
			  shortUrlDao.delete(shortUrl);
			  return "Deleted short url '"+domain+"/"+shortUrlString+"' successfully!"; 
		  } 
		  catch (Exception e) { 
			  return "URL not found!";
		  }	
	}
}
