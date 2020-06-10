package jeldwen.gather.controller;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.gather.model.BeaconLine;
import jeldwen.gather.repository.BeaconLineRepository;

@RestController()
@RequestMapping("/beacon/")
public class BeaconLineRestController {
	
	/* Repository */
	@Autowired
	private BeaconLineRepository repository;
	
	@GetMapping("add")
	public ResponseEntity<Map<?, ?>> add(BeaconLine beaconLine) {
		repository.save(beaconLine);
		
		return ResponseEntity.ok(Collections.singletonMap("status", beaconLine));
	}
	
	@GetMapping(value = "current", produces = "text/text")
	public String current() {
		return repository
				.findAll()
				.stream()
				.map(BeaconLine::toString)
				.collect(Collectors.joining("\n", BeaconLine.CSV_HEAD + "\n", ""));
	}
	
}