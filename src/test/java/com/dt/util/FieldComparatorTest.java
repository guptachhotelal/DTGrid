package com.dt.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		Random random = random();
		int size = random.nextInt(1, 11);
		List<TestData> list = objects(size);
		list.add(TestData.builder().build());
		Collections.sort(list, new FieldComparator<>(columnName(random), random.nextBoolean()));
		assertEquals(size + 1, list.size());
	}

	@Test
	void testCompareWittSecondNullvalue() {
		List<TestData> list = new ArrayList<>();
		list.add(TestData.builder().city("Mumbai").build());
		list.add(TestData.builder().build());
		Collections.sort(list, new FieldComparator<>("city", random().nextBoolean()));
		assertEquals(2, list.size());
	}

	@Test
	void testCompareWithAllNullvalue() throws Exception {
		Random random = random();
		int size = random.nextInt(1, 11);
		List<TestData> list = objects(size);
		String column = columnName(random);
		while ("serialversionuid".equalsIgnoreCase(column)) {
			random = new Random();
			column = columnName(random);
		}
		Collections.sort(list, new FieldComparator<>(column, random.nextBoolean()));
		assertEquals(size, list.size());
	}

	@Test
	void testCompare() {
		Random random = random();
		int size = random.nextInt(1, 11);
		List<TestData> list = objects(size);
		Collections.sort(list, new FieldComparator<>(columnName(random), random.nextBoolean()));
		assertEquals(size, list.size());
	}

	@Test
	void testCompareException() {
		Random random = random();
		int size = random.nextInt(1, 11);
		List<TestData> list = objects(size);
		list.add(null);
		Exception ex = assertThrows(Exception.class, () -> {
			Collections.sort(list, new FieldComparator<>(columnName(random), random.nextBoolean()));
		});
		assertTrue((ex instanceof IllegalAccessException) || (ex instanceof NoSuchFieldException)
				|| (ex instanceof RuntimeException));
	}

	private List<TestData> objects(int size) {
		return Stream.generate(TestData.builder()::build).limit(size).collect(Collectors.toList());
	}

	private Random random() {
		return new Random();
	}

	private String columnName(Random random) {
		Field[] fields = TestData.class.getDeclaredFields();
		return fields[random.nextInt(fields.length)].getName();
	}
}
