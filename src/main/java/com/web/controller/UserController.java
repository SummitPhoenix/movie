package com.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.web.entity.ImageText;
import com.web.entity.User;
import com.web.service.IUserService;
import com.web.util.ImageTextInput;
import com.web.util.SysFilesUploadServiceImpl;

@Controller
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private ImageTextInput imageTextInput;
	
	/**
	 * 注册页面
	 */
	@RequestMapping(value = "reg")
	public String reg() {
		return "reg";
	}

	/**
	 * 登录页面
	 */
	@RequestMapping(value = "login")
	public String login() {
		return "login";
	}

	/**
	 * 用户登陆后查看登录信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "userInfo")
	public String userInfo(HttpSession session, HttpServletRequest request, Model model) {
		String phone = (String) session.getAttribute("phone");
		if(phone == null) {
			return "redirect:login";
		}
		/**
		 * 设置管理员权限
		 */
		int isAdmin = userService.getAdmin(phone);
		request.setAttribute("admin", String.valueOf(isAdmin));
		
		request.setAttribute("phone", session.getAttribute("phone"));
		request.setAttribute("username", session.getAttribute("username"));
		request.setAttribute("email", session.getAttribute("email"));
		request.setAttribute("location", session.getAttribute("location"));
		model.addAttribute("list", new Gson().toJson(userService.getTicketList(phone)));
		model.addAttribute("ticketIdList", userService.getTicketList(phone));
		return "userInfo";
	}

	/**
	 * 处理注册
	 * 
	 * @param phone
	 * @param username
	 * @param password
	 * @param email
	 * @param location
	 * @return
	 */
	@RequestMapping(value = "/handleReg", method = RequestMethod.POST)
	public String handleReg(@RequestParam("phone") String phone, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("email") String email,
			@RequestParam("location") String location) {

		User user = new User();
		user.setPhone(phone);
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setLocation(location);
		// 调用业务层对象实现注册
		String error = userService.reg(user);
		if (error == null) {
			return "redirect:index";
		} else if (error.equals("PhoneFail")) {
			return "PhoneFail";
		} else {
			return "RegFail";
		}

	}

	/**
	 * 处理登录
	 * 
	 * @param phone
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/handleLogin", method = RequestMethod.POST)
	public String handleLogin(@RequestParam("phone") String phone, @RequestParam("password") String password,
			HttpSession session) {

		if (phone == null || password==null || phone.length()!=11 || password.length()<6 || password.length()>16) {
			return "LoginFail";
		}
		/**
		 * 如果之前有用户登录，销毁session
		 */
		if (session.getAttribute("phone") != null) {
			session.invalidate();
		}
		// 调用业务层对象的login()方法，并获取返回值
		User user = userService.login(phone, password);
		if (user == null) {
			return "LoginFail";
		}
		if (user.getPassword() != null) {
			return "LoginFail";
		}
		session.setMaxInactiveInterval(30 * 60);

		// 将用户id和用户名封装到session中
		session.setAttribute("phone", user.getPhone());
		session.setAttribute("username", user.getUsername());
		session.setAttribute("email", user.getEmail());
		session.setAttribute("location", user.getLocation());
		session.setAttribute("ticketId", user.getTicketId());
		// 返回
		return "redirect:index";
	}

	/**
	 * 退出登录
	 */
	@RequestMapping(value = "/signOut")
	public String signOut(HttpSession session) {
		// 销毁session
		session.invalidate();
		return "redirect:login";
	}

	/**
	 * 上传图片文件夹
	 */
	@RequestMapping(value = "/uploadImgFolder")
	public String uploadImgFolder(HttpServletRequest request) {
		return "uploadImgFolder";
	}
	
	/**
	 * 上传图片文件夹
	 */
	@RequestMapping(value = "/uploadFolder", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFileFolder(HttpServletRequest request) {
		System.out.println("uploadFile");
		MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
		List<MultipartFile> files = params.getFiles("fileFolder");// fileFolder为文件项的name值
		String result = SysFilesUploadServiceImpl.upload(files);
		return "<h1>" + result + "</h1>";
	}
	
	/**
	 * 上传图文
	 */
	@RequestMapping(value = "/upload")
	public String upload() {
		userService.insertImageText(imageTextInput.inputImageText());
		return "upload";
	}
	
	/**
	 * 展示图文
	 */
	@RequestMapping(value = "/imageText")
	public String imageText(@RequestParam("id") String id, HttpServletRequest request) {
		ImageText imageText = userService.getImageText(id);
		request.setAttribute("content", imageText.getContent());
		return "imageText";
	}
}
