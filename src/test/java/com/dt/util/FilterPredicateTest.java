package com.dt.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.dt.entity.TestData;

class FilterPredicateTest {

	@Test
	void testTest() {
		TestData td = TestData.builder().build();
		Predicate<TestData> predicate = new FilterPredicate<>("serialversionuid", "serialversionuid");
		boolean fail = predicate.test(td);
		assertFalse(fail);
	}

	@Test
	void testTestForNull() {
		Predicate<TestData> predicate = new FilterPredicate<>("serialversionuid", "serialversionuid");
		Exception ex = assertThrows(RuntimeException.class, () -> predicate.test(null));
		assertTrue(ex.getCause() == null || ex.getCause() instanceof NullPointerException);
	}

	@Test
	void testTestNonNull() {
		new FilterPredicate<>(null);
		TestData td = TestData.builder().city("Mumbai").build();
		Predicate<TestData> predicate = new FilterPredicate<>("Mum");
		assertTrue(predicate.test(td));
	}

	@Test
	void testTestNonNullContainsInIgnoreList() {
		new FilterPredicate<>(null);
		TestData td = TestData.builder().city("Mumbai").state("Maharashtra").build();
		Predicate<TestData> predicate = new FilterPredicate<>("Mum", "Mum");
		assertTrue(predicate.test(td));
	}

	@Test
	void testTestNonNullNotContainsInIgnoreList() {
		new FilterPredicate<>(null);
		TestData td = TestData.builder().city("Mumbai").state("Maharashtra").build();
		Predicate<TestData> predicate = new FilterPredicate<>("Mum", "city"); // ignore field name 'city'
		assertFalse(predicate.test(td));
	}
}