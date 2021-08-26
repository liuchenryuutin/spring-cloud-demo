package lc.cy.core.config;

import lc.cy.core.security.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider securityProvider;

    /**
     * 配置用户认证需要的authenticationProvider。
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(securityProvider);
    }

    /**
     * AuthenticationManager对象在OAuth2认证服务中要使用，提前放入IOC容器中
     * 注意这里的方面名一定不能是authenticationManager,这样会覆盖父类中的同名方法，使得配置出错。
     */
    @Bean
    public AuthenticationManager standAuthenticationManager()throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置login画面和logout画面
     * 放行"/oauth/*"的URL
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginProcessingUrl("/login").permitAll().
                and().logout().logoutUrl("/logout").logoutSuccessUrl("/").
                and().authorizeRequests().antMatchers("/oauth/token").permitAll().
                and().authorizeRequests().antMatchers("/oauth/error").permitAll().
                and().authorizeRequests().antMatchers("/oauth/check_token").permitAll().
                and().authorizeRequests().antMatchers("/oauth/token_key").permitAll().
                and().authorizeRequests().antMatchers("/test/*").permitAll().
                and().authorizeRequests().anyRequest().authenticated().
                and().csrf().disable();
    }

}
