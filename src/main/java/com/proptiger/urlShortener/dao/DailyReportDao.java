package com.proptiger.urlShortener.dao;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proptiger.urlShortener.model.DailyReport;

@Repository
public interface DailyReportDao extends JpaRepository<DailyReport, Date>{
	
	@Query(value = "select sum(URL_shortener.daily_report.created_url_count) from URL_shortener.daily_report where URL_shortener.daily_report.date between ?1 and ?2", nativeQuery = true)
	public BigDecimal countCreatedUrlByDateBetween(Date startDate, Date endDate);
	
	@Query(value = "select sum(URL_shortener.daily_report.hit_url_count) from URL_shortener.daily_report where URL_shortener.daily_report.date between ?1 and ?2", nativeQuery = true)
	public BigDecimal countHitUrlByDateBetween(Date startDate, Date endDate);
	
	/*
	 * @Query(value =
	 * "select URL_shortener.daily_report.date from URL_shortener.daily_report order by date desc limit 1"
	 * , nativeQuery = true) public Date findLatestDate();
	 * 
	 * @Query(value =
	 * "select URL_shortener.daily_report.date from URL_shortener.daily_report order by date limit 1"
	 * , nativeQuery = true) public Date findFirstDate();
	 */
}
