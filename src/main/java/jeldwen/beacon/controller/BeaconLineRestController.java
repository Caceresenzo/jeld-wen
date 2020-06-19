package jeldwen.beacon.controller;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.beacon.model.BeaconLine;
import jeldwen.beacon.repository.BeaconLineRepository;

@RestController()
@RequestMapping("/beacon/line/")
public class BeaconLineRestController {
	
	/* Repository */
	@Autowired
	private BeaconLineRepository repository;
	
	@GetMapping("add")
	public ResponseEntity<Map<?, ?>> add(BeaconLine beaconLine) {
		repository.save(beaconLine);
		
		return ResponseEntity.ok(Collections.singletonMap("status", beaconLine));
	}
	
	@GetMapping("status")
	public ResponseEntity<Map<?, ?>> status() {
		Map<Object, Object> response = new LinkedHashMap<>();
		
		response.put("count", repository.count());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("total")
	public ResponseEntity<Map<?, ?>> total() {
		Map<Object, Object> response = new LinkedHashMap<>();
		
		Map<Object, Object> stopMap = new LinkedHashMap<>();
		response.put("stops", stopMap);
		
		long yellowStop = 0, orangeStop = 0, pinkStop = 0, greenStop = 0, blueStop = 0, otherStop = 0, totalStop = 0;
		
		for (BeaconLine line : repository.findAll()) {
			yellowStop += line.getYellowStop().getTotalSeconds();
			orangeStop += line.getOrangeStop().getTotalSeconds();
			pinkStop += line.getPinkStop().getTotalSeconds();
			greenStop += line.getGreenStop().getTotalSeconds();
			blueStop += line.getBlueStop().getTotalSeconds();
			otherStop += line.getOtherStop().getTotalSeconds();
			totalStop += line.getTotalStop().getTotalSeconds();
		}
		
		stopMap.put("yellow", yellowStop);
		stopMap.put("orange", orangeStop);
		stopMap.put("pink", pinkStop);
		stopMap.put("green", greenStop);
		stopMap.put("blue", blueStop);
		stopMap.put("other", otherStop);
		stopMap.put("total", totalStop);
		stopMap.put("unqualified", totalStop - (yellowStop + orangeStop + pinkStop + greenStop + blueStop + otherStop));
		
		return ResponseEntity.ok(response);
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