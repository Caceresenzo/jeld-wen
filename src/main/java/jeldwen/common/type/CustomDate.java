package jeldwen.common.type;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jeldwen.common.serialization.ToStringSerializer;

@JsonSerialize(using = ToStringSerializer.class)
public class CustomDate implements Serializable {
	
	/* Serialization */
	private static final long serialVersionUID = 1L;

	/* Constants */
	public static final CustomDate ZERO = new CustomDate(0, 0, 0);
	
	/* Regex Pattern */
	public static final Pattern FROM_STRING_PATTERN = Pattern.compile("(\\d{2})\\/(\\d{2})\\/(\\d{4})");
	
	/* Variables */
	private final int day, month, year;
	
	/* Constructor */
	public CustomDate(int day, int month, int year) {
		super();
		
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	@Override
	public String toString() {
		return String.format("%02d/%02d/%04d", day, month, year);
	}
	
	public static String safeToString(CustomDate customDate) {
		return (customDate != null ? customDate : ZERO).toString();
	}
	
	public static CustomDate fromString(String string) {
		Matcher matcher = FROM_STRING_PATTERN.matcher(string);
		
		if (matcher.find()) {
			int day = Integer.parseInt(matcher.group(1));
			int month = Integer.parseInt(matcher.group(2));
			int year = Integer.parseInt(matcher.group(3));
			
			return new CustomDate(day, month, year);
		}
		
		return ZERO;
	}
	
}