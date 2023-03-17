package com.dt.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Field;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.dt.DTGridApplicationTest;
import com.dt.config.DocConstant;
import com.dt.entity.TestData;

public class DataControllerTest extends DTGridApplicationTest {

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

	@Test
	public void testListUser() throws Exception {
		// for user, password
		print(mockMvc.perform(withUser("", "")).andExpect(status().isUnauthorized()));
		print(mockMvc.perform(withUser("user", "")).andExpect(status().isUnauthorized()));
		print(mockMvc.perform(withUser("", "user")).andExpect(status().isUnauthorized()));
		print(mockMvc.perform(withUser("user", "user")).andExpect(status().isOk()));

		// for admin.password
		print(mockMvc.perform(withUser("admin", "")).andExpect(status().isUnauthorized()));
		print(mockMvc.perform(withUser("", "admin")).andExpect(status().isUnauthorized()));
		print(mockMvc.perform(withUser("admin", "admin")).andExpect(status().isOk()));
		print(mockMvc.perform(withUser("wronguser", "wrongpassword")).andExpect(status().isUnauthorized()));
	}

	@Test
	public void testSortColum() throws Exception {
		MockHttpServletRequestBuilder request = withUser("admin", "admin");
		print(mockMvc.perform(withField(request, SORT_COLUMN)).andExpect(status().isOk()));
		print(mockMvc.perform(withField(request, SORT_ORDER_ASC)).andExpect(status().isOk()));
		print(mockMvc.perform(withField(request, SORT_ORDER_DESC)).andExpect(status().isOk()));
	}

	@Test
	public void testSearch() throws Exception {
		MockHttpServletRequestBuilder request = withUser("admin", "admin");
		print(mockMvc.perform(withField(request, SEARCH_TEXT_EMPTY)).andExpect(status().isOk()));
		print(mockMvc.perform(withField(request, SEARCH_TEXT_NON_EMPTY)).andExpect(status().isOk()));
	}

	@Test
	public void testStart() throws Exception {
		MockHttpServletRequestBuilder request = withUser("admin", "admin");
		print(mockMvc.perform(withField(request, START_FROM_FIRST)).andExpect(status().isOk()));
		print(mockMvc.perform(withField(request, START_FROM_OTHER)).andExpect(status().isOk()));
	}

	@Test
	public void testPerPage() throws Exception {
		MockHttpServletRequestBuilder request = withUser("admin", "admin");
		print(mockMvc.perform(withField(request, PER_PAGE_ZERO)).andExpect(status().isOk()));
		print(mockMvc.perform(withField(request, PER_PAGE_NEGATIVE)).andExpect(status().isOk()));
		print(mockMvc.perform(withField(request, PER_PAGE_POSITIVE)).andExpect(status().isOk()));
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

	private MockHttpServletRequestBuilder withRequest() {
		return post(host() + DocConstant.API_VERSION + "/" + DocConstant.TAG_DATA_URL);
	}

	private MockHttpServletRequestBuilder withUser(String user, String password) {
		return withRequest().with(httpBasic(user, password));
	}

	private void print(ResultActions action) throws Exception {
		action.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	private String columnName() {
		Field[] fields = TestData.class.getDeclaredFields();
		return fields[new Random().nextInt(fields.length)].getName();
	}
}
