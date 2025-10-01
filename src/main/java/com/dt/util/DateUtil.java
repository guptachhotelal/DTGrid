package com.dt.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DateUtil {

	public static final String FORMAT_YYYY_MM_DD_DASH = "yyyy-MM-dd";

	public static long dateToLong(LocalDate ld) {
		return ld.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public static long dateTimeToLong(LocalDateTime ldt) {
		return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public static LocalDateTime longToDateTime(long time) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), TimeZone.getDefault().toZoneId());
	}

	public static LocalDate stringToDate(String date, String format) {
		return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
	}

	public static LocalDate longToDate(long time) {
		return LocalDate.ofInstant(Instant.ofEpochMilli(time), TimeZone.getDefault().toZoneId());
	}

	private DateUtil() {
		throw new UnsupportedOperationException("Cannot instantiate  " + getClass().getName());
	}
}
