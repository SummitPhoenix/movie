package com.web.service;

import java.util.List;
import java.util.Map;

import com.web.entity.Comment;
import com.web.entity.ImageText;
import com.web.entity.Movie;
import com.web.entity.MovieRecommendVo;
import com.web.entity.MovieVo;

public interface MovieService {
	/**
	 *  插入电影信息到数据库
	 * @param movie
	 * @return
	 */
	Movie inputMovieInfo();
	
	/**
	 * 获取最近流行电影 页面展示
	 * @return
	 */
	List<Map<String,String>> getPopularMovieInfo();
	
	/**
	 * 获取高分电影电影 页面展示
	 * @return
	 */
	List<Map<String,String>> getHighScoreMovieInfo();
	
	/**
	 * 具体展示电影信息 单独页面
	 * @param name
	 * @return
	 */
	Movie showMovieInfo(String name);

	/**
	 * 搜索框模糊查询电影信息 单独页面
	 * @param name
	 * @return
	 */
	Movie searchMovieInfo(String name);
	
	/**
	 * 获取某类型的电影 页面展示
	 * @param type
	 * @return
	 */
	List<Map<String, String>> getTypeMovieInfo(String type);
	/**
	 * 根据电影名获取片长
	 * @param name
	 * @return
	 */
	String getMovieFilmLength(String name);

	/**
	 * 获取某一类电影排行分页
	 * @param type
	 * @param page
	 * @return
	 */
	List<Map<String, String>> getRankList(String type, int page);

	/**
	 * 获得高分榜前20的评分,评价人数,票房
	 */
	List<MovieVo> getTwenty();

	/**
	 * 通过电影名获取电影ID
	 * @param movieName
	 * @return 
	 */
	String getMovieIdByMovieName(String movieName);

	/**
	 * 插入一条评论
	 * @param comment
	 */
	void addComment(Comment comment);

	/**
	 * 获得一个电影的全部评论
	 * @param movieName
	 * @return
	 */
	List<Comment> getComment(String movieName, int page);

	/**
	 * 高分
	 * 获取正在热映电影列表
	 * @param page 
	 * @return
	 */
	List<Map<String, String>> getHighScoreScreenMovieInfo(int page);
	
	/**
	 * 评论人数多
	 * 获取正在热映电影列表
	 * @param page
	 * @return
	 */
	List<Map<String, String>> getPopularScreenMovieInfo(int page);

	/**
	 * 票房高
	 * 获取正在热映电影列表
	 * @param page
	 * @return
	 */
	List<Map<String, String>> getBoxOfficeScreenMovieInfo(int page);

	/**
	 * 获得单个电影简单信息
	 * @param id
	 */
	MovieRecommendVo getSimpleMovieInfo(String id);

	/**
	 * 获取用户两个感兴趣的电影类型对应信息
	 * @param interest1
	 * @param interest2
	 * @return
	 */
	List<MovieVo> getTwoTypes(String interest1, String interest2);

	List<ImageText> getTitle();

	/**
	 * 删除评论
	 */
	void deleteComment(String movieId, String username);

	/**
	 * 评论人数+1
	 */
	void plusAssessNum(String movieName);
	
}
