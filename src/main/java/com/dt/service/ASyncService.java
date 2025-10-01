package com.dt.service;

import java.util.Map;

public interface ASyncService<T> {

	void generate();

	Map<Long, T> store();
}
