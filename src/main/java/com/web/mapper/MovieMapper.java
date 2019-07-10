package com.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.web.entity.Comment;
import com.web.entity.ImageText;
import com.web.entity.Movie;
import com.web.entity.MovieRecommendVo;
import com.web.entity.MovieVo;

@Mapper
public interface MovieMapper {
	Integer inputData(Movie movie);
	
	List<Map<String,String>> getPopularMovie();
	
	List<Map<String,String>> getHighScoreMovie();
	
	List<Map<String,String>> getTypeMovie(String type);
	
	Movie getMovieByName(String name);

	Movie searchMovieByName(String name);

	String getMovieFilmLength(String name);

	List<Map<String, String>> getTypeRankList(String type, int page);

	List<Map<String, String>> getHighScoreScreenMovie(int page);

	List<MovieVo> getTwenty();

	String getMovieIdByMovieName(String movieName);
	
	void addComment(Comment comment);
	
	List<Comment> getComment(String movieName,int page);

	List<Map<String, String>> getPopularScreenMovie(int page);

	List<Map<String, String>> getBoxOfficeScreenMovie(int page);

	MovieRecommendVo getSimpleMovieInfo(String id);

	List<MovieVo> getTwoTypes(String interest1, String interest2);

	List<ImageText> getTitle();

	void deleteComment(String movieId,String username);

	void plusAssessNum(String movieName);
}
