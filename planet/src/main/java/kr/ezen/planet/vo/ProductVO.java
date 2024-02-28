package kr.ezen.planet.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ProductVO {
	private int id; // NOT NULL, PRIMARY KEY
	private String productName;
	private int cost; // NOT NULL
	private boolean complete; // NOT NULL
	private Date completedate; 
	private int category_id; // NOT NULL
}
