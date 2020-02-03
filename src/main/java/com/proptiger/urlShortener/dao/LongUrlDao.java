package com.proptiger.urlShortener.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proptiger.urlShortener.model.LongUrl;

@Repository
public interface LongUrlDao extends JpaRepository<LongUrl, Long>{
	
	public LongUrl findByLongUrl(String longUrl);
	
}
