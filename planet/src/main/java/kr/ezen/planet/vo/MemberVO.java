package kr.ezen.planet.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemberVO implements UserDetails {

	private static final long serialVersionUID = -351776552684176127L;
	private int id; // NOT NULL, PRIMARY KEY
	private String email; // NOT NULL
	private String nickname; // NOT NULL
	private String password; // NOT NULL
	private String name; // NOT NULL
	private Date createDate; // NOT NULL
	private int reliability; // NOT NULL
	private String phonenumber;
	private String addr;
	private String addr_d;
	private String zipcode;
	private String role; // NOT NULL

	// 유저 디테일스 인터페이스 구현
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		// ROLE_ADMIN, ROLE_USER 형태로 저장되었을떄
		String[] roles = role.split(",");
		for (int i = 0; i < roles.length; i++) {
			authorities.add(new SimpleGrantedAuthority(roles[i].trim()));
		}
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

}
