package jeldwen.common.type;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jeldwen.common.serialization.ToStringSerializer;

@JsonSerialize(using = ToStringSerializer.class)
public class SimpleDuration implements Serializable {

	/** Serialization */
	private static final long serialVersionUID = 1L;

	/* Constants */
	public static final SimpleDuration ZERO = new SimpleDuration(0);
	
	/* Regex Pattern */
	public static final Pattern FROM_STRING_PATTERN = Pattern.compile("(\\d{2,}):(\\d{2}):(\\d{2})");

	/* Variables */
	private final long seconds;

	/* Constructor */
	public SimpleDuration(long hour, long minute, long seconds) {
		this(hour * 3600 + minute * 60 + seconds);
	}

	/* Constructor */
	public SimpleDuration(long seconds) {
		this.seconds = seconds;
	}

	public long getHour() {
		return seconds / 3600;
	}

	public long getMinutes() {
		return seconds / 60 % 60;
	}

	public long getSeconds() {
		return seconds % 60;
	}
	
	@Override
	public String toString() {
		return String.format("%02d:%02d:%02d", getHour(), getMinutes(), getSeconds());
	}

	public static String safeToString(SimpleDuration simpleDuration) {
		return (simpleDuration != null ? simpleDuration : ZERO).toString();
	}

	public static SimpleDuration fromString(String string) {
		Matcher matcher = FROM_STRING_PATTERN.matcher(string);
		
		if (matcher.find()) {
			int hours = Integer.parseInt(matcher.group(1));
			int minutes = Integer.parseInt(matcher.group(2));
			int seconds = Integer.parseInt(matcher.group(3));
			
			return new SimpleDuration(hours, minutes, seconds);
		}
		
		return ZERO;
	}

}