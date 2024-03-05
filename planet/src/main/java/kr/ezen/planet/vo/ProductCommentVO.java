package kr.ezen.planet.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ProductCommentVO {
	private int id; // NOT NULL, PRIMARY KEY
	private int product_id; // NOT NULL
	private int member_id; // NOT NULL
	private String content; // NOT NULL
	private Date commentdate; // NOT NULL
	
}
