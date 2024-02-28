package kr.ezen.planet.vo;

import java.util.Date;

import lombok.Data;

@Data
public class PostVO {
	private int id; // NOT NULL, PRIMARY KEY
	private int member_id; // NOT NULL
	private int product_id;
	private String text; // NOT NULL
	private String image;
	private Date postdate; // NOT NULL
	private String title; // NOT NULL
	private int viewcount; // NOT NULL

}
