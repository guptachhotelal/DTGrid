package com.dt;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = DTGridApplication.class)
@AutoConfigureMockMvc
public class DTGridApplicationTest {

	@LocalServerPort
	private int port;

	@Resource
	protected MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

	@Test
	void testMockMvcNull() {
		assertThat(mockMvc).isNotNull();
	}

	protected String host() {
		return "http://localhost:" + port + "/";
	}
}
