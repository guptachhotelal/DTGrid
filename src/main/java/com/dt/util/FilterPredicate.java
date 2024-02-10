package com.dt.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FilterPredicate<T> implements Predicate<T> {

	private String searchText;
	private List<String> ignoreList;

	public FilterPredicate(String searchText, String... ignoreFields) {
		this.searchText = searchText;
		this.ignoreList = Arrays.asList(ignoreFields);
	}

	@Override
	public boolean test(T t) {
		if ("".equals(searchText)) {
			return true;
		}

		boolean contains = false;
		try {
			Class<?> clazz = t.getClass();
			OUTER: while (clazz != null) {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					if ("serialversionuid".equals(field.getName()) || ignoreList.contains(searchText)) {
						continue;
					}
					contains = String.valueOf(field.get(t)).toLowerCase().contains(searchText);
					if (contains) {
						break OUTER;
					}
				}
				clazz = clazz.getSuperclass();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return contains;
	}
}