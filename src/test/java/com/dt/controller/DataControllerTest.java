package com.dt.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Field;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.dt.DTGridApplicationTest;
import com.dt.config.DocConstant;
import com.dt.entity.TestData;

public class DataControllerTest extends DTGridApplicationTest {

	@Test
	public void testListEmptyUserEmptyPasword1() throws Exception {
		print(mockMvc.perform(request("", "")).andExpect(status().isUnauthorized()));
	}

	@Test
	public void testListUserEmptyPasword() throws Exception {
		print(mockMvc.perform(request("user", "")).andExpect(status().isUnauthorized()));
	}

	@Test
	public void testListEmptyUserPasword() throws Exception {
		print(mockMvc.perform(request("", "user")).andExpect(status().isUnauthorized()));
	}

	@Test
	public void testListAuthorizedUserPassword() throws Exception {
		print(mockMvc.perform(request("user", "user")).andExpect(status().isOk()));
	}

	@Test
	public void testListAdminEmptyPassword() throws Exception {
		print(mockMvc.perform(request("admin", "")).andExpect(status().isUnauthorized()));
	}

	@Test
	public void testListEmptyAdminPassword() throws Exception {
		print(mockMvc.perform(request("", "admin")).andExpect(status().isUnauthorized()));
	}

	@Test
	public void testListAuthorizedAdminPassword() throws Exception {
		print(mockMvc.perform(request("admin", "admin")).andExpect(status().isOk()));
	}

	@Test
	public void testListWrongUserWrongPassword() throws Exception {
		print(mockMvc.perform(request("wronguser", "wrongpassword")).andExpect(status().isUnauthorized()));
	}

	private RequestBuilder request(String user, String password) {
		return post(host() + DocConstant.API_VERSION + "/" + DocConstant.TAG_DATA_URL)
				.param("order[0][column]", columnName()).with(httpBasic(user, password));
	}

	private void print(ResultActions action) throws Exception {
		action.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	private String columnName() {
		Field[] fields = TestData.class.getDeclaredFields();
		return fields[new Random().nextInt(fields.length)].getName();
	}
}
