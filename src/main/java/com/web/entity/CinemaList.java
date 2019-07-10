package com.web.entity;

import java.util.List;

import lombok.Data;

/**
 * 	影院列表
 */
@Data
public class CinemaList {
	private String movieId;
	private List<String> cinemaId;
}
