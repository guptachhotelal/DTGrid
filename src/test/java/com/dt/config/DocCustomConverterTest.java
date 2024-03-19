package com.dt.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.dt.entity.TestData;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.core.jackson.TypeNameResolver.Options;
import io.swagger.v3.oas.annotations.media.Schema;

class DocCustomConverterTest {

	@Test
	void testConstructor() {
		ObjectMapper mapper = new ObjectMapper();
		try (MockedStatic<DocCustomConverter> mockedStatic = mockStatic(DocCustomConverter.class)) {
			DocCustomConverter.add(mapper);
			mockedStatic.verify(() -> DocCustomConverter.add(mapper));
		}
	}

	@Test
	void testQualifiedTypeNameResolverClassEmptyOptionsNull() {
		DocCustomConverter.QualifiedTypeNameResolver tnr = new DocCustomConverter.QualifiedTypeNameResolver();
		String name = tnr.nameForClass(TestData.class, Collections.emptySet());
		assertThat(name).isNull();
	}

	@Test
	void testQualifiedTypeNameResolverClassEmptyOptionsNonNull() {
		DocCustomConverter.QualifiedTypeNameResolver tnr = new DocCustomConverter.QualifiedTypeNameResolver();
		String name = tnr.nameForClass(TestData1.class, Collections.emptySet());
		assertThat(name).isNotNull();
	}

	@Test
	void testQualifiedTypeNameResolverClass() {
		DocCustomConverter.QualifiedTypeNameResolver tnr = new DocCustomConverter.QualifiedTypeNameResolver();
		Set<Options> options = new HashSet<>();
		options.add(Options.SKIP_API_MODEL);
		String name = tnr.nameForClass(TestData.class, options);
		assertThat(name).isNotNull();
		assertEquals(TestData.class.getSimpleName(), name);
	}
}

@Schema
class TestData1{}

