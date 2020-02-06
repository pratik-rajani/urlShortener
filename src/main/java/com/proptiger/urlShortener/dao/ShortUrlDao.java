package com.proptiger.urlShortener.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proptiger.urlShortener.model.LongUrl;
import com.proptiger.urlShortener.model.ShortUrl;

@Repository
public interface ShortUrlDao extends JpaRepository<ShortUrl, String>{

	//@Query("select u from short_url u where u.url_id = ?1")
	public ShortUrl findByLongUrl(LongUrl longUrl);
}
