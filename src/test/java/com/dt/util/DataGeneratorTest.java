package com.dt.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.dt.entity.TestData;

public class DataGeneratorTest {

	@Test
	public void testStore() {
		Map<Long, TestData> store = DataGenerator.store(500);
		assertNotNull(store);
		assertFalse(store.isEmpty());
	}
}
