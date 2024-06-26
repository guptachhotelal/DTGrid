package com.dt.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import com.dt.entity.TestData;

class HTMLtoPDFTest extends TestUtilClass {

	private static int seed = 10000;

	@Test
	void testInstantiation() throws Exception {
		Throwable th = testUtilClass(HTMLtoPDF.class);
		assertEquals(UnsupportedOperationException.class, th.getClass());
		UnsupportedOperationException usoe = (UnsupportedOperationException) th;
		assertTrue(usoe.getMessage().contains("Cannot"));
	}

	@Test
	void testEmptyHTML() {
		String html = HTMLtoPDF.html(Collections.emptyList());
		Elements trs = Jsoup.parse(html).getElementsByTag("tr");
		assertEquals(1, trs.size());
	}

	@Test
	void testNonEmptyHTML() {
		String html = HTMLtoPDF.html(DataGenerator.store(seed, false).values());
		Elements trs = Jsoup.parse(html).getElementsByTag("tr");
		assertEquals((seed + 1), trs.size());
	}

	@Test
	void testEmptyPdf() throws Exception {
		String html = HTMLtoPDF.html(Collections.emptyList());
		String fileName = "Report" + System.nanoTime() + ".pdf";
		String file = HTMLtoPDF.pdf(html, fileName, false);
		Path path = Paths.get(file);
		assertTrue(Files.exists(path));
		Files.delete(path);
	}

	@Test
	void testPdf() throws Exception {
		Map<Long, TestData> map = DataGenerator.store(seed, false);
		String html = HTMLtoPDF.html(map.values());
		String fileName = "Report" + System.nanoTime() + ".pdf";
		String file = HTMLtoPDF.pdf(html, fileName, false);
		deleteFile(file);
	}

	@Test
	void testProtectedPdf() throws Exception {
		Map<Long, TestData> map = DataGenerator.store(seed, false);
		String html = HTMLtoPDF.html(map.values());
		String fileName = "Report" + System.nanoTime() + ".pdf";
		String file = HTMLtoPDF.pdf(html, fileName, true);
		deleteFile(file);
	}

	private void deleteFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		assertTrue(Files.exists(path));
		Files.delete(path);
	}
}
