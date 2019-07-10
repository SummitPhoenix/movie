package com.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.entity.Comment;
import com.web.entity.ImageText;
import com.web.entity.Movie;
import com.web.entity.MovieRecommendVo;
import com.web.entity.MovieVo;
import com.web.mapper.MovieMapper;
import com.web.service.MovieService;
import com.web.util.TextInput;

@Service("movieService")
public class MovieServcieImpl implements MovieService{
	
	@Autowired
	private MovieMapper movieMapper;
	
	@Autowired
	private TextInput textInput;
	
	@Override
	public Movie inputMovieInfo() {
		Movie movie = textInput.inputMovie();
		movieMapper.inputData(movie);
		return movie;
	}
	
	@Override
	public List<Map<String,String>> getPopularMovieInfo() {
		return movieMapper.getPopularMovie();
	}
	
	@Override
	public List<Map<String, String>> getHighScoreMovieInfo() {
		return movieMapper.getHighScoreMovie();
	}
	
	@Override
	public List<Map<String,String>> getTypeMovieInfo(String type) {
		return movieMapper.getTypeMovie(type);
	}
	
	@Override
	public Movie showMovieInfo(String name) {
		return movieMapper.getMovieByName(name);
	}

	@Override
	public Movie searchMovieInfo(String name) {
		return movieMapper.searchMovieByName(name);
	}

	@Override
	public String getMovieFilmLength(String name) {
		return movieMapper.getMovieFilmLength(name);
	}

	@Override
	public List<Map<String, String>> getRankList(String type, int page) {
		return movieMapper.getTypeRankList(type,page);
	}

	@Override
	public List<MovieVo> getTwenty() {
		return movieMapper.getTwenty();
	}

	@Override
	public String getMovieIdByMovieName(String movieName) {
		return movieMapper.getMovieIdByMovieName(movieName); 
	}

	@Override
	public void addComment(Comment comment) {
		movieMapper.addComment(comment);
	}

	@Override
	public List<Comment> getComment(String movieName, int page) {
		return movieMapper.getComment(movieName,page);
	}

	@Override
	public List<Map<String, String>> getHighScoreScreenMovieInfo(int page) {
		return movieMapper.getHighScoreScreenMovie(page);
	}
	
	@Override
	public List<Map<String, String>> getPopularScreenMovieInfo(int page) {
		return movieMapper.getPopularScreenMovie(page);
	}

	@Override
	public List<Map<String, String>> getBoxOfficeScreenMovieInfo(int page) {
		return movieMapper.getBoxOfficeScreenMovie(page);
	}

	@Override
	public MovieRecommendVo getSimpleMovieInfo(String id) {
		return movieMapper.getSimpleMovieInfo(id);
	}

	@Override
	public List<MovieVo> getTwoTypes(String interest1, String interest2) {
		return movieMapper.getTwoTypes(interest1,interest2);
	}

	@Override
	public List<ImageText> getTitle() {
		return movieMapper.getTitle();
	}

	@Override
	public void deleteComment(String movieId, String username) {
		movieMapper.deleteComment(movieId,username);
	}

	@Override
	public void plusAssessNum(String movieName) {
		movieMapper.plusAssessNum(movieName);
	}
	
}
