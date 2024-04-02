package com.dt.util;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Objects;

public class FieldComparator<T> implements Comparator<T> {

	private String fieldName;
	private boolean asc;

	public FieldComparator(String fieldName, boolean asc) {
		this.fieldName = fieldName;
		this.asc = asc;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int compare(T t1, T t2) {
		int val = 0;
		if (Objects.isNull(fieldName)) {
			return val;
		}
		try {
			Field field1 = t1.getClass().getDeclaredField(fieldName);
			Field field2 = t2.getClass().getDeclaredField(fieldName);

			field1.setAccessible(true);
			field2.setAccessible(true);
			Comparable<Object> cmp1 = (Comparable<Object>) field1.get(t1);
			Comparable<Object> cmp2 = (Comparable<Object>) field2.get(t2);

			if (Objects.isNull(cmp2)) {
				val = 1;
			} else if (Objects.isNull(cmp1)) {
				val = -1;
			} else {
				val = Objects.compare(cmp1, cmp2, asc ? Comparator.naturalOrder() : Comparator.reverseOrder());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return val;
	}
}