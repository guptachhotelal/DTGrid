package com.dt.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public class FilterPredicate<T> implements Predicate<T> {

	private final String searchText;
	private final Set<String> ignoreSet;

	public FilterPredicate(String searchText, String... ignoreFields) {
		this.searchText = Objects.toString(searchText, "").toLowerCase();
		this.ignoreSet = new HashSet<>(Arrays.asList(ignoreFields));
		this.ignoreSet.add("serialversionuid");
	}

	@Override
	public boolean test(T t) {
		if (searchText.strip().isEmpty()) {
			return true;
		}
		try {
			for (Class<?> clazz = t.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
				for (Field field : clazz.getDeclaredFields()) {
					field.setAccessible(true);
					if (ignoreSet.contains(field.getName().toLowerCase())) {
						continue;
					}
					Object value = field.get(t);
					if (value != null && value.toString().toLowerCase().contains(searchText)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return false;
	}
}