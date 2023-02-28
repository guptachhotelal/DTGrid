package com.dt.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DateUtilTest {

	@Test
	public void testDateToLong() {
		assertTrue(DateUtil.dateToLong(LocalDate.now()) > 0);
	}

	@Test
	public void testDateTimeToLong() {
		assertTrue(DateUtil.dateTimeToLong(LocalDateTime.now()) > 0);
	}

	@Test
	public void testLongToDateTime() {
		assertNotNull(DateUtil.longToDateTime(System.currentTimeMillis()));
	}

	@Test
	public void testStringToDate() {
		assertNotNull(DateUtil.stringToDate("05-02-2023", "dd-MM-yyyy"));
	}
}
