package com.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.entity.Cinema;
import com.web.entity.CinemaList;
import com.web.entity.Movie;
import com.web.entity.SingleSchedule;
import com.web.entity.Ticket;
import com.web.service.CinemaService;
import com.web.service.IUserService;
import com.web.service.MovieService;
import com.web.util.ConfigService;
import com.web.util.MailSender;

@Controller
public class CinemaController {

	@Autowired
	private CinemaService cinemaService;

	@Autowired
	private MovieService movieService;

	@Autowired
	private IUserService userService;

	/**
	 * 影院选择列表
	 */
	@RequestMapping(value = "cinemalist")
	public String showCinemaList(@RequestParam("moviename") String moviename, HttpServletRequest request, Model model,
			HttpSession session) {
		/**
		 * 判断用户是否登录
		 */
		if (session.getAttribute("phone") == null) {
			return "redirect:login";
		}
		Movie movie = null;
		boolean flag = true;
		if (ConfigService.movieList != null) {
			for (int i = 0; i < ConfigService.movieList.size(); i++) {
				if (moviename.equals(ConfigService.movieList.get(i).getName())) {
					movie = ConfigService.movieList.get(i);
					flag = false;
				}
			}
		}
		if (flag) {
			movie = movieService.showMovieInfo(moviename);
		}

		request.setAttribute("id", movie.getId());
		request.setAttribute("name", movie.getName());
		request.setAttribute("suffix", movie.getSuffix());
		request.setAttribute("score", movie.getScore());
		request.setAttribute("assessNum", movie.getAssessNum());
		request.setAttribute("director", movie.getDirector());
		request.setAttribute("screenWriter", movie.getScreenWriter());
		request.setAttribute("performer", movie.getPerformer());
		request.setAttribute("type", movie.getType());
		request.setAttribute("region", movie.getRegion());
		request.setAttribute("language", movie.getLanguage());
		request.setAttribute("releaseDate", movie.getReleaseDate());
		request.setAttribute("filmLength", movie.getFilmLength());
		request.setAttribute("alias", movie.getAlias());
		request.setAttribute("synopsis", movie.getSynopsis());
		request.setAttribute("release", movie.getRelease());

		CinemaList cinemaList = cinemaService.getCinemaListInfo(moviename);
		List<String> cinemaIds = cinemaList.getCinemaId();
		String[] cinemaNames = new String[10];
		for (int i = 0; i < cinemaIds.size(); i++) {
			cinemaNames[i] = cinemaService.getCinemaNameInfo(cinemaIds.get(i));
		}
		List<Map<String, String>> list = new ArrayList<>();

		for (int i = 0; i < cinemaNames.length; i++) {
			Cinema cinema = cinemaService.getCinemaInfo(cinemaNames[i]);
			if (cinema == null) {
				break;
			}
			Map<String, String> map = new HashMap<>();
			map.put("cinemaName", cinema.getCinemaName());
			map.put("address", cinema.getAddress());
			list.add(map);
		}
		model.addAttribute("list", list);
		return "cinemalist";
	}

	/**
	 * 选择场次详情页
	 */
	@RequestMapping(value = "cinema")
	public String showScheduleList(@RequestParam("cinemaname") String cinemaName,
			@RequestParam("moviename") String movieName, HttpServletRequest request, Model model) {
		String filmLength = "";
		boolean flag = true;
		if (ConfigService.movieList != null) {
			for (int i = 0; i < ConfigService.movieList.size(); i++) {
				if (movieName.equals(ConfigService.movieList.get(i).getName())) {
					filmLength = ConfigService.movieList.get(i).getFilmLength();
					ConfigService.movieList.remove(i);
					flag = false;
				}
			}
		}
		if (flag) {
			filmLength = movieService.getMovieFilmLength(movieName);
		}

		Cinema cinema = cinemaService.getCinemaInfo(cinemaName);
		request.setAttribute("cinemaname", cinema.getCinemaName());
		request.setAttribute("address", cinema.getAddress());
		request.setAttribute("phone", cinema.getPhone());
		request.setAttribute("service", cinema.getService());

		String[] schedules = cinemaService.getScheduleList(cinemaName, movieName);
		List<Map<String, String>> list = new ArrayList<>();
		for (int i = 0; i < schedules.length; i++) {
			SingleSchedule schedule = cinemaService.getSingleSchedule(schedules[i]);
			if (schedule == null) {
				break;
			}
			Map<String, String> map = new HashMap<>();

			map.put("cinemaName", cinemaName);
			map.put("movieName", movieName);
			map.put("scheduleId", schedule.getScheduleId());
			map.put("screenDate", schedule.getScreenDate());
			map.put("screenTime", schedule.getScreenTime());
			map.put("filmLength", filmLength);
			map.put("languageVersion", schedule.getLanguageVersion());
			map.put("videoHall", schedule.getVideoHall());
			map.put("price", schedule.getPrice());
			list.add(map);
		}
		model.addAttribute("list", list);
		return "cinema";
	}

