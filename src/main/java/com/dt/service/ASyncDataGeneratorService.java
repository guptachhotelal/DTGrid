package com.dt.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dt.model.TestData;
import com.dt.util.DataGenerator;

@Service
public class ASyncDataGeneratorService implements ASyncService<TestData> {

	@Value("${app.record.start}")
	private int startSize;

	@Value("${app.record.end}")
	private int endSize;

	@Async
	@Override
	public void generate() {
		DataGenerator.generate(startSize, endSize, true);
	}

	@Override
	public Map<Long, TestData> store() {
		return DataGenerator.store();
	}
}
