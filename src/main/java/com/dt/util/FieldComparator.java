package com.dt.util;

import java.lang.reflect.Field;
import java.util.Comparator;

public class FieldComparator<T> implements Comparator<T> {

	private final String fieldName;
	private final boolean asc;

	public FieldComparator(String fieldName, boolean asc) {
		this.fieldName = fieldName;
		this.asc = asc;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int compare(T t1, T t2) {
		if (fieldName == null) {
			return 0;
		}
		try {
			Field field = getField(t1.getClass(), fieldName);
			field.setAccessible(true);
			Comparable<Object> cmp1 = (Comparable<Object>) field.get(t1);
			Comparable<Object> cmp2 = (Comparable<Object>) field.get(t2);
			if (cmp1 == null && cmp2 == null)
				return 0;
			if (cmp1 == null)
				return asc ? -1 : 1;
			if (cmp2 == null)
				return asc ? 1 : -1;
			return asc ? cmp1.compareTo(cmp2) : cmp2.compareTo(cmp1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Field getField(Class<?> clazz, String name) throws NoSuchFieldException {
		while (clazz != null) {
			try {
				return clazz.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			}
		}
		throw new NoSuchFieldException(name);
	}
}