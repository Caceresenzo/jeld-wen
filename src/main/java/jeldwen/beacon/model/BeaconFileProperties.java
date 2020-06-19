package jeldwen.beacon.model;

import java.sql.Date;

import org.apache.commons.io.FileUtils;

public class BeaconFileProperties {
	
	/* Variables */
	private String path;
	private String size;
	private Date date;
	
	/* Constructor */
	public BeaconFileProperties() {
		this(null, 0, null);
	}
	
	/* Constructor */
	public BeaconFileProperties(String path, long size, Date date) {
		this.path = path;
		this.size = FileUtils.byteCountToDisplaySize(size);
		this.date = date;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getSize() {
		return size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
}