package com.dt.controller;

import java.lang.reflect.Field;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dt.DTGridApplicationTest;
import com.dt.model.TestData;

import jakarta.annotation.Resource;

class DataControllerTest extends DTGridApplicationTest {

	@LocalServerPort
	private int port;

	@Resource
	private MockMvc mockMvc;

	@Resource
	private WebApplicationContext context;

	private static final int SORT_COLUMN = 1;
	private static final int SORT_ORDER_ASC = 2;
	private static final int SORT_ORDER_DESC = 3;
	private static final int SEARCH_TEXT_EMPTY = 4;
	private static final int SEARCH_TEXT_NON_EMPTY = 5;
	private static final int START_FROM_FIRST = 6;
	private static final int START_FROM_OTHER = 7;
	private static final int PER_PAGE_ZERO = 8;
	private static final int PER_PAGE_NEGATIVE = 9;
	private static final int PER_PAGE_POSITIVE = 10;

	private static final String USER_NAME = "user";
	private static final String USER_PASSWORD = "user";

	private static final String ADMIN_USER_NAME = "admin";
	private static final String ADMIN_PASSWORD = "admin";

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

	@Test
	void testMockMvcNull() {
		Assertions.assertNotNull(mockMvc);
	}

	@Test
	void testListUser() throws Exception {
		// for user, password
		print(mockMvc.perform(withUser("", "")).andExpect(MockMvcResultMatchers.status().isUnauthorized()));
		print(mockMvc.perform(withUser(USER_NAME, "")).andExpect(MockMvcResultMatchers.status().isUnauthorized()));
		print(mockMvc.perform(withUser("", USER_PASSWORD)).andExpect(MockMvcResultMatchers.status().isUnauthorized()));
		print(mockMvc.perform(withUser(USER_NAME, USER_PASSWORD)).andExpect(MockMvcResultMatchers.status().isOk()));

		// for admin. password
		print(mockMvc.perform(withUser(ADMIN_USER_NAME, ""))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized()));
		print(mockMvc.perform(withUser("", ADMIN_PASSWORD)).andExpect(MockMvcResultMatchers.status().isUnauthorized()));
		print(mockMvc.perform(withUser(ADMIN_USER_NAME, ADMIN_PASSWORD))
				.andExpect(MockMvcResultMatchers.status().isOk()));

		print(mockMvc.perform(withUser("wronguser", "wrongpassword"))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized()));
	}

	@Test
	void testSortColum() throws Exception {
		MockHttpServletRequestBuilder request = withUser(ADMIN_USER_NAME, ADMIN_PASSWORD);
		print(mockMvc.perform(withField(request, SORT_COLUMN)).andExpect(MockMvcResultMatchers.status().isOk()));
		print(mockMvc.perform(withField(request, SORT_ORDER_ASC)).andExpect(MockMvcResultMatchers.status().isOk()));
		print(mockMvc.perform(withField(request, SORT_ORDER_DESC)).andExpect(MockMvcResultMatchers.status().isOk()));
	}

	@Test
	void testSearch() throws Exception {
		MockHttpServletRequestBuilder request = withUser(ADMIN_USER_NAME, ADMIN_PASSWORD);
		print(mockMvc.perform(withField(request, SEARCH_TEXT_EMPTY)).andExpect(MockMvcResultMatchers.status().isOk()));
		print(mockMvc.perform(withField(request, SEARCH_TEXT_NON_EMPTY))
				.andExpect(MockMvcResultMatchers.status().isOk()));
	}

	@Test
	void testStart() throws Exception {
		MockHttpServletRequestBuilder request = withUser(ADMIN_USER_NAME, ADMIN_PASSWORD);
		print(mockMvc.perform(withField(request, START_FROM_FIRST)).andExpect(MockMvcResultMatchers.status().isOk()));
		print(mockMvc.perform(withField(request, START_FROM_OTHER)).andExpect(MockMvcResultMatchers.status().isOk()));
	}

	@Test
	void testPerPage() throws Exception {
		MockHttpServletRequestBuilder request = withUser(ADMIN_USER_NAME, ADMIN_PASSWORD);
		print(mockMvc.perform(withField(request, PER_PAGE_ZERO)).andExpect(MockMvcResultMatchers.status().isOk()));
		print(mockMvc.perform(withField(request, PER_PAGE_NEGATIVE)).andExpect(MockMvcResultMatchers.status().isOk()));
		print(mockMvc.perform(withField(request, PER_PAGE_POSITIVE)).andExpect(MockMvcResultMatchers.status().isOk()));
	}

	private RequestBuilder withField(MockHttpServletRequestBuilder request, int condition) {
		switch (condition) {
		case SORT_COLUMN:
			return request.param("order[0][column]", columnName());
		case SORT_ORDER_ASC:
			return request.param("order[0][dir]", "asc");
		case SORT_ORDER_DESC:
			return request.param("order[0][dir]", "desc");
		case SEARCH_TEXT_EMPTY:
			return request.param("search[value]", "");
		case SEARCH_TEXT_NON_EMPTY:
			return request.param("search[value]", "abc");
		case START_FROM_FIRST:
			return request.param("start", "0");
		case START_FROM_OTHER:
			return request.param("start", "10");
		case PER_PAGE_ZERO:
			return request.param("length", "0");
		case PER_PAGE_NEGATIVE:
			return request.param("length", "-10");
		case PER_PAGE_POSITIVE:
			return request.param("length", "20");
		default:
			return request.param("length", "20");
		}
	}

	private MockHttpServletRequestBuilder withUser(String user, String password) {
		return MockMvcRequestBuilders.post(host() + "v1/data")
				.with(SecurityMockMvcRequestPostProcessors.httpBasic(user, password));
	}

	private void print(ResultActions action) throws Exception {
		action.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	private String columnName() {
		Field[] fields = TestData.class.getDeclaredFields();
		return fields[new Random().nextInt(fields.length)].getName();
	}

	private String host() {
		return "http://localhost:" + port + "/";
	}
}
