package com.dt.entity;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.dt.model.TestData;

class TestDataTest {

	@Test
	void testToString() {
		String builderName = TestData.class.getSimpleName() + "Builder";
		assertTrue(TestData.builder().toString().contains(builderName));
	}
}
