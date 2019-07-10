package com.web.entity;

import lombok.Data;

/**
 * 	电影详情
 */
@Data
public class Movie {
	private String id;
	private String name;
	private String suffix;
	//评分
	private double score;
	//评价人数
	private int assessNum;
	
	private String director;
	private String screenWriter;
	private String performer;
	private String type;
	//地区
	private String region;
	private String language;
	//上映日期
	private String releaseDate;
	private String filmLength;
	//別名
	private String alias;
	
	//剧情简介
	private String synopsis;
	/**
	 * 0表示未上映
	 * 1表示正在上映 
	 * 2表示未上映但可播放预告片
	 */
	private int release;
}
