package com.dt.service;

import java.util.Map;

import com.dt.entity.TestData;

public interface ASyncService {

	void generate();

	Map<Long, TestData> store();
}
