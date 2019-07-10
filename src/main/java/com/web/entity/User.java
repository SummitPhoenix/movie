package com.web.entity;

import lombok.Data;

@Data
public class User {
	private String phone;
    private String username;
    private String password;
    private String email;
    private String location;
    /**
     * 盐，用于密码加密
     */
    private String salt;
    private String ticketId;
    
}
