package com.dt.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterAndSortUtil {

	public static final <T> List<T> filter(Collection<T> data, String searchText) {
		Predicate<T> predicate = new FilterPredicate<>(searchText);
		return data.parallelStream().filter(predicate).collect(Collectors.toList());
	}

	public static final <T> List<T> sort(Collection<T> data, String sortColumn, boolean asc) {
		Comparator<T> comparator = new FieldComparator<>(sortColumn, asc);
		return data.parallelStream().sorted(comparator).collect(Collectors.toList());
	}

	public static final <T> Map<Long, List<T>> filterAndSort(Collection<T> data, String searchText, int pgNumber,
			int pgSize, String sortColumn, boolean asc) {
		Predicate<T> predicate = new FilterPredicate<>(searchText);
		Comparator<T> comparator = new FieldComparator<>(sortColumn, asc);
		List<T> list = data.parallelStream().filter(predicate).sorted(comparator).collect(Collectors.toList());
		return list.parallelStream().skip((pgNumber - 1) * pgSize).limit(pgSize)
				.collect(Collectors.groupingBy(l -> Long.valueOf(list.size()), Collectors.toList()));
	}

	private FilterAndSortUtil() {
		throw new UnsupportedOperationException("Cannot instantiate " + getClass().getName());
	}
}
