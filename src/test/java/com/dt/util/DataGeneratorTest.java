package com.dt.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.dt.entity.TestData;

class DataGeneratorTest {

	@Test
	void testStore() {
		DataGenerator.generate(100, 10000, false);
		Map<Long, TestData> store = DataGenerator.store();
		assertNotNull(store);
		assertFalse(store.isEmpty());
	}

	@Test
	void testStoredefault() {
		DataGenerator.generate(100, 10000, false);
		Map<Long, TestData> store = DataGenerator.store();
		assertNotNull(store);
		assertFalse(store.isEmpty());
	}

	@Test
	void testStoredefaultReadSize() {
		DataGenerator.generate(0, 1000, true);
		Map<Long, TestData> store = DataGenerator.store();
		assertNotNull(store);
		assertFalse(store.isEmpty());
	}
}
