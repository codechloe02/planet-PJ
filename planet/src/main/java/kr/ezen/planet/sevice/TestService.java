package kr.ezen.planet.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ezen.planet.dao.TestDAO;

@Service
public class TestService {
	@Autowired
	private TestDAO testDAO;
	
	public String today() {
		return testDAO.today();
	}
}
