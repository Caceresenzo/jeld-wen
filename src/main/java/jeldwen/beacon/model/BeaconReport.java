package jeldwen.beacon.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "beacon_reports")
public class BeaconReport {
	
	/* Variables */
	@Id()
	@GeneratedValue()
	private int id;
	
	@Column()
	private Date date;
	
	@Column()
	private String filename;
	
	@Column()
	private String displayName;
	
	@Column()
	private boolean atMidnight;
	
	/* Constructor */
	public BeaconReport() {
		this(0, null, null, null, false);
	}
	
	/* Constructor */
	public BeaconReport(int id, Date date, String displayName, String filename, boolean atMidnight) {
		this.id = id;
		this.date = date;
		this.displayName = displayName;
		this.filename = filename;
		this.atMidnight = atMidnight;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public boolean isAtMidnight() {
		return atMidnight;
	}
	
	public void setAtMidnight(boolean atMidnight) {
		this.atMidnight = atMidnight;
	}
	
	@Override
	public String toString() {
		return "BeaconReport [id=" + id + ", date=" + date + ", name=" + filename + ", atMidnight=" + atMidnight + "]";
	}
	
}