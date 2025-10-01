package com.dt.filter;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.dt.util.XSSSanitizer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class XSSFilter implements Filter {

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpRes = (HttpServletResponse) res;
		String contentType = httpRes.getContentType();

		if (contentType != null && contentType.contains("json")) {
			ContentCachingRequestWrapper reqWrapper = new XSSRequestWrapper(httpReq);
			ContentCachingResponseWrapper resWrapper = new ContentCachingResponseWrapper(httpRes);
			chain.doFilter(reqWrapper, resWrapper);
			byte[] resData = resWrapper.getContentAsByteArray();
			JsonNode node = mapper.readTree(resData);
			XSSSanitizer.escapeValue(node, mapper);
			resWrapper.resetBuffer();
			resWrapper.getOutputStream().write(node.toString().getBytes(resWrapper.getCharacterEncoding()));
			resWrapper.copyBodyToResponse();
		} else {
			// For non-JSON responses (JSP, HTML, static resources), do not wrap or modify
			chain.doFilter(req, res);
		}
	}
}

class XSSRequestWrapper extends ContentCachingRequestWrapper {

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
		return XSSSanitizer.sanitizeObject(super.getParameter(parameter));
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
			encodedValues[i] = Objects.isNull(values[i]) ? null : XSSSanitizer.sanitizeObject(values[i]);
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