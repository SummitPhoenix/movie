package com.web.entity;

import lombok.Data;

/**
 * 	时刻表
 */
@Data
public class MovieSchedule {
	private int cinemaId;
	private String movieId;
	private String[] schedules;
}
