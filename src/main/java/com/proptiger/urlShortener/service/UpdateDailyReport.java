package com.proptiger.urlShortener.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proptiger.urlShortener.dao.DailyReportDao;
import com.proptiger.urlShortener.model.DailyReport;

@Service
public class UpdateDailyReport {
	
	@Autowired
	private DailyReportDao 			dailyReportDao;
	
	public void updateUrlCount(String type) {

		DailyReport dailyReport = findOrCreateDailyReport();
		
		if(type.equalsIgnoreCase("create")) {
			dailyReport.setCreatedUrlCount(dailyReport.getCreatedUrlCount()+1);
		}
		else if(type.equalsIgnoreCase("hit"))
			dailyReport.setHitUrlCount(dailyReport.getHitUrlCount()+1);
		
		dailyReportDao.save(dailyReport);
	}
	
	private DailyReport findOrCreateDailyReport() {

		DailyReport dailyReport = null;
		Date currentDate = getCurrentDate();
		
		if(dailyReportDao.exists(currentDate)) {
			dailyReport = dailyReportDao.findOne(currentDate);
		}
		else {
			dailyReport = new DailyReport();
			dailyReport.setDate(currentDate);
		}
		return dailyReport;
	}
	
	private Date getCurrentDate() {
		
		Date currentDate = null;
		
		try {

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
			currentDate = formatter.parse(formatter.format(new Date()));
		}
		catch (Exception e) {
			System.err.println(e);
		}
		
		return currentDate;
	}
}
