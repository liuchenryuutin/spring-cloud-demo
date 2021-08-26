package lc.cy.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SecurityProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = ((UsernamePasswordAuthenticationToken) authentication).getName();
		UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
		if (!userDetails.getUser().getPassword()
				.equals((String) ((UsernamePasswordAuthenticationToken) authentication).getCredentials())) {
			throw new UsernameNotFoundException("用户名或者密码错误");
		}
		return new UsernamePasswordAuthenticationToken(userDetails.getUser(),authentication.getCredentials(), userDetails.getAuthorities());

	}

	public boolean supports(Class<?> authentication) {
		return authentication == UsernamePasswordAuthenticationToken.class;
	}

}
