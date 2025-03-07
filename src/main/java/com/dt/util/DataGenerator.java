package com.dt.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.dt.entity.TestData;

@Configuration
@PropertySource({ "classpath:/dtgrid-${spring.profiles.active}.properties" })
public class DataGenerator {

	private static int SIZE;

	@Value("${app.record.size}")
	private int size;

	@Value("${app.record.size}")
	void setSize(int size) {
		SIZE = size;
	}

	private static final char[] CHARS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	private static final String[][] CPS = { { "Mumbai", "400001", "Maharashra" }, { "New Delhi", "110001", "Delhi" },
			{ "Kolkata", "700001", "West Bengal" }, { "Chennai", "600001", "Tamil Nadu" },
			{ "Bangaluru", "560001", "Karnataka" }, { "Pune", "411001", "Maharashtra" }, { "Patna", "800001", "Bihar" },
			{ "Mysuru", "570001", "karnataka" }, { "Indore", "452001", "Madhya Pradesh" },
			{ "Nagpur", "440001", "Maharashtra" } };

	private static final String[] DOMAINS = { "gmail.com", "yahoo.com", "msn.com", "live.com", "hotmail.com",
			"yahoo.co.in", "outlook.com", "gmx.com", "rediff.com", "zoho.com" };

	private static final Random RANDOM = new Random();

	private static final Map<Long, TestData> STORAGE = new ConcurrentHashMap<>();

	private static long date() {
		LocalDateTime ldt1 = LocalDateTime.now().minusYears(18);
		long startMillis = DateUtil.dateTimeToLong(ldt1);
		LocalDateTime ldt2 = ldt1.withYear(1980);
		long endMillis = DateUtil.dateTimeToLong(ldt2);
		return ThreadLocalRandom.current().nextLong(endMillis, startMillis);
	}

	private static void generate(Map<Long, TestData> map, int from, int to) {
		int charLen = CHARS.length;
		int cityLen = CPS.length;
		StringBuilder sb = new StringBuilder();
		for (long i = from; i <= to; i++) {
			sb.append(Character.toUpperCase(CHARS[index(charLen)]));
			for (int j = 0; j < 15; j++) {
				sb.append(CHARS[index(charLen)]);
			}
			String name = String.valueOf(sb);
			sb.setLength(0);

			List<Integer> start = Arrays.asList(9, 8, 7, 6);
			Collections.shuffle(start);
			sb.append(start.stream().map(String::valueOf).collect(Collectors.joining()));
			for (int j = 0; j < 6; j++) {
				int num = index(10);
				num = (num == 0) ? (num += index(10)) : num;
				sb.append(num);
			}

			String phone = String.valueOf(sb);
			sb.setLength(0);

			int cIdx = index(cityLen);
			long dob = date();
			LocalDateTime ldt = DateUtil.longToDateTime(dob).plusYears(index(10));
			long time = DateUtil.dateTimeToLong(ldt);
			String email = name.substring(0, 10).toLowerCase().concat("@").concat(DOMAINS[index(DOMAINS.length)]);
			TestData td = TestData.builder().id(i).name(name).dob(dob).phone(phone).email(email).city(CPS[cIdx][0])
					.pincode(CPS[cIdx][1]).state(CPS[cIdx][2]).createDate(time)
					.updateDate(time + RANDOM.nextInt(0, 3600)).build();
			map.put(i, td);
		}
	}

	private static int index(int seed) {
		return RANDOM.nextInt(seed);
	}

	public static final Map<Long, TestData> store(int seed, boolean readSize) {
		int start = seed <= 0 ? 1000 : seed;
		if (STORAGE.isEmpty()) {
			generate(STORAGE, 1, start);
		}
		if (STORAGE.size() == start && readSize) {
			Thread.startVirtualThread(() -> generate(STORAGE, start + 1, SIZE));
		}
		return STORAGE;
	}
}