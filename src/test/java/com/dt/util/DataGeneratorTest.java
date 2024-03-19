package com.dt.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.dt.entity.TestData;

class DataGeneratorTest {

	@Test
	void testStore() {
		Map<Long, TestData> store = DataGenerator.store(100, false);
		assertNotNull(store);
		assertFalse(store.isEmpty());
	}

	@Test
	void testStoredefault() {
		Map<Long, TestData> store = DataGenerator.store(0, false);
		assertNotNull(store);
		assertFalse(store.isEmpty());
	}
}
