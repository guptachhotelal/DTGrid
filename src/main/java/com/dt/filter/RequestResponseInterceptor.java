package com.dt.filter;

import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.dt.util.XSSSanitizer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@Component
public class RequestResponseInterceptor implements HandlerInterceptor {

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Wrap the request to sanitize parameters and headers
		HttpServletRequest sanitizedRequest = new XSSRequestWrapper(request);
		// Use sanitizedRequest for the rest of the chain
		request.setAttribute("_xssSanitizedRequest", sanitizedRequest);
		String acceptHeader = sanitizedRequest.getHeader("Accept");
		if (acceptHeader != null && acceptHeader.toLowerCase().contains("json")) {
			ContentCachingResponseWrapper resWrapper = new ContentCachingResponseWrapper(response);
			sanitizedRequest.setAttribute("_xssResWrapper", resWrapper);
		}
		return HandlerInterceptor.super.preHandle(sanitizedRequest, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// Use sanitized request if available
		HttpServletRequest sanitizedRequest = (HttpServletRequest) request.getAttribute("_xssSanitizedRequest");
		if (sanitizedRequest == null)
			sanitizedRequest = request;
		HandlerInterceptor.super.postHandle(sanitizedRequest, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Object wrapperObj = request.getAttribute("_xssResWrapper");
		if (wrapperObj instanceof ContentCachingResponseWrapper) {
			ContentCachingResponseWrapper resWrapper = (ContentCachingResponseWrapper) wrapperObj;
			String contentType = resWrapper.getContentType();
			if (!ObjectUtils.isEmpty(contentType) && contentType.contains("json")) {
				byte[] resData = resWrapper.getContentAsByteArray();
				JsonNode node = mapper.readTree(resData);
				XSSSanitizer.escapeValue(node, mapper);
				resWrapper.resetBuffer();
				resWrapper.getOutputStream().write(node.toString().getBytes(resWrapper.getCharacterEncoding()));
			}
			resWrapper.copyBodyToResponse();
		} else {
			HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
		}
	}
}