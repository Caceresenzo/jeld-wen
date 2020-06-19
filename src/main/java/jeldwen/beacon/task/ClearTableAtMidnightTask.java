package jeldwen.beacon.task;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jeldwen.beacon.controller.BeaconLineRestController;
import jeldwen.beacon.model.BeaconReport;
import jeldwen.beacon.repository.BeaconLineRepository;
import jeldwen.beacon.repository.BeaconReportRepository;
import jeldwen.beacon.service.BeaconFileManager;

@Component
public class ClearTableAtMidnightTask {
	
	/* Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(ClearTableAtMidnightTask.class);
	
	/* Repository */
	@Autowired
	private BeaconLineRepository lineRepository;
	
	@Autowired
	private BeaconReportRepository reportRepository;
	
	/* Service */
	@Autowired
	private BeaconFileManager manager;
	
	/* Rest Controller */
	@Autowired
	private BeaconLineRestController beaconLineRestController;
	
	/* see https://www.freeformatter.com/cron-expression-generator-quartz.html */
	@Scheduled(cron = "0 0 0 ? * *")
	public void doClearingTask() throws Exception {
		long count = lineRepository.count();
		
		if (count == 0) {
			LOGGER.info("Skipping midnight clearing (no item).");
		} else {
			LOGGER.info("Executing midnight clearing... (count: {})", count);
			
			Date date = new Date(System.currentTimeMillis() - (1000 * 3600));
			String autoName = date + " midnight";
			
			doReport(date, autoName, true, true);
		}
	}
	
	public BeaconReport doReport(Date date, String displayName, boolean clearToo, boolean atMidnight) throws IOException {
		File file = File.createTempFile(date + "-", ".csv", manager.getStorageDirectory());
		
		FileUtils.writeStringToFile(file, beaconLineRestController.current(), StandardCharsets.UTF_8);
		
		BeaconReport report = new BeaconReport(0, date, displayName, file.getName(), atMidnight);
		reportRepository.save(report);
		
		if (clearToo) {
			lineRepository.deleteAll();
		}
		
		return report;
	}
	
}