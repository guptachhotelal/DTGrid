package com.dt.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class DataGeneratorTest {

	@Test
	public void testStore() {
		assertNotNull(DataGenerator.store());
	}
}
