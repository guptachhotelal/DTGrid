package com.dt;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class TestUtilClass {

	public <T> Throwable testUtilClass(Class<T> clazz) throws Exception {
		Constructor<T> constructor = clazz.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		InvocationTargetException ite = assertThrows(InvocationTargetException.class, () -> {
			constructor.setAccessible(true);
			constructor.newInstance();
		});
		return ite.getTargetException();
	}
}
