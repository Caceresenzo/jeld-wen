package jeldwen.gather.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jeldwen.gather.repository.BeaconLineRepository;

@Component
public class ClearTableAtMidnightTask {
	
	/* Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(ClearTableAtMidnightTask.class);
	
	/* Repository */
	@Autowired
	private BeaconLineRepository repository;
	
	/* see https://www.freeformatter.com/cron-expression-generator-quartz.html */
	@Scheduled(cron = "0 0 0 ? * *")
	public void doClearingTask() {
		long count = repository.count();
		
		if (count == 0) {
			LOGGER.info("Skipping midnight clearing (no item).");
		} else {
			LOGGER.info("Executing midnight clearing... (count: {})", count);
			repository.deleteAll();
		}
	}
	
}