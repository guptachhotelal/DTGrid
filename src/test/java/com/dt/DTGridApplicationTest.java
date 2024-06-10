package com.dt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

class DTGridApplicationTest extends BaseApplicationTest {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

	@Test
	void testConfigure() {
		DTGridApplication servletInitializer = new DTGridApplication();
		when(springApplicationBuilder.sources(DTGridApplication.class)).thenReturn(springApplicationBuilder);
		SpringApplicationBuilder result = servletInitializer.configure(springApplicationBuilder);
		assertNotNull(result);
		verify(springApplicationBuilder).sources(DTGridApplication.class);
		assertEquals(springApplicationBuilder, result);
	}
}
