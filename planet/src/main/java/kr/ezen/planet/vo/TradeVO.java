package kr.ezen.planet.vo;

import lombok.Data;

@Data
public class TradeVO {
	private int id; // NOT NULL, PRIMARY KEY
	private int product_id; // NOT NULL
	private int member_id;// NOT NULL
	
}
