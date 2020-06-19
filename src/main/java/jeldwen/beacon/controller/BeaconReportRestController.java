package jeldwen.beacon.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Date;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.beacon.model.BeaconReport;
import jeldwen.beacon.repository.BeaconLineRepository;
import jeldwen.beacon.repository.BeaconReportRepository;
import jeldwen.beacon.service.BeaconFileManager;
import jeldwen.beacon.task.ClearTableAtMidnightTask;

@RestController()
@RequestMapping("/beacon/reports/")
public class BeaconReportRestController {
	
	/* Repository */
	@Autowired
	private BeaconLineRepository lineRepository;
	
	@Autowired
	private BeaconReportRepository reportRepository;
	
	/* Service */
	@Autowired
	private BeaconFileManager manager;
	
	/* Task */
	@Autowired
	private ClearTableAtMidnightTask midnightTask;
	
	@GetMapping("all")
	public ResponseEntity<List<?>> status() {
		return ResponseEntity.ok(reportRepository.findAll());
	}
	
	@GetMapping(value = "download/{id}")
	public ResponseEntity<FileSystemResource> download(@PathVariable int id) throws IOException {
		Optional<BeaconReport> optional = reportRepository.findById(id);
		
		if (optional.isPresent()) {
			File file = manager.getPathFor(optional.get()).toFile();
			
			HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.setContentType(new MediaType("text", "csv", StandardCharsets.UTF_8));
			respHeaders.setContentLength(file.length());
			respHeaders.setContentDispositionFormData("attachment", file.getName());
			
			return new ResponseEntity<>(new FileSystemResource(file), respHeaders, HttpStatus.OK);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("delete/{id}")
	public ResponseEntity<Map<?, ?>> delete(@PathVariable int id) throws IOException {
		Optional<BeaconReport> optional = reportRepository.findById(id);
		
		if (optional.isPresent()) {
			Files.deleteIfExists(manager.getPathFor(optional.get()));
			
			reportRepository.deleteById(id);
			
			return ResponseEntity.ok(Collections.singletonMap("deleted", true));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("property/{id}")
	public ResponseEntity<Map<?, ?>> propery(@PathVariable int id) {
		Optional<BeaconReport> optional = reportRepository.findById(id);
		
		if (optional.isPresent()) {
			BeaconReport report = optional.get();
			
			Map<Object, Object> response = new LinkedHashMap<>();
			
			response.put("report", report);
			response.put("absolute", manager.getPathFor(report).toAbsolutePath().toString());
			response.put("properties", manager.getPropertyFor(report));
			
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("force")
	public ResponseEntity<Map<?, ?>> forceReport(@RequestParam boolean clearToo, @RequestParam(required = false, defaultValue = "") String displayName) throws IOException {
		Map<Object, Object> response = new LinkedHashMap<>();
		
		if (displayName != null && displayName.isEmpty()) {
			displayName = null;
		}
		
		long count = lineRepository.count();
		
		response.put("count", count);
		response.put("created", false);
		
		if (count != 0) {
			response.put("created", true);
			response.put("report", midnightTask.doReport(new Date(System.currentTimeMillis()), displayName, clearToo, false));
		}
		
		return ResponseEntity.ok(response);
	}
	
}