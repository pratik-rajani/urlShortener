package com.proptiger.urlShortener.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "daily_report", schema = "URL_shortener")
@JsonInclude(Include.NON_NULL)
public class DailyReport {

	@Id
	private Date date;
	
	@Column(name = "created_url_count")
	private long createdUrlCount;
	
	@Column(name = "hit_url_count")
	private long hitUrlCount;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getCreatedUrlCount() {
		return createdUrlCount;
	}

	public void setCreatedUrlCount(long createdUrlCount) {
		this.createdUrlCount = createdUrlCount;
	}

	public long getHitUrlCount() {
		return hitUrlCount;
	}

	public void setHitUrlCount(long hitUrlCount) {
		this.hitUrlCount = hitUrlCount;
	}
}
