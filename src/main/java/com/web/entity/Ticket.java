package com.web.entity;

import lombok.Data;

@Data
public class Ticket {
	private String email;
	private String ticketId;
	private String phone;
	private String movieName;
	private String cinemaName;
	private String videoHall;
	private String row;
	private String seat;
	private String screenDate;
	private String screenTime;
	private String price;
	private String qrCodeFileUrl;
}
