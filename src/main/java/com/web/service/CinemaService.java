package com.web.service;

import java.util.List;

import com.web.entity.Cinema;
import com.web.entity.CinemaList;
import com.web.entity.SingleSchedule;

public interface CinemaService {
	/**
	 * 输入电影名获取影院ID列表
	 * @param movieName
	 * @return
	 */
	CinemaList getCinemaListInfo(String movieName);
	/**
	 * 输入影院ID获取影院名
	 * @param movieName
	 * @return
	 */
	String getCinemaNameInfo(String cinemaId);
	/**
	 * 获得影院信息
	 * @param cinemaName
	 * @return
	 */
	Cinema getCinemaInfo(String cinemaName);
	/**
	 * 获得影院电影排片时间
	 * @param cinemaName
	 * @param movieName
	 * @return
	 */
	String[] getScheduleList(String cinemaName, String movieName);
	/**
	 * 获得单个排片信息
	 * @param scheduleId
	 * @return
	 */
	SingleSchedule getSingleSchedule(String scheduleId);
	/**
	 * 获得正在上映电影列表
	 * @return
	 */
	List<String> getScreenMovieName();
	/**
	 * 获得已被选过的座位
	 * @param scheduleId 
	 * @return
	 */
	String getSelectedSeats(String scheduleId);
	
	/**
	 * 选择电影插入影院列表
	 * @return
	 */
	void updateCinemaList(String movieName, String cinemaId);
	
	/**
	 * 插入影院信息
	 * @param service 
	 * @param phone 
	 * @param address 
	 * @param cinemaName 
	 * @return
	 */
	Integer insertCinemaInfo(String cinemaName, String address, String phone, String service);
	
	/**
	 * 根据电影名获取电影ID
	 * @param movieName
	 * @return
	 */
	String getMovieId(String movieName);
	/**
	 * 根据影院明获取影院ID
	 * @param cinemaName
	 * @return
	 */
	String getCinemaId(String cinemaName);
	
	/**
	 * 更新SelectedSeats
	 * @param scheduleId
	 * @param newSelectedSeats
	 */
	int setSelectedSeats(String scheduleId, String selectedSeats, String seatId);
	
	/**
	 * 插入时刻表中的一条记录
	 * @param screenDate
	 * @param screenTime
	 * @param language
	 * @param videoHall
	 * @param price
	 */
	Integer insertSingleSchedule(SingleSchedule singeleSchedule);
	
	/**
	 * 将时刻表中一条记录插入时刻表列表
	 * @param cinemaId
	 * @param movieName
	 * @param scheduleId
	 */
	void insertMovieSchedule(String cinemaId, String movieName, String schedules);
	
	/**
	 * 查询该电影上映影院是否有schedules
	 * @param cinemaId
	 * @param movieName
	 * @return
	 */
	String getSchedules(String cinemaId, String movieName);
	
	/**
	 * 将时刻表中一条记录更新时刻表列表
	 * @param cinemaId
	 * @param movieName
	 * @param scheduleId
	 */
	void updateMovieSchedule(String cinemaId, String movieName, String schedules);
	/**
	 * 修改电影状态
	 */
	void updateMovieStatus(String movieName, int status);
	/**
	 * 为电影添加影院
	 */
	void addCinemaToCinemaList(String movieName, String cinemaName);
	
}
