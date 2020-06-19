package jeldwen.beacon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jeldwen.beacon.model.BeaconReport;

@Repository()
public interface BeaconReportRepository extends JpaRepository<BeaconReport, Integer> {

}