package com.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.entity.Cinema;
import com.web.entity.SingleSchedule;

@Mapper
public interface CinemaMapper {
	
	String getCinemaList(String movieName);
	
	String getCinemaName(String cinemaId);
	
	Cinema getCinema(String cinemaName);
	
	String getScheduleList(String cinemaName,String movieName);
	
	SingleSchedule getSingleSchedule(String scheduleId);
	
	List<String> getScreenMovieName();

	String getSelectedSeatsInfo(String scheduleId);

	Integer insertCinemaInfo(String cinemaName, String address, String phone, String service);

	String getMovieId(String movieName);

	String getCinemaId(String cinemaName);

	Integer updateCinemaList(String movieName, String cinemaId);

	int setSelectedSeats(String scheduleId, String selectedSeats, String seatId);

	Integer insertSingleSchedule(SingleSchedule singleSchedule);

	void insertMovieSchedule(String cinemaId, String movieId, String schedules);

	String getSchedules(String cinemaId, String movieName);

	void updateMovieSchedule(String cinemaId, String movieName, String schedules);

	void updateMovieStatus(String movieName, int status);
	
	List<String> getAllCinema();

	void addCinemaToCinemaList(String movieName, String cinemaName);
}
