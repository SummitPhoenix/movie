package com.web.entity;

import lombok.Data;

/**
 * 	影院上映电影列表
 */
@Data
public class ReleaseInfo {
	private String cinemaName;
	private String[] movieList;
	//TODO
}
