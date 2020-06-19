package jeldwen.beacon.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jeldwen.common.convert.hibernate.CustomDateAttributeConverter;
import jeldwen.common.convert.hibernate.SimpleDurationAttributeConverter;
import jeldwen.common.type.CustomDate;
import jeldwen.common.type.SimpleDuration;

@Entity()
@Table(name = "beacons")
public class BeaconLine {
	
	/* Constants */
	public static final String CSV_HEAD = "Date;Heure fin;Machine;Poduction;Objectif;Arret jaune;Arret orange;Arret rose;Arret vert;Arret bleu;Arret autre;Total arret";
	
	/* Variables */
	@Id()
	@GeneratedValue()
	@JsonIgnore()
	@Column()
	private int id;
	
	@Convert(converter = CustomDateAttributeConverter.class)
	@Column()
	private CustomDate date;
	
	@Convert(converter = SimpleDurationAttributeConverter.class)
	@Column()
	private SimpleDuration endHour;

	@Column()
	private String machine;

	@Column()
	private long production;

	@Column()
	private long objective;
	
	@Convert(converter = SimpleDurationAttributeConverter.class)
	@Column()
	private SimpleDuration yellowStop;
	
	@Convert(converter = SimpleDurationAttributeConverter.class)
	@Column()
	private SimpleDuration orangeStop;
	
	@Convert(converter = SimpleDurationAttributeConverter.class)
	@Column()
	private SimpleDuration pinkStop;
	
	@Convert(converter = SimpleDurationAttributeConverter.class)
	@Column()
	private SimpleDuration greenStop;
	
	@Convert(converter = SimpleDurationAttributeConverter.class)
	@Column()
	private SimpleDuration blueStop;
	
	@Convert(converter = SimpleDurationAttributeConverter.class)
	@Column()
	private SimpleDuration otherStop;
	
	@Convert(converter = SimpleDurationAttributeConverter.class)
	@Column()
	private SimpleDuration totalStop;
	
	/* Constructor */
	public BeaconLine() {
		this(0, CustomDate.ZERO, SimpleDuration.ZERO, "", 0, 0, SimpleDuration.ZERO, SimpleDuration.ZERO, SimpleDuration.ZERO, SimpleDuration.ZERO, SimpleDuration.ZERO, SimpleDuration.ZERO, SimpleDuration.ZERO);
	}

	/* Constructor */
	public BeaconLine(int id, CustomDate date, SimpleDuration endHour, String machine, long production, long objective, SimpleDuration yellowStop, SimpleDuration orangeStop, SimpleDuration pinkStop, SimpleDuration greenStop, SimpleDuration blueStop, SimpleDuration otherStop, SimpleDuration totalStop) {
		super();
		
		this.id = id;
		this.date = date;
		this.endHour = endHour;
		this.machine = machine;
		this.production = production;
		this.objective = objective;
		this.yellowStop = yellowStop;
		this.orangeStop = orangeStop;
		this.pinkStop = pinkStop;
		this.greenStop = greenStop;
		this.blueStop = blueStop;
		this.otherStop = otherStop;
		this.totalStop = totalStop;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public CustomDate getDate() {
		return date;
	}
	
	public void setDate(CustomDate date) {
		this.date = date;
	}
	
	public SimpleDuration getEndHour() {
		return endHour;
	}
	
	public void setEndHour(SimpleDuration endHour) {
		this.endHour = endHour;
	}
	
	public String getMachine() {
		return machine;
	}
	
	public void setMachine(String machine) {
		this.machine = machine;
	}
	
	public long getProduction() {
		return production;
	}
	
	public void setProduction(long production) {
		this.production = production;
	}
	
	public long getObjective() {
		return objective;
	}
	
	public void setObjective(long objective) {
		this.objective = objective;
	}
	
	public SimpleDuration getYellowStop() {
		return yellowStop;
	}
	
	public void setYellowStop(SimpleDuration yellowStop) {
		this.yellowStop = yellowStop;
	}
	
	public SimpleDuration getOrangeStop() {
		return orangeStop;
	}
	
	public void setOrangeStop(SimpleDuration orangeStop) {
		this.orangeStop = orangeStop;
	}
	
	public SimpleDuration getPinkStop() {
		return pinkStop;
	}
	
	public void setPinkStop(SimpleDuration pinkStop) {
		this.pinkStop = pinkStop;
	}
	
	public SimpleDuration getGreenStop() {
		return greenStop;
	}
	
	public void setGreenStop(SimpleDuration greenStop) {
		this.greenStop = greenStop;
	}
	
	public SimpleDuration getBlueStop() {
		return blueStop;
	}
	
	public void setBlueStop(SimpleDuration blueStop) {
		this.blueStop = blueStop;
	}
	
	public SimpleDuration getOtherStop() {
		return otherStop;
	}
	
	public void setOtherStop(SimpleDuration otherStop) {
		this.otherStop = otherStop;
	}
	
	public SimpleDuration getTotalStop() {
		return totalStop;
	}
	
	public void setTotalStop(SimpleDuration totalStop) {
		this.totalStop = totalStop;
	}
	
	@Override
	public String toString() {
		return date + ";" + endHour + ";" + machine + ";" + production + ";" + objective + ";" + yellowStop + ";" + orangeStop + ";" + pinkStop + ";" + greenStop + ";" + blueStop + ";" + otherStop + ";" + totalStop;
	}
	
}