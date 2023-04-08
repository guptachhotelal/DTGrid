package com.dt.entity;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestData implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private long dob;
	private String phone;
	private String email;
	private String city;
	private String pincode;
	private String state;
	private long createDate;
	private long updateDate;
}