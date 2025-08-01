package com.dt.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

@ExtendWith(SpringExtension.class)
class HomeControllerTest {

	private HomeController homeController;

	@Mock
	protected Model model;

	@BeforeEach
	public void before() {
		homeController = new HomeController();
	}

	@Test
	void testHome() {
		String returnValue = homeController.home(model);
		assertTrue(returnValue.contains("jsp/home"));
	}

	@Test
	void testLoginValid() {
		String returnValue = homeController.login(model, "", "");
		assertTrue(returnValue.contains("common/login"));
	}

	@Test
	void testLoginInvalid() {
		String returnValue = homeController.login(model, "xyz", null);
		verify(model, times(1)).addAttribute("msg", "Your username or password are invalid.");
		assertTrue(returnValue.contains("common/login"));
	}

	@Test
	void testLogout() {
		String returnValue = homeController.login(model, null, "logout");
		verify(model, times(1)).addAttribute("msg", "You have been logged out successfully.");
		assertTrue(returnValue.contains("common/login"));
	}
}
