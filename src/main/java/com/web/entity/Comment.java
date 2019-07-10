package com.web.entity;

import lombok.Data;

/**
 * 	评论
 */
@Data
public class Comment {
	private String movieId;
	private String username;
	private String score;
	private String evaluationDate;
	private String userComment;
}
