package com.dt.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.dt.entity.TestData;

class FilterAndSortUtilTest extends TestUtilClass {

	@Test
	void testInstantiation() throws Exception {
		Throwable th = testUtilClass(FilterAndSortUtil.class);
		assertTrue(th instanceof UnsupportedOperationException);
		UnsupportedOperationException usoe = (UnsupportedOperationException) th;
		assertTrue(usoe.getMessage().contains("Cannot"));
	}

	@Test
	void testFilter() {
		List<TestData> list = temp(new Random().nextInt(1, 10));
		list.add(TestData.builder().city("Mumbai").build());
		list.add(TestData.builder().state("Delhi").build());
		List<TestData> filtered = FilterAndSortUtil.filter(list, "Delhi");
		assertFalse(filtered.isEmpty());
		assertEquals(1, filtered.size());
		assertEquals("Delhi", filtered.get(0).getState());
	}

	@Test
	void testSortAsc() {
		Random random = new Random();
		List<TestData> list = temp(random.nextInt(1, 10));
		List<TestData> filtered = FilterAndSortUtil.sort(list, columnName(random), true);
		assertFalse(filtered.isEmpty());
	}

	@Test
	void testSortDesc() {
		Random random = new Random();
		List<TestData> list = temp(random.nextInt(1, 10));
		List<TestData> filtered = FilterAndSortUtil.sort(list, columnName(random), false);
		assertFalse(filtered.isEmpty());
	}

	private List<TestData> temp(int seed) {
		return Stream.generate(TestData.builder()::build).limit(seed).collect(Collectors.toList());
	}

	private String columnName(Random random) {
		Field[] fields = TestData.class.getDeclaredFields();
		return fields[random.nextInt(fields.length)].getName();
	}
}
