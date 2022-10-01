package com.dt.config;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.jackson.TypeNameResolver;
import io.swagger.v3.oas.annotations.media.Schema;

public class DocCustomConverter extends ModelResolver {

	public DocCustomConverter(ObjectMapper mapper) {
		super(mapper, new QualifiedTypeNameResolver());
	}

	public static void add(ObjectMapper mapper) {
		ModelConverters.getInstance().addConverter(new DocCustomConverter(mapper));
	}

	static class QualifiedTypeNameResolver extends TypeNameResolver {
		@Override
		protected String nameForClass(Class<?> cls, Set<Options> options) {
			String clazzName = cls.getSimpleName();
			if (options.contains(Options.SKIP_API_MODEL)) {
				return clazzName;
			}
			Schema model = cls.getAnnotation(Schema.class);
			return Objects.isNull(model) ? null : model.name().trim();
		}
	}
}
