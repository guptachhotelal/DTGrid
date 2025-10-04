package com.dt.util;

import java.util.Collections;
import java.util.List;

import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class XSSSanitizer {

	public static String sanitizeObject(Object object) {
		String text = String.valueOf(object);
		return !isEscaped(text) ? HtmlUtils.htmlEscape(text) : text;
	}

	public static <T> T sanitizeObject(T object, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json(mapper, object), clazz);
		} catch (JsonProcessingException jpe) {
			throw new RuntimeException(jpe.getMessage());
		}
	}

	public static <T> List<T> sanitizeObject(List<T> objects, Class<T> clazz) {
		if (objects.isEmpty()) {
			return Collections.emptyList();
		}
		return objects.stream().map(object -> sanitizeObject(object, clazz)).toList();
	}

	private static String json(ObjectMapper mapper, Object obj) throws JsonProcessingException {
		String json = mapper.writeValueAsString(obj);
		JsonNode node = mapper.readTree(json);
		escapeValue(node, mapper);
		return node.toString();
	}

	public static boolean isEscaped(String text) {
		return !text.equals(HtmlUtils.htmlUnescape(text));
	}

	public static void escapeValue(JsonNode parentNode, ObjectMapper mapper) {
		parentNode.properties().forEach(childNode -> {
			JsonNode child = childNode.getValue();
			if (child.isArray()) {
				child.iterator().forEachRemaining(node -> escapeValue(node, mapper));
			} else if (child.isContainerNode()) {
				escapeValue(child, mapper);
			} else if (child.isTextual()) {
				String text = child.asText();
				if (!isEscaped(text)) {
					text = sanitizeObject(text);
				}
				childNode.setValue(mapper.valueToTree(text));
			}
		});
	}

	private XSSSanitizer() {
		throw new UnsupportedOperationException("Cannot instantiate " + getClass().getName());
	}
}
