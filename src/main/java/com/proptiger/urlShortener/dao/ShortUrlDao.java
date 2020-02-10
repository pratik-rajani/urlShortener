package com.proptiger.urlShortener.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proptiger.urlShortener.model.LongUrl;
import com.proptiger.urlShortener.model.ShortUrl;

@Repository
public interface ShortUrlDao extends JpaRepository<ShortUrl, Long>{

	public ShortUrl findByLongUrl(LongUrl longUrl);
	
	@Query(value = "select * from URL_shortener.short_url as s where s.short_url = ?1", nativeQuery = true)
	public ShortUrl findByShortUrl(String shortUrl);
	
	@Modifying
	@Transactional
	@Query(value = "delete from URL_shortener.short_url where URL_shortener.short_url.short_url = ?1", nativeQuery = true)
	public void deleteByShortUrl(String shortUrl);
	 
}
