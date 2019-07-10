package com.web.entity;

import lombok.Data;

/**
 * 	影院信息
 */
@Data
public class Cinema {
	private String cinemaId;
	private String cinemaName;
	private String address;
	private String phone;
	private String service;
}
