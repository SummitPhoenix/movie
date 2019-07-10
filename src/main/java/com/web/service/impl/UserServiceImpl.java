package com.web.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.entity.ImageText;
import com.web.entity.Ticket;
import com.web.entity.User;
import com.web.entity.UserRecord;
import com.web.mapper.UserMapper;
import com.web.service.IUserService;

/**
 * 用户数据的业务层
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	public String reg(User user) {
		// * 定位：组织业务，保证数据的安全性
		// 根据尝试注册的用户名查询用户数据
		User result = findUserByPhone(user.getPhone());
		int isNameExist = findUserByName(user.getUsername());
		// 判断是否查询到数据
		if (result != null || isNameExist==1) {
			// 执行返回
			// 是：查询到数据，用户名被占用
			return "PhoneFail";
		} else {
			// 否：没有查询到数据，即用户名没有被占用，则执行插入用户数据，获取返回值
			Integer rows = insertUserInfo(user);
			if(rows==1) {
				return null;
			}else {
				return "RegFail";
			}
			
		}
	}

	private int findUserByName(String username) {
		return userMapper.findUserByName(username);
	}

	public User login(String phone, String password) {
		// 根据用户名查询用户数据
		User user = findUserByPhone(phone);
		// 判断是否查询到数据
		if (user != null) {
			// 是：查询到与用户名匹配的数据，获取盐值
			String salt = user.getSalt();
			// 基于参数密码与盐值进行加密
			String md5Password = getEncrpytedPassword(password, salt);
			// 判断加密结果与用户数据中的密码是否匹配
			if (user.getPassword().equals(md5Password)) {
				// 是：返回用户数据
				user.setPassword(null);
				user.setSalt(null);
				return user;
			} else {
				user.setPassword("密码错误");
				return user;
			}
		} else {
			// 否：没有与手机号匹配的数据，则抛出UserNotFoundException异常
			return null;
		}
	}

	
	
	
	/**
	 * 插入用户数据
	 * 
	 * @param user 用户数据
	 * @return 插入用户数据的条数
	 */
	private Integer insertUserInfo(User user) {
		// 在参数user中封装那些不由外部提供的数据：
		// 1. 生成随机盐，并封装到user中
		String salt = UUID.randomUUID().toString().toUpperCase();
		user.setSalt(salt);
		// 2. 取出user中原密码执行加密，并封装回user中
		String oldPassword = user.getPassword();
		String md5Password = getEncrpytedPassword(oldPassword, salt);
		user.setPassword(md5Password);
		
		// 插入用户数据
		return userMapper.insertUserInfo(user);
	}

	/**
	 * 获取加密后的密码
	 * 
	 * @param password 密码原文
	 * @param salt     盐值
	 * @return 加密后的密码
	 */
	private String getEncrpytedPassword(String password, String salt) {
		// 将原密码加密
		String str1 = DigestUtils.md5Hex(password).toUpperCase();
		// 将盐加密
		String str2 = DigestUtils.md5Hex(salt).toUpperCase();
		// 将以上2个加密结果拼接
		String result = str1 + str2;
		result = DigestUtils.md5Hex(result).toUpperCase();
		// 返回
		return result;
	}

	/**
	 * 检查手机号是否已被注册
	 */
	@Override
	public User findUserByPhone(String phone) {
		return userMapper.findUserByPhone(phone);
	}

	@Override
	public void addTicket(Ticket ticket) {
		userMapper.addTicket(ticket);
	}

	@Override
	public String getEmail(String phone) {
		return userMapper.getEmail(phone);
	}

	@Override
	public UserRecord getUserRecord(String phone) {
		return userMapper.getUserRecord(phone);
	}

	@Override
	public void initUserRecord(String phone) {
		userMapper.initUserRecord(phone);
	}

	@Override
	public void updateUserRecord(String phone, String type, int count) {
		userMapper.updateUserRecord(phone,type,count);
	}

	@Override
	public List<Ticket> getTicketList(String phone) {
		return userMapper.getTicketList(phone);
	}

	@Override
	public void insertImageText(ImageText imageText) {
		userMapper.insertImageText(imageText);
	}

	@Override
	public ImageText getImageText(String id) {
		return userMapper.getImageText(id);
	}

	@Override
	public int getAdmin(String phone) {
		return userMapper.getAdmin(phone);
	}

}
