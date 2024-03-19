package com.dt.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterPredicate<T> implements Predicate<T> {

	private String searchText;
	private List<String> ignoreList;

	public FilterPredicate(String searchText, String... ignoreFields) {
		this.searchText = Objects.isNull(searchText) ? "" : searchText.toLowerCase();
		ignoreList = Arrays.asList(ignoreFields).stream().collect(Collectors.toList());
		ignoreList.add("serialversionuid");
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
					String fieldValue = String.valueOf(field.get(t)).toLowerCase();
					contains = fieldValue.contains(searchText) && !ignoreList.contains(searchText);
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