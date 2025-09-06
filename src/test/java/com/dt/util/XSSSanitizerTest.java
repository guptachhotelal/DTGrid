package com.dt.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

class XSSSanitizerTest {

	@Test
	void testSanitizeTextNull() throws Exception {
		String dirty = null;
		Assertions.assertEquals("null", XSSSanitizer.sanitizerObject(dirty));
	}

	@Test
	void testSanitizeTextEmpty() throws Exception {
		String dirty = "";
		String SanitizeedText = XSSSanitizer.sanitizerObject(dirty);
		Assertions.assertNotNull(SanitizeedText);
		Assertions.assertEquals(dirty, SanitizeedText);
	}

	@Test
	void testSanitizeText() throws Exception {
		String dirty = "<script>alert(\"This is JavaScript\")</script>";
		String SanitizeedText = XSSSanitizer.sanitizerObject(dirty);
		Assertions.assertNotNull(SanitizeedText);
		Assertions.assertNotEquals(dirty, SanitizeedText);
	}

	@Test
	void testAlreadySanitizeObject() throws Exception {
		Dirty dirty = new Dirty(1, "abc def", "This is test description", new Date());
		Dirty SanitizeedObj = XSSSanitizer.sanitizerObject(dirty, Dirty.class);
		Assertions.assertEquals(dirty, SanitizeedObj);
	}

	@Test
	void testSanitizeObjectList() throws Exception {
		List<String> list = Arrays.asList("a", "b", "c");
		List<String> Sanitizeed = XSSSanitizer.sanitizerObject(list, String.class);
		Assertions.assertEquals(list.size(), Sanitizeed.size());
	}

	@Test
	void testSanitizeObjectNullValue() throws Exception {
		Dirty dirty = new Dirty(null, null, null, null);
		Dirty SanitizeedObj = XSSSanitizer.sanitizerObject(dirty, Dirty.class);
		Assertions.assertEquals(dirty, SanitizeedObj);
		Assertions.assertNull(SanitizeedObj.getId());
		Assertions.assertNull(SanitizeedObj.getName());
		Assertions.assertNull(SanitizeedObj.getDescription());
		Assertions.assertNull(SanitizeedObj.getDob());
	}

	@Test
	void testSanitizeEmptyValue() throws Exception {
		Dirty dirty = new Dirty(null, "", "", null);
		Dirty SanitizeedObj = XSSSanitizer.sanitizerObject(dirty, Dirty.class);
		Assertions.assertEquals(dirty, SanitizeedObj);
		Assertions.assertNull(SanitizeedObj.getId());
		Assertions.assertNotNull(SanitizeedObj.getName());
		Assertions.assertNotNull(SanitizeedObj.getDescription());
		Assertions.assertNull(SanitizeedObj.getDob());
	}

	@Test
	void testSanitizeObject() throws Exception {
		Dirty dirty = new Dirty(1, "Abc<def>", "testd\"escription", new Date());
		Dirty SanitizeedObj = XSSSanitizer.sanitizerObject(dirty, Dirty.class);
		Assertions.assertNotEquals(dirty, SanitizeedObj);
		Assertions.assertNotEquals(dirty.getName(), SanitizeedObj.getName());
		Assertions.assertTrue(SanitizeedObj.getName().contains("&lt;"));
		Assertions.assertTrue(SanitizeedObj.getName().contains("&lt;def&gt;"));
		Assertions.assertTrue(SanitizeedObj.getName().endsWith("&gt;"));
		Assertions.assertEquals(HtmlUtils.htmlEscape(dirty.getDescription()), SanitizeedObj.getDescription());
	}

	@Test
	void testSanitizeScript() throws Exception {
		Dirty dirty = new Dirty(1, "<script>alert(\"This is JavaScript\")</script>", "testd\"escription", new Date());
		Dirty SanitizeedObj = XSSSanitizer.sanitizerObject(dirty, Dirty.class);
		Assertions.assertNotEquals(dirty, SanitizeedObj);
		Assertions.assertNotEquals(dirty.getName(), SanitizeedObj.getName());
		Assertions.assertEquals(HtmlUtils.htmlEscape(dirty.getDescription()), SanitizeedObj.getDescription());
	}

	@Test
	void testSanitizeHtmlScript() throws Exception {
		Dirty dirty = new Dirty(1, "<script>alert(\"This is JavaScript\")</script>",
				"<html><body><script>alert(\"This is sample html with JavaScript\")</script></body></html>",
				new Date());
		Dirty SanitizeedObj = XSSSanitizer.sanitizerObject(dirty, Dirty.class);
		Assertions.assertNotEquals(dirty, SanitizeedObj);
		Assertions.assertNotEquals(dirty.getName(), SanitizeedObj.getName());
		Assertions.assertTrue(SanitizeedObj.getDescription().contains("&lt;"));
		Assertions.assertTrue(SanitizeedObj.getDescription().endsWith("&gt;"));
		Assertions.assertNotEquals(dirty.getDescription(), SanitizeedObj.getDescription());
	}

	@Test
	void testEscapeValue() throws Exception {
		String json = "{" + "  \"id\" : 1," + "  \"name\" : \"<h1>test</h1>\"," + "  \"phones\" : {"
				+ "    \"mobile\" : \"09876543210\"," + "    \"office\" : \"033-22445566\","
				+ "    \"home\" : \"022-22334455\"" + "  },"
				+ "  \"hobbies\" : [ \"Chess\", \"Cricket\", \"Football\" ]" + "}";

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(json);
		XSSSanitizer.escapeValue(jsonNode, mapper);
		Assertions.assertTrue(jsonNode.toString().contains("&lt;h1&gt;"));
	}

	@Test
	void testIsEsacped() throws Exception {
		String text = "<script>alert(\"This is JavaScript\")</script>";
		Assertions.assertTrue(!XSSSanitizer.isEscaped(text));
		String escapedText = "&lt;script&gt;alert(&quot;This is JavaScript&quot;)&lt;/script&gt;";
		Assertions.assertTrue(XSSSanitizer.isEscaped(escapedText));
	}
}

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
class Dirty {
	Integer id;
	String name;
	String description;
	Date dob;

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Dirty other = (Dirty) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}
}
