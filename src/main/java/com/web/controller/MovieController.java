package com.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.entity.Comment;
import com.web.entity.ImageText;
import com.web.entity.Movie;
import com.web.entity.MovieRecommendVo;
import com.web.entity.MovieVo;
import com.web.entity.UserRecord;
import com.web.service.IUserService;
import com.web.service.MovieService;
import com.web.util.ConfigService;

@Controller
public class MovieController {

	@Autowired
	private MovieService movieService;

	@Autowired
	private IUserService userService;

	/**
	 * 主页
	 */
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request) {
		/**
		 * model绑定一个List<Map<String,String>>
		 */
		model.addAttribute("list", movieService.getPopularMovieInfo());
		List<ImageText> list = movieService.getTitle();
		for (ImageText imageText : list) {
			request.setAttribute(imageText.getId(), imageText.getTitle() + imageText.getBrief());
		}
		if (ConfigService.recommendMovieIdList == null) {
			recommendArithmetic(null);
		}
		return "index";
	}

	/**
	 * 分类
	 */
	@RequestMapping("/index_highscore")
	public String indexHighScore(Model model) {
		model.addAttribute("list", movieService.getHighScoreMovieInfo());
		return "index";
	}

	@RequestMapping("/index_plot")
	public String indexPlot(Model model, HttpSession session) {
		model.addAttribute("list", movieService.getTypeMovieInfo("剧情"));
		String phone = (String) session.getAttribute("phone");
		if (phone != null) {
			UserRecord userRecord = userService.getUserRecord(phone);
			if (userRecord != null) {
				int type = userRecord.getPlot() + 1;
				// 更新用户点击数据
				userService.updateUserRecord(phone, "plot", type);
			} else {
				// 初始化UserRecord
				userService.initUserRecord(phone);
			}
		}
		return "index";
	}

	@RequestMapping("/index_action")
	public String indexAction(Model model, HttpSession session) {
		model.addAttribute("list", movieService.getTypeMovieInfo("动作"));
		String phone = (String) session.getAttribute("phone");
		if (phone != null) {
			UserRecord userRecord = userService.getUserRecord(phone);
			if (userRecord != null) {
				int type = userRecord.getAction() + 1;
				// 更新用户点击数据
				userService.updateUserRecord(phone, "action", type);
			} else {
				// 初始化UserRecord
				userService.initUserRecord(phone);
			}
		}
		return "index";
	}

	@RequestMapping("/index_comedy")
	public String indexComedy(Model model, HttpSession session) {
		model.addAttribute("list", movieService.getTypeMovieInfo("喜剧"));
		String phone = (String) session.getAttribute("phone");
		if (phone != null) {
			UserRecord userRecord = userService.getUserRecord(phone);
			if (userRecord != null) {
				int type = userRecord.getComdy() + 1;
				// 更新用户点击数据
				userService.updateUserRecord(phone, "comdy", type);
			} else {
				// 初始化UserRecord
				userService.initUserRecord(phone);
			}
		}
		return "index";
	}

	@RequestMapping("/index_love")
	public String indexLove(Model model, HttpSession session) {
		model.addAttribute("list", movieService.getTypeMovieInfo("爱情"));
		String phone = (String) session.getAttribute("phone");
		if (phone != null) {
			UserRecord userRecord = userService.getUserRecord(phone);
			if (userRecord != null) {
				int type = userRecord.getLove() + 1;
				// 更新用户点击数据
				userService.updateUserRecord(phone, "love", type);
			} else {
				// 初始化UserRecord
				userService.initUserRecord(phone);
			}
		}
		return "index";
	}

	@RequestMapping("/index_science")
	public String indexScience(Model model, HttpSession session) {
		model.addAttribute("list", movieService.getTypeMovieInfo("科幻"));
		String phone = (String) session.getAttribute("phone");
		if (phone != null) {
			UserRecord userRecord = userService.getUserRecord(phone);
			if (userRecord != null) {
				int type = userRecord.getScience() + 1;
				// 更新用户点击数据
				userService.updateUserRecord(phone, "science", type);
			} else {
				// 初始化UserRecord
				userService.initUserRecord(phone);
			}
		}
		return "index";
	}

	@RequestMapping("/index_suspense")
	public String indexSuspense(Model model, HttpSession session) {
		model.addAttribute("list", movieService.getTypeMovieInfo("悬疑"));
		String phone = (String) session.getAttribute("phone");
		if (phone != null) {
			UserRecord userRecord = userService.getUserRecord(phone);
			if (userRecord != null) {
				int type = userRecord.getSuspense() + 1;
				// 更新用户点击数据
				userService.updateUserRecord(phone, "suspense", type);
			} else {
				// 初始化UserRecord
				userService.initUserRecord(phone);
			}
		}
		return "index";
	}

	/**
	 * 电影信息录入
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "input")
	public Movie inputMovie() {
		return movieService.inputMovieInfo();
	}

	/**
	 * 电影详情页展示
	 * 
	 * @param moviename
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "movie")
	public String showMovie(@RequestParam("moviename") String moviename, HttpServletRequest request,
			HttpSession session) {
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

		session.setAttribute("movieName", moviename);

		/**
		 * 设置管理员权限
		 */
		int isAdmin = userService.getAdmin((String)session.getAttribute("phone"));
		request.setAttribute("admin", String.valueOf(isAdmin));
		
		return "movieInfo";
	}

	/**
	 * 展示评论
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "comment")
	public List<Comment> comment(@RequestParam("page") int page, HttpSession session) {
		// movieId
		String movieName = (String) session.getAttribute("movieName");
		List<Comment> list = new ArrayList<>();
		if (list.isEmpty()) {
			return movieService.getComment(movieName, (page - 1) * 5);
		}
		if (session.getAttribute("updateComment") != null) {
			return movieService.getComment(movieName, (page - 1) * 5);
		}
		return list;
	}

	/**
	 * 添加评论
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addComment")
	public List<Comment> addComment(HttpServletRequest request, HttpSession session) {
		// username
		String username = (String) session.getAttribute("username");
		String movieName = (String) session.getAttribute("movieName");
		Comment comment = new Comment();
		// 未登录情况下不能评论
		if (username != null) {
			String score = request.getParameter("score");
			String userComment = request.getParameter("userComment");
			// movieId
			String movieId = movieService.getMovieIdByMovieName(movieName);
			// evaluationDate
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String evaluationDate = sdf.format(date);
			// comment

			comment.setMovieId(movieId);
			comment.setUsername(username);
			comment.setScore(score);
			comment.setEvaluationDate(evaluationDate);
			comment.setUserComment(userComment);
			movieService.addComment(comment);

			session.setAttribute("updateComment", 0);
			
			/**
			 * 评论人数+1
			 */
			movieService.plusAssessNum(movieName);
		}
		return movieService.getComment(movieName, 0);
	}

	/**
	 * 删除评论
	 * 
	 * @return
	 */
	@RequestMapping(value = "deleteComment")
	public String deleteComment(@RequestParam("movieId") String movieId, @RequestParam("username") String username,
			HttpSession session) {
		movieService.deleteComment(movieId, username);
		String movieName = (String) session.getAttribute("movieName");
		try {
			movieName = URLEncoder.encode(movieName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:movie?moviename=" + movieName;
	}

	/**
	 * 搜索框模糊查询
	 * 
	 * @param moviename
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/search")
	public String searchMovie(@RequestParam("moviename") String moviename, HttpServletRequest request,
			HttpSession session) {
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
			movie = movieService.searchMovieInfo(moviename);
		}
		if (movie == null) {
			return "redirect:index";
		}
		/**
		 * 收集用户访问数据
		 */
		String movieType = "";
		boolean Flag = true;
		if (movie.getType().indexOf("剧情") >= 0) {
			movieType = "plot";
			Flag = false;
		} else if (Flag && movie.getType().indexOf("动作") >= 0) {
			movieType = "action";
			Flag = false;
		} else if (Flag && movie.getType().indexOf("喜剧") >= 0) {
			movieType = "comdy";
			Flag = false;
		} else if (Flag && movie.getType().indexOf("爱情") >= 0) {
			movieType = "love";
			Flag = false;
		} else if (Flag && movie.getType().indexOf("科幻") >= 0) {
			movieType = "science";
			Flag = false;
		} else if (Flag && movie.getType().indexOf("悬疑") >= 0) {
			movieType = "suspense";
			Flag = false;
		}
		// 如果有匹配结果
		if (!Flag) {
			String phone = (String) session.getAttribute("phone");
			if (phone != null) {
				UserRecord userRecord = userService.getUserRecord(phone);
				if (userRecord != null) {
					int type = 0;
					switch (movieType) {
					case "plot":
						type = userRecord.getPlot() + 1;
						break;
					case "action":
						type = userRecord.getAction() + 1;
						break;
					case "comdy":
						type = userRecord.getComdy() + 1;
						break;
					case "love":
						type = userRecord.getLove() + 1;
						break;
					case "science":
						type = userRecord.getScience() + 1;
						break;
					case "suspense":
						type = userRecord.getSuspense() + 1;
						break;
					default:
						break;
					}
					// 更新用户点击数据
					userService.updateUserRecord(phone, movieType, type);
				} else {
					// 初始化UserRecord
					userService.initUserRecord(phone);
				}
			}
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

		session.setAttribute("movieName", moviename);

//		return "redirect:movieInfo";
		return "movieInfo";
	}

	/**
	 * 热映榜单
	 */
	@RequestMapping("/screen")
	public String screen(@RequestParam("type") String type, @RequestParam("page") String page, Model model,
			HttpServletRequest request) {
		request.setAttribute("type", type);
		if ("score".equals(type)) {
			model.addAttribute("list", movieService.getHighScoreScreenMovieInfo((Integer.parseInt(page) - 1) * 10));
		} else if ("assessNum".equals(type)) {
			model.addAttribute("list", movieService.getPopularScreenMovieInfo((Integer.parseInt(page) - 1) * 10));
		} else {
			model.addAttribute("list", movieService.getBoxOfficeScreenMovieInfo((Integer.parseInt(page) - 1) * 10));
		}
		return "screen";
	}

	/**
	 * 分类排行榜
	 * 
	 * @param moviename
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "rank")
	public String rankList(@RequestParam("type") String type, @RequestParam("page") String page, Model model,
			HttpServletRequest request, HttpSession session) {
		request.setAttribute("type", type);
		/**
		 * 收集用户访问数据
		 */
		String phone = (String) session.getAttribute("phone");
		if (phone != null) {
			UserRecord userRecord = userService.getUserRecord(phone);
			if (userRecord != null) {
				int movietype = 0;
				String recordType = "";
				switch (type) {
//					case "剧情":movietype = userRecord.getPlot()+1;recordType="plot";break;
				case "动作":
					movietype = userRecord.getAction() + 1;
					recordType = "action";
					break;
				case "喜剧":
					movietype = userRecord.getComdy() + 1;
					recordType = "comdy";
					break;
				case "爱情":
					movietype = userRecord.getLove() + 1;
					recordType = "love";
					break;
				case "科幻":
					movietype = userRecord.getScience() + 1;
					recordType = "science";
					break;
				case "悬疑":
					movietype = userRecord.getSuspense() + 1;
					recordType = "suspense";
					break;
				default:
					break;
				}
				// 更新用户点击数据
				if (!type.equals("剧情")) {
					userService.updateUserRecord(phone, recordType, movietype);
				}
			} else {
				// 初始化UserRecord
				userService.initUserRecord(phone);
			}
		}
		if (!"".equals(type)) {
			model.addAttribute("list", movieService.getRankList(type, (Integer.parseInt(page) - 1) * 10));
		}
		return "rank";
	}

	/**
	 * 播放电影
	 */
	@RequestMapping("/video")
	public String videoPlay(@RequestParam("moviename") String movieName, HttpServletRequest request) {
		request.setAttribute("movieName", "video/" + movieName + ".mp4");
		return "video";
	}

	/**
	 * 推荐算法
	 */
	public void recommendArithmetic(List<MovieVo> movieVo) {
		/**
		 * 如果是个人推荐算法产生的数据
		 */
		boolean isPersonal = false;
		if (movieVo == null) {
			// 获得评分排名前20
			movieVo = movieService.getTwenty();
		} else {
			isPersonal = true;
		}

		List<Map<String, String>> movieList = new ArrayList<>();
		for (MovieVo vo : movieVo) {
			Map<String, String> map = new HashMap<>();
			map.put("id", vo.getId());
			map.put("score", String.valueOf(vo.getScore()));
			map.put("record", vo.getRecord());
			map.put("assessNum", String.valueOf(vo.getAssessNum()));
			movieList.add(map);
		}
		double record;
		int assessNum;

		Map<String, Double> recordMap = new HashMap<>();
		Map<String, Integer> assessMap = new HashMap<>();
		Map<String, Integer> recordResult = new HashMap<>();
		Map<String, Integer> assessResult = new HashMap<>();
		// 添加recordRank assessRank
		for (Map<String, String> map : movieList) {
			String id = map.get("id");
			record = Double.parseDouble(map.get("record"));
			assessNum = Integer.parseInt(map.get("assessNum"));
			recordMap.put(id, record);
			assessMap.put(id, assessNum);
		}
		/**
		 * 票房排序
		 */
		List<Map.Entry<String, Double>> comparaMap = new ArrayList<>(recordMap.entrySet());
		Collections.sort(comparaMap, new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Map.Entry<String, Double> m1, Map.Entry<String, Double> m2) {
				double result = m2.getValue() - m1.getValue();
				if (result > 0) {
					return 1;
				} else if (result == 0) {
					return 0;
				} else {
					return -1;
				}
			}
		});
		int i = 1;
		for (Map.Entry<String, Double> map : comparaMap) {
			recordResult.put(map.getKey(), i);
			i++;
		}
		/**
		 * 评价人数排序
		 */
		List<Map.Entry<String, Integer>> comparaMap2 = new ArrayList<>(assessMap.entrySet());
		Collections.sort(comparaMap2, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> m1, Map.Entry<String, Integer> m2) {
				int result = m2.getValue() - m1.getValue();
				if (result > 0) {
					return 1;
				} else if (result == 0) {
					return 0;
				} else {
					return -1;
				}
			}
		});
		i = 1;
		for (Map.Entry<String, Integer> map : comparaMap2) {
			assessResult.put(map.getKey(), i);
			i++;
		}
		/**
		 * 权重
		 */
		String id;
		double score;
		double ultimate;
		Map<Double, String> rank = new HashMap<>();
		for (Map<String, String> map : movieList) {
			id = map.get("id");
			score = Double.parseDouble(map.get("score"));
			int recordRank = recordResult.get(id);
			int assessRank = assessResult.get(id);
			// 最终结果
			ultimate = (score * 10 + (100 - recordRank * 2) + (100 - assessRank * 3)) / 3.0;
			rank.put(ultimate, id);
		}

		List<Double> resultKeys = new ArrayList<>();
		for (Double key : rank.keySet()) {
			resultKeys.add(key);
		}
		Collections.sort(resultKeys, Collections.reverseOrder());
		List<String> resultIds = new ArrayList<>();
		int movieNum = 0;
		for (Double key : resultKeys) {
			resultIds.add(rank.get(key));
			movieNum++;
			if (movieNum == 10) {
				break;
			}
		}
		if (!isPersonal) {
			ConfigService.recommendMovieIdList = resultIds;
		} else {
			ConfigService.personalRecommendMovieIdList = resultIds;
		}
		System.out.println(ConfigService.recommendMovieIdList);
	}

	/**
	 * 推荐电影展示页面
	 */
	@RequestMapping("/recommend")
	public String recommend(Model model, HttpSession session) {
		/**
		 * 判断用户是否登录
		 */
		String phone = (String) session.getAttribute("phone");
		if (phone == null) {
			/**
			 * 未登录
			 */
			List<Map<String, String>> list = new ArrayList<>();
			for (String id : ConfigService.recommendMovieIdList) {
				MovieRecommendVo movie = movieService.getSimpleMovieInfo(id);
				Map<String, String> map = new HashMap<>();
				map.put("id", movie.getId());
				map.put("name", movie.getName());
				map.put("score", movie.getScore());
				list.add(map);
			}
			model.addAttribute("list", list);
		} else {
			/**
			 * 用户已登录
			 */
			UserRecord userRecord = userService.getUserRecord(phone);
			if(userRecord == null) {
				userService.initUserRecord(phone);
				userRecord = userService.getUserRecord(phone);
			}
			Map<String, Integer> map = new TreeMap<>();
			map.put("剧情", userRecord.getPlot());
			map.put("动作", userRecord.getAction());
			map.put("喜剧", userRecord.getComdy());
			map.put("爱情", userRecord.getLove());
			map.put("科幻", userRecord.getScience());
			map.put("悬疑", userRecord.getSuspense());
			/**
			 * 判断用户点击信息是否全为0
			 */
			boolean allZero = false;
			int zeroTimes = 0;
			for (String key : map.keySet()) {
				if (map.get(key) == 0) {
					zeroTimes++;
				}
			}
			if (zeroTimes == 6) {
				allZero = true;
			}
			/**
			 * 用户已登录无数据
			 */
			if (allZero) {
				List<Map<String, String>> list = new ArrayList<>();
				for (String id : ConfigService.recommendMovieIdList) {
					MovieRecommendVo movie = movieService.getSimpleMovieInfo(id);
					Map<String, String> movieList = new HashMap<>();
					movieList.put("id", movie.getId());
					movieList.put("name", movie.getName());
					movieList.put("score", movie.getScore());
					list.add(movieList);
				}
				model.addAttribute("list", list);
			} else {
				/**
				 * 用户已登录有数据
				 */
				List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
				Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
					@Override
					public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
						int result = o2.getValue() - o1.getValue();
						if (result > 0) {
							return 1;
						} else if (result == 0) {
							return 0;
						} else {
							return -1;
						}
					}
				});
				String interest1 = "";
				String interest2 = "";
				int count = 0;
				for (Map.Entry<String, Integer> result : list) {
					if (count == 0) {
						interest1 = result.getKey();
					} else if (count == 1) {
						interest2 = result.getKey();
					} else {
						break;
					}
					count++;
					// System.out.println(result.getKey()+":"+result.getValue());
				}

				/**
				 * 输入两个类型参数 分别获取两个类型电影前10名数据
				 */
				// System.out.println(interest1+":"+interest2);
				List<MovieVo> movieList = movieService.getTwoTypes(interest1, interest2);
				recommendArithmetic(movieList);
				/**
				 * 展示个人推荐
				 */
				// System.out.println(ConfigService.personalRecommendMovieIdList);
				int count1 = 0;
				List<Map<String, String>> showList = new ArrayList<>();
				for (String id : ConfigService.personalRecommendMovieIdList) {
					MovieRecommendVo movie = movieService.getSimpleMovieInfo(id);
					Map<String, String> movieInfo = new HashMap<>();
					movieInfo.put("id", movie.getId());
					movieInfo.put("name", movie.getName());
					movieInfo.put("score", movie.getScore());
					showList.add(movieInfo);
					count1++;
					if (count1 == 3) {
						break;
					}
				}
				for (String id : ConfigService.recommendMovieIdList) {
					// 去重
					boolean isRepeat = false;
					for (String generalId : ConfigService.personalRecommendMovieIdList) {
						if (generalId.equals(id)) {
							isRepeat = true;
						}
					}
					if (isRepeat) {
						continue;
					}
					MovieRecommendVo movie = movieService.getSimpleMovieInfo(id);
					Map<String, String> movieInfo = new HashMap<>();
					movieInfo.put("id", movie.getId());
					movieInfo.put("name", movie.getName());
					movieInfo.put("score", movie.getScore());
					showList.add(movieInfo);
					count++;
					if (count == 4) {
						break;
					}
				}
				model.addAttribute("list", showList);
			}

		}

		return "recommend";
	}
}
