package com.dt.filter;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.dt.util.XSSSanitizer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;

public class XSSRequestWrapper extends ContentCachingRequestWrapper {

	public XSSRequestWrapper(ServletRequest request) {
		super((HttpServletRequest) request);
	}

	@Override
	public @NonNull ServletInputStream getInputStream() throws IOException {
		ServletInputStream sis = super.getInputStream();
		String body = streamToEscapedString(sis);
		return new ServletInputStream() {
			private final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());

			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public boolean isFinished() {
				return bais.available() == 0;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				throw new UnsupportedOperationException("Operation not supported.");
			}
		};
	}

//	@Override
//	public String getHeader(String name) {
//		String header = super.getHeader(name);
//		if (name.toLowerCase().startsWith(customheade prefix)) {
//			return Objects.isNull(header) ? null : XSSSanitizer.cleanObject(header);
//		}
//		return header;
//	}
//
//	@Override
//	public Enumeration<String> getHeaders(String name) {
//		Enumeration<String> headerValues = super.getHeaders(name);
//		if (name.toLowerCase().startsWith(customheade prefix)) {
//			List<String> customHeaders = new ArrayList<>();
//			while (headerValues.hasMoreElements()) {
//				String headerValue = headerValues.nextElement();
//				String value = Objects.isNull(headerValue) ? null : XSSSanitizer.cleanObject(headerValue);
//				customHeaders.add(value);
//			}
//			return Collections.enumeration(customHeaders);
//		}
//		return headerValues;
//	}

	@Override
	public @NonNull String getParameter(String parameter) {
		return XSSSanitizer.sanitizerObject(super.getParameter(parameter));
	}

	@Override
	public @NonNull String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		int arrLength = 0;
		if (!Objects.isNull(values)) {
			arrLength = values.length;
		}
		String[] encodedValues = new String[arrLength];
		for (int i = 0; i < arrLength; i++) {
			encodedValues[i] = Objects.isNull(values[i]) ? null : XSSSanitizer.sanitizerObject(values[i]);
		}
		return encodedValues;
	}

	private String streamToEscapedString(InputStream inputStream) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(inputStream);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int result = bis.read(); result != -1; result = bis.read()) {
			baos.write((byte) result);
		}
		baos.close();
		bis.close();
		String requestBody = baos.toString(StandardCharsets.UTF_8.name());
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(requestBody);
		XSSSanitizer.escapeValue(node, mapper);
		return node.toString();
	}
}