package com.dt.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;

class DateUtilTest {

	@Test
	void testInstantiation() throws Exception {
		Constructor<DateUtil> constructor = DateUtil.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		InvocationTargetException ite = assertThrows(InvocationTargetException.class, () -> {
			constructor.setAccessible(true);
			constructor.newInstance();
		});
		Exception ex = (UnsupportedOperationException) ite.getTargetException();
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
