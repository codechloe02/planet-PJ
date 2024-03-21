package kr.ezen.planet.vo;

import java.util.List;

public class PagingVO<T> {
	// 1페이지 분량의 데이터를 저장할 리스트
	List<T> list;
	// 페이지 계산을 위한 변수
	private int totalCount; // 전채 개수
	private int currentPage; // 현재 페이지 수
	private int sizeOfPage; // 1페이지당 개수
	private int sizeOfBlock; // 하단에 표시할 페이지 개수

	private int startNo; // 시작 글번호
	private int endNo; // 끝 글번호
	private int startPage; // 시작페이지 번호
	private int endPage; // 끝페이지 번호
	private int totalPage; // 전체 페이지 수

	// 상단의 4개의 변수는 넘겨주고 아래의 5개는 계산을 한다.
	// 4개는 생성자로 받아 처리
	public PagingVO(int totalCount, int currentPage, int sizeOfPage, int sizeOfBlock) {
		super();
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.sizeOfPage = sizeOfPage;
		this.sizeOfBlock = sizeOfBlock;
		calc();
	}

	private void calc() {
		// 유효성 검사
		// 현재 페이지 번호가 1보다는 적을 수 없다.
		if (currentPage <= 0)
			currentPage = 1;
		// 페이지당 글수는 최소 2보다 적을 수 없다.
		if (sizeOfPage < 2)
			sizeOfPage = 12;
		// 하단 페이지수는 최소 2개 이상이어야 한다.
		if (sizeOfBlock < 2)
			sizeOfBlock = 10;

		// 글이 존재할때만 나머지를 계산
		if (totalCount > 0) {
			// 나머지 변수들 계산
			// 전체 페이지 수 계산 = (전체 개수 -1)/ 페이지당 글수 +1
			totalPage = (totalCount - 1) / sizeOfPage + 1;
			// 현재 페이지수는 전체 페이지수를 넘을 수 없다.
			if (currentPage > totalPage)
				currentPage = 1;
			// 시작 글 번호 = (현재 페이지 -1) * 페이지당 글 수 +1
			startNo = (currentPage - 1) * sizeOfPage + 1;
			// 끝 글 번호 = 시작글번호 + 페이지당 글수 -1
			endNo = startNo + sizeOfPage - 1;
			// 마지막번호는 전채 개수를 넘을 수 없다.
			if (endNo > totalCount)
				endNo = totalCount;
			// 시작페이지번호 = (현재 페이지 -1)/ 표시 할 페이지 개수 * 표시 할 페이지개수+1
			startPage = (currentPage - 1) / sizeOfBlock * sizeOfBlock + 1;
			// 끝페이지 번호 = 시작페이지 번호 + 표시할 페이지 개수 -1
			endPage = startPage + sizeOfBlock - 1;
			// 끝 페이지 번호는 전체 페이지 수를 넘을 수 없다.
			if (endPage > totalPage)
				endPage = totalPage;
		}
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getSizeOfPage() {
		return sizeOfPage;
	}

	public int getSizeOfBlock() {
		return sizeOfBlock;
	}

	public int getStartNo() {
		return startNo;
	}

	public int getEndNo() {
		return endNo;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	@Override
	public String toString() {
		return "PagingVO [list=" + list + ", totalCount=" + totalCount + ", currentPage=" + currentPage
				+ ", sizeOfPage=" + sizeOfPage + ", sizeOfBlock=" + sizeOfBlock + ", startNo=" + startNo + ", endNo="
				+ endNo + ", startPage=" + startPage + ", endPage=" + endPage + ", totalPage=" + totalPage + "]";
	}

	public static void main(String[] args) {
		PagingVO<String> pv = new PagingVO<String>(100, 10, 10, 10);
		System.out.println(pv);
	}

	// 메서드 2개 추가
	// 1. 하단에 이동하는 메서드
	public String getPageList() {
		StringBuffer sb = new StringBuffer();
		if (totalCount > 0) {
			sb.append("<nav aria-label='Page navigation example'>");
			sb.append("<ul class='pagination pagination-sm justify content-center'>");
			// 시작페이지가 1보다 클때만 이전이 있다.
			if (startPage > 1) {
				sb.append("<li class='page-item'>");
				sb.append("<a class='page-link' href='?p=" + (startPage - 1) + "&s=" + sizeOfPage + "&b=" + sizeOfBlock
						+ "'aria-label='Previous'>");
				sb.append("<span aria-hidden='true'>&laquo;</span>");
				sb.append("</a>");
			}
			// 페이지 이동
			for (int i = startPage; i <= endPage; i++) {
				if (i == currentPage) {
					// 현재 페이지는 링크를 걸지 않는다.
					sb.append(
							"<li class='page-item active' aria-current='page'><a class='page-link'>" + i + "</a></li>");
				} else {
					sb.append("<li class='page-item'><a class='page-link' href='?p=" + i + "&s=" + sizeOfPage + "&b="
							+ sizeOfBlock + "'>" + i + "</a></li>");
				}
			}
			if (endPage < totalPage) {
				sb.append("<li class='page-item'>");
				sb.append("<a class='page-link' href='?p=" + (endPage + 1) + "&s=" + sizeOfPage + "&b=" + sizeOfBlock
						+ "'aria-label='Nexr'>");
				sb.append("<span aria-hidden='true'>&raquo;</span>");
				sb.append("</a>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</nav>");
		}
		return sb.toString();
		
	}
	public String getPageList2() {
		StringBuffer sb = new StringBuffer();
		if(totalCount>0) {
			sb.append("<nav aria-label='Page navigation example'>");
			sb.append("<ul class='pagination pagination-sm justify-content-center'>");
			
			// 이전 : 시작페이지가 1보다 클때만 이전이 있다.
			if(startPage>1) {
				sb.append("<li class='page-item'>");
				sb.append("<a class='page-link' href='javascript:sendPost(\"?\",{\"p\":"+(startPage-1)+",\"s\":"+sizeOfPage+",\"b\":"+sizeOfBlock+"})' aria-label='Previous'>");
				sb.append("<span aria-hidden='true'>&laquo;</span>");
				sb.append("</a>");
			}
			// 페이지 이동
			for(int i=startPage;i<=endPage;i++) {
				if(i==currentPage) { // 현재 페이지는 링크를 걸지 않는다.
					sb.append("<li class='page-item active'  aria-current='page'><a class='page-link'>" + i + "</a></li>");
				}else {
					sb.append("<li class='page-item'><a class='page-link' href='javascript:sendPost(\"?\",{\"p\":"+i+",\"s\":"+sizeOfPage+",\"b\":"+sizeOfBlock+"})'>" + i + "</a></li>");
				}
			}
			// 다음 : 끝페이지가 전체페이지 수 보다 적을때만 다음이 있다.
			if(endPage<totalPage) {
				sb.append("<li class='page-item'>");
				sb.append("<a class='page-link' href='javascript:sendPost(\"?\",{\"p\":"+(endPage+1)+",\"s\":"+sizeOfPage+",\"b\":"+sizeOfBlock+"})' aria-label='Next'>");
				sb.append("<span aria-hidden='true'>&raquo;</span>");
				sb.append("</a>");
				sb.append("</li>");
			}
			
			sb.append("</ul>");
			sb.append("</nav>");
		}
		return sb.toString();
	}
	
}
