package com.dt.config;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

class DocConstantTest {

	@Test
	void testInstantiation() throws Exception {
		Constructor<DocConstant> constructor = DocConstant.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		InvocationTargetException ite = assertThrows(InvocationTargetException.class, () -> {
			constructor.setAccessible(true);
			constructor.newInstance();
		});
		UnsupportedOperationException uoe = (UnsupportedOperationException) ite.getTargetException();
		assertTrue(uoe.getMessage().contains("Cannot"));
	}
}
