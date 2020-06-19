package jeldwen.beacon.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jeldwen.beacon.model.BeaconFileProperties;
import jeldwen.beacon.model.BeaconReport;

@Service("beacon-file-service")
public class BeaconFileManager {
	
	/* Variables */
	@Value("${app.config.beacon.report.storage:./storage/}")
	private String storageDirectoryPath;
	
	@Value("${app.config.beacon.report.extensions:csv}")
	private String reportExtensions;
	
	/* Constructor */
	private BeaconFileManager() {
		;
	}
	
	@PostConstruct
	private void postConstruct() throws IOException {
		File storageDirectory = new File(storageDirectoryPath);
		
		if (storageDirectory.isFile()) {
			Files.delete(storageDirectory.toPath());
		}
		
		if (!storageDirectory.exists()) {
			storageDirectory.mkdirs();
		}
	}
	
	public Path getPathFor(BeaconReport report) {
		return Paths.get(storageDirectoryPath, new File(report.getFilename()).getName());
	}
	
	public BeaconFileProperties getPropertyFor(BeaconReport report) {
		File file = getPathFor(report).toFile();
		
		if (!file.exists()) {
			return null;
		}
		
		return new BeaconFileProperties(file.getPath(), file.length(), report.getDate());
	}

	public File getStorageDirectory() {
		return new File(storageDirectoryPath);
	}
	
}