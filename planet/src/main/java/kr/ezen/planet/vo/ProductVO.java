package kr.ezen.planet.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ProductVO {
	private int id; // NOT NULL, PRIMARY KEY
	private int member_id; // NOT NULL
	private String productName;
	private int cost; // NOT NULL
	private String title; // NOT NULL
	private String text; // NOT NULL
	private String img;
	private Date productdate; // NOT NULL	
	private boolean complete; // NOT NULL
	private Date completedate; 
	private int category_id; // NOT NULL

}
