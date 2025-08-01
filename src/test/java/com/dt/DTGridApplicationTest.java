package com.dt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.UseMainMethod;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, useMainMethod = UseMainMethod.WHEN_AVAILABLE, classes = DTGridApplication.class)
public class DTGridApplicationTest {

	@Mock
	protected SpringApplicationBuilder springApplicationBuilder;

	@Test
	void contextLoads() {
		Assertions.assertTrue(true);
	}

	@Test
	void testConfigure() {
		DTGridApplication servletInitializer = new DTGridApplication();
		Mockito.when(springApplicationBuilder.sources(DTGridApplication.class)).thenReturn(springApplicationBuilder);
		SpringApplicationBuilder result = servletInitializer.configure(springApplicationBuilder);
		Mockito.verify(springApplicationBuilder).sources(DTGridApplication.class);
		Assertions.assertEquals(springApplicationBuilder, result);
	}
}
