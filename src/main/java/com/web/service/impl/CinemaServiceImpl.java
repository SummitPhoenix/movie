package com.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.entity.Cinema;
import com.web.entity.CinemaList;
import com.web.entity.SingleSchedule;
import com.web.mapper.CinemaMapper;
import com.web.service.CinemaService;

@Service("cinemaService")
public class CinemaServiceImpl implements CinemaService{
	
	@Autowired
	private CinemaMapper cinemaMapper;
	
	@Override
	public CinemaList getCinemaListInfo(String movieName) {
		String cinemaNames = cinemaMapper.getCinemaList(movieName);
		CinemaList cinemaList = new CinemaList();
		if(cinemaNames==null) {
			/**
			 * 显示所有影院
			 */
			List<String> list = cinemaMapper.getAllCinema();
			list.add("此电影还没有添加影院，为其添加影院");
			cinemaList.setCinemaId(list);
			return cinemaList;
		}
		String[] cinemalist = cinemaNames.split(":");
		List<String> list = new ArrayList<>();
		for(String s:cinemalist) {
			list.add(s);
		}
		cinemaList.setCinemaId(list);
		return cinemaList;
	}

	@Override
	public Cinema getCinemaInfo(String cinemaName) {
		return cinemaMapper.getCinema(cinemaName);
	}

	@Override
	public String[] getScheduleList(String cinemaName, String movieName) {
		return cinemaMapper.getScheduleList(cinemaName,movieName).split(":");
	}

	@Override
	public SingleSchedule getSingleSchedule(String scheduleId) {
		return cinemaMapper.getSingleSchedule(scheduleId);
	}

	@Override
	public List<String> getScreenMovieName() {
		return cinemaMapper.getScreenMovieName();
	}

	@Override
	public String getCinemaNameInfo(String cinemaId) {
		return cinemaMapper.getCinemaName(cinemaId);
	}

	@Override
	public String getSelectedSeats(String scheduleId) {
		return cinemaMapper.getSelectedSeatsInfo(scheduleId);
	}

	@Override
	public Integer insertCinemaInfo(String cinemaName, String address, String phone, String service) {
		return cinemaMapper.insertCinemaInfo(cinemaName, address, phone,service);
	}

	@Override
	public void updateCinemaList(String movieName, String cinemaId) {
		cinemaId = cinemaMapper.getCinemaList(movieName)+":"+cinemaId;
		cinemaMapper.updateCinemaList(movieName, cinemaId);
	}

	@Override
	public String getMovieId(String movieName) {
		return cinemaMapper.getMovieId(movieName);
	}

	@Override
	public String getCinemaId(String cinemaName) {
		return cinemaMapper.getCinemaId(cinemaName);
	}

	@Override
	public int setSelectedSeats(String scheduleId, String selectedSeats, String seatId) {
		return cinemaMapper.setSelectedSeats(scheduleId,selectedSeats, seatId);
	}

	@Override
	public Integer insertSingleSchedule(SingleSchedule singleSchedule) {
		return cinemaMapper.insertSingleSchedule(singleSchedule);
	}

	@Override
	public void insertMovieSchedule(String cinemaId, String movieName, String schedules) {
		String movieId = cinemaMapper.getMovieId(movieName);
		cinemaMapper.insertMovieSchedule(cinemaId, movieId, schedules);
	}

	@Override
	public String getSchedules(String cinemaId, String movieName) {
		return cinemaMapper.getSchedules(cinemaId, movieName);
	}

	@Override
	public void updateMovieSchedule(String cinemaId, String movieName, String schedules) {
		cinemaMapper.updateMovieSchedule(cinemaId, movieName, schedules);
	}

	@Override
	public void updateMovieStatus(String movieName, int status) {
		cinemaMapper.updateMovieStatus(movieName, status);
	}

	@Override
	public void addCinemaToCinemaList(String movieName, String cinemaName) {
		cinemaMapper.addCinemaToCinemaList(movieName, cinemaName);
	}
	
}
