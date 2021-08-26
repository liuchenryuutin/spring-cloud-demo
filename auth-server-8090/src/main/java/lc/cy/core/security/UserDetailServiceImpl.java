package lc.cy.core.security;

import lc.cy.core.dao.LoginDao;
import lc.cy.core.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private LoginDao loginDao;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		try {
			user = loginDao.getUserByID(username);
		} catch (Exception e) {
			throw new UsernameNotFoundException("用户没有找到！");
		}

		if (user == null) {
			throw new UsernameNotFoundException("用户没有找到！");
		} else {
			List<String> roles = Arrays.asList("DEFAULT");
			List<GrantedAuthority> authorities = getRoles(roles);
			return new UserDetailsImpl(user, authorities);
		}
	}

	private List<GrantedAuthority> getRoles(List<String> roles) {
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			result.add(new SimpleGrantedAuthority(role));
		}
		return result;
	}

}
