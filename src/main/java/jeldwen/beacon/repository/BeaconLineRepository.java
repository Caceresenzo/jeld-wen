package jeldwen.beacon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jeldwen.beacon.model.BeaconLine;

@Repository()
public interface BeaconLineRepository extends JpaRepository<BeaconLine, Integer> {

}