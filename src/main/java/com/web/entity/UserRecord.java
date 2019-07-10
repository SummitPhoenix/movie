package com.web.entity;

import lombok.Data;

/**
 * 	用户访问信息
 */
@Data
public class UserRecord {
	private String phone;
	private int plot;
	private int action;
	private int comdy;
	private int love;
	private int science;
	private int suspense;
}
