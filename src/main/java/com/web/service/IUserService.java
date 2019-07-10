package com.web.service;

import java.util.List;

import com.web.entity.ImageText;
import com.web.entity.Ticket;
import com.web.entity.User;
import com.web.entity.UserRecord;

public interface IUserService {

	/**
	 * 用户注册
	 * 
	 * @param user 用户数据
	 * @return 
	 */
	String reg(User user);

	/**
	 * 用户登录
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @return 成功登录的用户信息
	 */
	User login(String username, String password);
	
	
	/**
	 * 检查手机号是否已经被注册
	 * @param username
	 * @return
	 */
	User findUserByPhone(String phone);

	/**
	 * 插入购票信息
	 * @param phone
	 * @param ticketId
	 */
	void addTicket(Ticket ticket);

	/**
	 * 通过手机号获取邮箱
	 * @param phone
	 */
	String getEmail(String phone);

	/**
	 * 通过手机号获得用户记录 
	 * @param phone
	 * @return
	 */
	UserRecord getUserRecord(String phone);
	
	/**
	 * 初始化Userecord
	 * @param phone
	 */
	void initUserRecord(String phone);

	/**
	 * 更新数据到UserRecord(通用)
	 */
	void updateUserRecord(String phone, String type, int count);

	/**
	 * 获得用户所有Ticket
	 */
	List<Ticket> getTicketList(String phone);
	
	/**
	 * 图文录入
	 */
	void insertImageText(ImageText inputImageText);

	/**
	 * 获取图文
	 */
	ImageText getImageText(String id);

	/**
	 * 判断此用户是否是管理员
	 */
	int getAdmin(String phone);
}
