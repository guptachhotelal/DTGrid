package com.dt.util;

import java.lang.reflect.Field;
import java.util.function.Predicate;

public class FilterPredicate<T> implements Predicate<T> {

	private String searchText;

	public FilterPredicate(String searchText) {
		this.searchText = searchText;
	}

	@Override
	public boolean test(T t) {
		if ("".equals(searchText)) {
			return true;
		}

		boolean contains = false;
		try {
			Field[] fields = t.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if ("serialversionuid".equals(field.getName())) {
					continue;
				}
				Object value = field.get(t);
				contains = String.valueOf(value).toLowerCase().contains(searchText);
				if (contains) {
					break;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return contains;
	}
}