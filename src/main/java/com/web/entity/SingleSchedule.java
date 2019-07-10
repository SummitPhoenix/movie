package com.web.entity;

import lombok.Data;

/**
 * 	单个时间表
 */
@Data
public class SingleSchedule {
	private String scheduleId;
	private String screenDate;
	private String screenTime;
	private String languageVersion;
	private String videoHall;
	private String price;
}
