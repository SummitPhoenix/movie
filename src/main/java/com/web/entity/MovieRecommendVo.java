package com.web.entity;

import lombok.Data;

@Data
public class MovieRecommendVo {
	private String id;
	private String name;
	private String score;
	private String type;
}
