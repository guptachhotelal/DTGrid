package com.dt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.builder.SpringApplicationBuilder;

class DTGridApplicationTest extends BaseApplicationTest {

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
