package com.dt.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.dt.util.TestUtilClass;

class DocConstantTest extends TestUtilClass {

	@Test
	void testInstantiation() throws Exception {
		Throwable th = testUtilClass(DocConstant.class);
		assertTrue(th instanceof UnsupportedOperationException);
		UnsupportedOperationException usoe = (UnsupportedOperationException) th;
		assertTrue(usoe.getMessage().contains("Cannot"));
	}
}
