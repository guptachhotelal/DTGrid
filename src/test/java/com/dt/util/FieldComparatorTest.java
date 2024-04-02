package com.dt.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.dt.entity.TestData;

class FieldComparatorTest {

	@Test
	void testCompareWithOneNullvalue() {
		List<TestData> list = Stream.generate(TestData.builder()::build).limit(10).collect(Collectors.toList());
		Random random = new Random();
		list.add(TestData.builder().build());
		Collections.sort(list, new FieldComparator<>(columnName(random), random.nextBoolean()));
		assertEquals(11, list.size());
	}

	@Test
	void testCompareWittSecondNullvalue() {
		List<TestData> list = new ArrayList<>();
		Random random = new Random();
		list.add(TestData.builder().city("Mumbai").build());
		list.add(TestData.builder().build());
		Collections.sort(list, new FieldComparator<>("city", random.nextBoolean()));
		assertEquals(2, list.size());
	}

	@Test
	void testCompareWithAllNullvalue() throws Exception {
		List<TestData> list = new ArrayList<>();
		Random random = new Random();
		String column = columnName(random);
		while ("serialversionuid".equalsIgnoreCase(column)) {
			random = new Random();
			column = columnName(random);
		}
		list.add(TestData.builder().build());
		list.add(TestData.builder().build());
		list.add(TestData.builder().build());
		list.add(TestData.builder().build());
		list.add(TestData.builder().build());
		Collections.sort(list, new FieldComparator<>(column, random.nextBoolean()));
		assertEquals(5, list.size());
	}

	@Test
	void testCompare() {
		List<TestData> list = Stream.generate(TestData.builder()::build).limit(10).collect(Collectors.toList());
		Random random = new Random();
		Collections.sort(list, new FieldComparator<>(columnName(random), random.nextBoolean()));
		assertEquals(10, list.size());
	}

	@Test
	void testCompareException() {
		List<TestData> list = Stream.generate(TestData.builder()::build).limit(10).collect(Collectors.toList());
		list.add(null);
		Random random = new Random();
		assertThrows(Exception.class, () -> {
			Collections.sort(list, new FieldComparator<>(columnName(random), random.nextBoolean()));
		});
	}

	private String columnName(Random random) {
		Field[] fields = TestData.class.getDeclaredFields();
		return fields[random.nextInt(fields.length)].getName();
	}
}
