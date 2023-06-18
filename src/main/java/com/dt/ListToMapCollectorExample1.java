package com.dt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

public class ListToMapCollectorExample1 {
    public static void main(String[] args) {
        List<Object> objects = List.of("apple", "banana", "cherry", "date", "elderberry");

        Collector<Object, List<Object>, Map<Integer, List<Object>>> listToMapCollector = Collector.of(
                ArrayList::new,                                           
                List::add,                                               
                (list1, list2) -> {                                       
                    list1.addAll(list2);
                    return list1;
                },
                result -> {                                                
                    Map<Integer, List<Object>> map = new HashMap<>();
                    map.put(result.size(), result);
                    return map;
                },
                Collector.Characteristics.IDENTITY_FINISH
        );

        Map<Integer, List<Object>> resultMap = objects.stream()
                .collect(listToMapCollector);

        System.out.println(resultMap);
        // {5=[apple, banana, cherry, date, elderberry]}
    }
}