	/**
	 * 选择座位
	 */
	@RequestMapping(value = "chooseSeat")
	public String chooseSeat(@RequestParam("movieName") String movieName, @RequestParam("cinemaName") String cinemaName,
			@RequestParam("scheduleId") String scheduleId, HttpServletRequest request, Model model) {
		Cinema cinema = cinemaService.getCinemaInfo(cinemaName);
		request.setAttribute("cinemaname", cinema.getCinemaName());
		request.setAttribute("address", cinema.getAddress());
		request.setAttribute("phone", cinema.getPhone());
		request.setAttribute("service", cinema.getService());

		Map<String, String> map = new HashMap<>();

		for (int i = 1; i < 7; i++) {
			for (int j = 0; j < 10; j++) {
				String s = "";
				switch (j) {
				case 0:
					s = "a";
					break;
				case 1:
					s = "b";
					break;
				case 2:
					s = "c";
					break;
				case 3:
					s = "d";
					break;
				case 4:
					s = "e";
					break;
				case 5:
					s = "f";
					break;
				case 6:
					s = "g";
					break;
				case 7:
					s = "h";
					break;
				case 8:
					s = "i";
					break;
				case 9:
					s = "j";
					break;
				default:
					break;
				}
				map.put(s + i, "0");
			}
		}

		String selectedSeats = cinemaService.getSelectedSeats(scheduleId);

		if (selectedSeats != null) {
			String[] seats = selectedSeats.split(":");
			for (String seat : seats) {
				map.put(seat, "1");
			}
		}

		map.put("cinemaName", cinemaName);
		map.put("movieName", movieName);
		map.put("scheduleId", scheduleId);
		model.addAttribute("map", map);
		return "chooseSeat";
	}

