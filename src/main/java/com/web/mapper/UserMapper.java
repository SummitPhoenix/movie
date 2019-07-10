package com.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.entity.ImageText;
import com.web.entity.Ticket;
import com.web.entity.User;
import com.web.entity.UserRecord;

@Mapper
public interface UserMapper {

	/**
	 * 插入用户数据
	 * 
	 * @param user 用户数据
	 * @return 受影响的行数
	 */
	Integer insertUserInfo(User user);

	/**
	 * 根据用户名查询用户数据
	 * 
	 * @param username 用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findUserByPhone(String phone);


	/**
	 * 插入购票信息
	 * @param phone
	 * @param ticketId
	 */
	void addTicket(Ticket ticket);

	/**
	 * 根据手机号获取邮箱
	 * @param phone 
	 * @return
	 */
	String getEmail(String phone);

	/**
	 * 获得UserRecord
	 * @param phone
	 * @return
	 */
	UserRecord getUserRecord(String phone);

	/**
	 * 初始化UserRecord
	 * @param phone
	 */
	void initUserRecord(String phone);

	/**
	 * 更新数据到UserRecord
	 * @param type
	 * @param count
	 */
	void updateUserRecord(String phone, String type, int count);

	/**
	 * 获得用户订票信息
	 */
	List<Ticket> getTicketList(String phone);
	
	/**
	 * 插入图文
	 */
	void insertImageText(ImageText imageText);

	/**
	 * 获得图文
	 */
	ImageText getImageText(String id);

	/**
	 * 判断此用户是否是管理员
	 */
	int getAdmin(String phone);

	/**
	 * 查询用户名是否存在
	 */
	int findUserByName(String username);
}