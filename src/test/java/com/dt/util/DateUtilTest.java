package com.dt.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;

class DateUtilTest extends TestUtilClass {

	@Test
	void testInstantiation() throws Exception {
		Exception ex = testUtilClass(DateUtil.class);
		assertTrue(ex.getMessage().contains("Cannot"));
	}

	@Test
	void testDateToLong() {
		assertTrue(DateUtil.dateToLong(LocalDate.now()) > 0);
	}

	@Test
	void testDateTimeToLong() {
		assertTrue(DateUtil.dateTimeToLong(LocalDateTime.now()) > 0);
	}

	@Test
	void testLongToDateTime() {
		assertNotNull(DateUtil.longToDateTime(System.currentTimeMillis()));
	}

	@Test
	void testStringToDate() {
		assertNotNull(DateUtil.stringToDate("05-02-2023", "dd-MM-yyyy"));
	}

	@Test
	void testlongToDate() {
		assertNotNull(DateUtil.longToDate(new Date().getTime()));
	}
}
