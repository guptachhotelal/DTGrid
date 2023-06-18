package com.dt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ListToMapCollectorExample2 {
	public static void main(String[] args) {
		Collection<String> objects = List.of("apple", "banana", "cherry", "date", "elderberry");

		Collector<String, ?, Map<Integer, List<String>>> listToMapCollector = Collectors
				.groupingBy(obj -> objects.size(), Collectors.toList());

		Map<Integer, List<String>> resultMap = objects.stream().collect(listToMapCollector);

		System.out.println(resultMap);
	}
}
