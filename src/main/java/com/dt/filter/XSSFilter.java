package com.dt.filter;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.dt.util.XSSSanitizer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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