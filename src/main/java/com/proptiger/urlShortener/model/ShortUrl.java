package com.proptiger.urlShortener.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "short_url", schema = "URL_shortener")
public class ShortUrl {
	
	@Id
	@Column(name = "short_url", nullable = false)
	private String shortUrl;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "url_id", nullable = false, unique = true)
	private LongUrl longUrl;

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public LongUrl getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(LongUrl longUrl) {
		this.longUrl = longUrl;
	}
}
