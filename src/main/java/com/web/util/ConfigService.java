package com.web.util;

import java.util.List;

import com.web.entity.Movie;

/**
 * 服务器暂时存储电影信息
 * 减少数据库查询次数
 */
public class ConfigService {
	public static List<Movie> movieList;
	public static List<String> recommendMovieIdList;
	public static List<String> personalRecommendMovieIdList;
}
