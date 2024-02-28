package kr.ezen.planet.vo;

import lombok.Getter;
import lombok.ToString;

// 글번호, 현재페이지 번호, 페이지당 글 수 , 페이지 목록 수를 받기 위한 공통 변수를 가지는 클래스
@Getter @ToString
public class Common {
	private int p = 1;
	private int s = 10;
	private int b = 10;
	private int idx = 0;
	private int currentPage = 1;
	private int sizeOfPage = 10;
	private int sizeOfBlock = 10;
	private String mode = "insert";
	
	public void setP(int p) {
		this.p = p;
		setCurrentPage(p); //p값이 바뀔 때 currentPage도 같이 바뀌어야 한다.
	}
	public void setS(int s) {
		this.s = s;
		setSizeOfPage(s); //s값이 바뀔대 sizeOfPage도 같이 바뀌어야 한다.
	}
	public void setB(int b) {
		this.b = b;
		setSizeOfBlock(b); //b값이 바뀔때 sizeOfBlock도 같이 바뀌어야 한다.
	}
	public void setIdx(int idx) {
		this.idx = idx;
		
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public void setSizeOfPage(int sizeOfPage) {
		this.sizeOfPage = sizeOfPage;
	}
	public void setSizeOfBlock(int sizeOfBlock) {
		this.sizeOfBlock = sizeOfBlock;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
}