	/**
	 * 生成电影票
	 */
	@RequestMapping(value = "ticket")
	public String generateTicket(@RequestParam("movieName") String movieName,
			@RequestParam("cinemaName") String cinemaName, @RequestParam("scheduleId") String scheduleId,
			@RequestParam("seatId") String seatId, HttpServletRequest request, Model model, HttpSession session) {
		String filmLength = "";
		boolean flag = true;
		if (ConfigService.movieList != null) {
			for (int i = 0; i < ConfigService.movieList.size(); i++) {
				if (movieName.equals(ConfigService.movieList.get(i).getName())) {
					filmLength = ConfigService.movieList.get(i).getFilmLength();
					ConfigService.movieList.remove(i);
					flag = false;
				}
			}
		}
		if (flag) {
			filmLength = movieService.getMovieFilmLength(movieName);
		}

		Cinema cinema = cinemaService.getCinemaInfo(cinemaName);
		request.setAttribute("cinemaname", cinema.getCinemaName());
		request.setAttribute("address", cinema.getAddress());
		request.setAttribute("phone", cinema.getPhone());
		request.setAttribute("service", cinema.getService());

		SingleSchedule schedule = cinemaService.getSingleSchedule(scheduleId);
		/**
		 * 开场前一个小时不能购票
		 */
		Date date = new Date();
		date.setHours(date.getHours()-1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String now = sdf.format(date);
		System.out.println(now);
		System.out.println(schedule.getScreenDate()+" "+schedule.getScreenTime());
		Date ticketTime = null;
		try {
			ticketTime = sdf.parse(schedule.getScreenDate()+" "+schedule.getScreenTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(ticketTime.getTime());
		System.out.println(date.getTime());
		if(ticketTime.getTime()<date.getTime()) {
			return "timefail";
		}
		Map<String, String> map = new HashMap<>();

		map.put("cinemaName", cinemaName);
		map.put("movieName", movieName);
		map.put("scheduleId", schedule.getScheduleId());
		map.put("screenDate", schedule.getScreenDate());
		map.put("screenTime", schedule.getScreenTime());
		map.put("filmLength", filmLength);
		map.put("languageVersion", schedule.getLanguageVersion());
		map.put("videoHall", schedule.getVideoHall());

		String seat = "";
		switch (seatId.charAt(1)) {
		case 'a':
			seat = "1座";
			break;
		case 'b':
			seat = "2座";
			break;
		case 'c':
			seat = "3座";
			break;
		case 'd':
			seat = "4座";
			break;
		case 'e':
			seat = "5座";
			break;
		case 'f':
			seat = "6座";
			break;
		case 'g':
			seat = "7座";
			break;
		case 'h':
			seat = "8座";
			break;
		case 'i':
			seat = "9座";
			break;
		case 'j':
			seat = "10座";
			break;
		default:
			break;
		}
		map.put("row", seatId.charAt(0) + "排");
		map.put("seat", seat);

		map.put("price", schedule.getPrice());

		String ticketId = DigestUtils.md5Hex(scheduleId + seatId).toUpperCase();
		map.put("ticketId", ticketId);
		map.put("seatId", seatId);
		model.addAttribute("map", map);

		return "ticket";
	}

	/**
	 * 购票完成
	 */
	@RequestMapping(value = "addticket")
	public String saveTicket(@RequestParam("movieName") String movieName, @RequestParam("cinemaName") String cinemaName,
			@RequestParam("scheduleId") String scheduleId, @RequestParam("seatId") String seatId,
			HttpServletRequest request, Model model, HttpSession session) {

		/**
		 * 更新SelectedSeats到数据库
		 * 
		 * 加锁，防止重复购票
		 */
		String seatid = ":" + seatId.charAt(1) + seatId.charAt(0);
		// 此selectedSeats可能会在更新之前因为并发被修改
		String selectedSeats = cinemaService.getSelectedSeats(scheduleId);
		// 更新前确认是否重复
		int updateStatus = cinemaService.setSelectedSeats(scheduleId, selectedSeats, seatid);
//		selectedSeats = cinemaService.getSelectedSeats(scheduleId);
//		//更新后去重确认是否重复
//		String singleSeats[] = selectedSeats.split(":");
//		Set<String> set = new HashSet<>();
//		for(String s:singleSeats) {
//			set.add(s);
//		}
//		if(updateStatus != 1 || singleSeats.length != set.size()) {
//			return "ticketFail";
//		}
		if (updateStatus != 1) {
			return "ticketFail";
		}

		String seat = "";
		switch (seatId.charAt(1)) {
		case 'a':
			seat = "1座";
			break;
		case 'b':
			seat = "2座";
			break;
		case 'c':
			seat = "3座";
			break;
		case 'd':
			seat = "4座";
			break;
		case 'e':
			seat = "5座";
			break;
		case 'f':
			seat = "6座";
			break;
		case 'g':
			seat = "7座";
			break;
		case 'h':
			seat = "8座";
			break;
		case 'i':
			seat = "9座";
			break;
		case 'j':
			seat = "10座";
			break;
		default:
			break;
		}
		SingleSchedule schedule = cinemaService.getSingleSchedule(scheduleId);
		String ticketId = DigestUtils.md5Hex(scheduleId + seatId).toUpperCase();

		String phone = (String) session.getAttribute("phone");
		/**
		 * 用户订票成功邮件发送
		 */
		if (phone != null) {
			Ticket ticket = new Ticket();
			ticket.setCinemaName(cinemaName);
			ticket.setMovieName(movieName);
			ticket.setScreenDate(schedule.getScreenDate());
			ticket.setScreenTime(schedule.getScreenTime());
			ticket.setVideoHall(schedule.getVideoHall());
			ticket.setRow(seatId.charAt(0) + "排");
			ticket.setSeat(seat);
			ticket.setPrice(schedule.getPrice());
			ticket.setTicketId(ticketId);
			ticket.setPhone(phone);

			userService.addTicket(ticket);
			// 通过用户手机号获取邮箱
			String email = userService.getEmail(phone);
			ticket.setEmail(email);
			MailSender.SendMail(ticket);
		}

		return "redirect:index";
	}

	/**
	 * 管理端上映列表
	 */
	@RequestMapping(value = "administrator")
	public String administrator(Model model) {
		List<String> list = cinemaService.getScreenMovieName();
		model.addAttribute("list", list);
		return "administrator";
	}

	/**
	 * 为上映电影添加影院信息 影院列表
	 */
	@RequestMapping(value = "addcinema")
	public String addCinema(@RequestParam("moviename") String movieName, HttpSession session, Model model) {
		CinemaList cinemaList = cinemaService.getCinemaListInfo(movieName);
		if (cinemaList != null) {
			List<String> cinemaIds = cinemaList.getCinemaId();
			List<String> list = new ArrayList<>();
			for (int i = 0; i < cinemaIds.size(); i++) {
				String cinemaName = cinemaService.getCinemaNameInfo(cinemaIds.get(i));
				if(cinemaName==null) {
					cinemaName = "此电影还没有添加影院，为其添加影院";
				}
				list.add(cinemaName);
			}
			session.setAttribute("movieName", movieName);
			model.addAttribute("list", list);
		}
		return "addcinema";
	}

	/**
	 * 在影院列表界面录入影院信息
	 */
	@RequestMapping(value = "cinemaInfoImport")
	public String addSchedules(@RequestParam("cinemaName") String cinemaName, @RequestParam("address") String address,
			@RequestParam("phone") String phone, @RequestParam("service") String service, HttpSession session) {
		if (cinemaName != null) {
			cinemaService.insertCinemaInfo(cinemaName, address, phone, service);
		}
		// 电影添加影院列表
		String movieName = (String) session.getAttribute("movieName");
		String cinemaId = cinemaService.getCinemaId(cinemaName);
		cinemaService.updateCinemaList(movieName, cinemaId);
		System.out.println(cinemaId);
		try {
			movieName = URLEncoder.encode(movieName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:addcinema?moviename=" + movieName;
	}

	/**
	 * 添加时刻表中的一条记录
	 */
	@RequestMapping(value = "addSingleSchedule")
	public String addSingleSchedule(@RequestParam("cinemaName") String cinemaName,
			@RequestParam("screenDate") String screenDate, @RequestParam("screenTime") String screenTime,
			@RequestParam("languageVersion") String languageVersion, @RequestParam("videoHall") String videoHall,
			@RequestParam("price") String price, HttpSession session) {
		String movieName = (String) session.getAttribute("movieName");
		SingleSchedule singleSchedule = new SingleSchedule();
		singleSchedule.setScreenDate(screenDate);
		singleSchedule.setScreenTime(screenTime);
		singleSchedule.setLanguageVersion(languageVersion);
		singleSchedule.setVideoHall(videoHall);
		singleSchedule.setPrice(price);

		cinemaService.insertSingleSchedule(singleSchedule);
		int scheduleId = Integer.parseInt(singleSchedule.getScheduleId());
		// 插入时刻表列表
		String cinemaId = cinemaService.getCinemaId(cinemaName);

		String schedules = cinemaService.getSchedules(cinemaId, movieName);
		if (schedules == null) {
			schedules = scheduleId + "";
			cinemaService.insertMovieSchedule(cinemaId, movieName, schedules);
		} else {
			schedules = schedules + ":" + scheduleId;
			cinemaService.updateMovieSchedule(cinemaId, movieName, schedules);
		}

		try {
			movieName = URLEncoder.encode(movieName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:addcinema?moviename=" + movieName;
	}

	/**
	 * 修改电影状态
	 */
	@RequestMapping(value = "updateMovieStatus")
	public String updateMovieStatus(@RequestParam("movieName") String movieName,
			@RequestParam("movieStatus") String movieStatus, HttpSession session, Model model) {
		if (!("上映".equals(movieStatus) || "未上映".equals(movieStatus) || "可播放".equals(movieStatus))) {
			return "redirect:index";
		}
		int status;
		if ("上映".equals(movieStatus)) {
			status = 1;
		} else if ("未上映".equals(movieStatus)) {
			status = 0;
		} else {
			status = 2;
		}
		cinemaService.updateMovieStatus(movieName, status);
		try {
			movieName = URLEncoder.encode(movieName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:addcinema?moviename=" + movieName;
	}
	
	/**
	 * 为电影添加影院
	 */
	@RequestMapping(value = "addCinemaToCinemaList")
	public String addCinemaToCinemaList(@RequestParam("moviename") String movieName,
			@RequestParam("cinemaName") String cinemaName) {
		cinemaService.addCinemaToCinemaList(movieName, cinemaName);
		try {
			movieName = URLEncoder.encode(movieName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:addcinema?moviename=" + movieName;
	}
}