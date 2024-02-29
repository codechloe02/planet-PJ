package kr.ezen.planet.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ReputationVO {
	private int id; // NOT NULL, PRIMARY KEY
	private int trade_id; // NOT NULL
	private int score;
	private int member_id; // NOT NULL
	private String content; // NOT NULL
	private Date reputationdate; // NOT NULL

}
