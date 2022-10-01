package com.dt.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.lang.reflect.Field;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.dt.DTGridApplicationTest;
import com.dt.entity.TestData;

public class DataControllerTest extends DTGridApplicationTest {

	@Test
	public void testListEmptyUserEmptyPasword() throws Exception {
		assertEquals(HttpStatus.FOUND.value(), testCommon().getResponse().getStatus());
	}

	@Test
	@WithMockUser(username = "user", password = "", roles = { "USER" })
	public void testListEmptyUserPasword() throws Exception {
		assertEquals(HttpStatus.OK.value(), testCommon().getResponse().getStatus());
	}

	@Test
	@WithMockUser(username = "", password = "user", roles = { "USER" })
	public void testListUserEmptyPasword() throws Exception {
		assertEquals(HttpStatus.OK.value(), testCommon().getResponse().getStatus());
	}

	@Test
	@WithMockUser(username = "user", password = "user", roles = { "USER" })
	public void testListAuthorizedUserPassword() throws Exception {
		assertEquals(HttpStatus.OK.value(), testCommon().getResponse().getStatus());
	}

	@Test
	@WithMockUser(username = "admin", password = "", roles = { "ADMIN" })
	public void testListAdminEmptyPassword() throws Exception {
		assertEquals(HttpStatus.OK.value(), testCommon().getResponse().getStatus());
	}

	@Test
	@WithMockUser(username = "", password = "admin", roles = { "ADMIN" })
	public void testListEmptyAdminPassword() throws Exception {
		assertEquals(HttpStatus.OK.value(), testCommon().getResponse().getStatus());
	}

	@Test
	@WithMockUser(username = "admin", password = "admin", roles = { "ADMIN" })
	public void testListAuthorizedAdminPassword() throws Exception {
		assertEquals(HttpStatus.OK.value(), testCommon().getResponse().getStatus());
	}

	private MvcResult testCommon() throws Exception {
		RequestBuilder builder = post(host() + "v1/data").param("order[0][column]", columnName());
		return mockMvc.perform(builder).andDo(MockMvcResultHandlers.print()).andReturn();
	}

	private String columnName() {
		Field[] fields = TestData.class.getDeclaredFields();
		return fields[new Random().nextInt(fields.length)].getName();
	}
}
