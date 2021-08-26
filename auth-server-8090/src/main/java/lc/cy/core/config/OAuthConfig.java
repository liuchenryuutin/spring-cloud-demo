package lc.cy.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class OAuthConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;  //jks公钥

    //客户端信息来源
    @Autowired
    private ClientDetailsService clientDetails;

    @Autowired
    private UserDetailsService userDetailsService;  //加载用户信息

    // token保存策略
    @Autowired
    private TokenStore tokenStore;

    // 授权模式专用对象
    @Autowired
    private AuthenticationManager authenticationManager; //认证管理器

    // 授权码模式数据来源
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;  //授权码

    @Bean
    public JwtTokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        return new JwtAccessTokenConverter();
    }


    /**
     * 加载客户端信息来源<br>
     * Bean的名称一定要是clientDetailsService，因为ClientDetailsServiceConfiguration中也定义了这个Bean
     * 我们要用我们的Bean覆盖他，不然下面注入的将不是我们定义的Bean.<br>
     * 在 bootstrap.yml中要配置允许覆盖bean的定义（main.allow-bean-definition-overriding: true）
     * @param dataSource
     * @return
     */
    @Bean(name = "clientDetailsService")
    public ClientDetailsService clientDetailsService(DataSource dataSource){
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean  //令牌管理服务
    public AuthorizationServerTokenServices tokenService() {
        //jwt令牌内容增强
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<TokenEnhancer>();
//        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(delegates); //配置JWT的内容增强器

        // 配置tokenServices参数
        DefaultTokenServices service = new DefaultTokenServices();
        service.setClientDetailsService(clientDetails); //客户端详情服务
        service.setSupportRefreshToken(true); //支持刷新令牌

        service.setTokenStore(tokenStore); //令牌存储,把access_token和refresh_token保存到数据库
        service.setTokenEnhancer(enhancerChain); //配置JWT的内容增强

        service.setAccessTokenValiditySeconds(7200);  // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200);  // 刷新令牌默认有效期3天
        return service;
    }

    // 检查token的策略
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder)
                .tokenKeyAccess("permitAll()")            //oauth/token_key是公开
                .checkTokenAccess("isAuthenticated()");   //oauth/check_token公开
    }

    // 指定客户端信息的数据来源
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory().withClient("stock").  //clientid
//                secret(passwordEncoder.encode("1234"))  //
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token")        // 该client允许的授权类型
//                .scopes("all")  //限制允许的权限配置
//                .accessTokenValiditySeconds(3600)  //token有效时间  秒
//                .refreshTokenValiditySeconds(3600)
//                .redirectUris("http://localhost:8090/login")
//                .autoApprove(true);

        clients.withClientDetails(clientDetails); // 来源于数据库
    }

    // OAuth2的主配置信息
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) // 配置用户名密码认证的管理认证对象,在TokenEndpoint的password模式中用来做用户密码的验证
                .authorizationCodeServices(authorizationCodeServices) // 授权码服务,添加就可以保存到数据库了
                .accessTokenConverter(jwtAccessTokenConverter) // jwt保存的信息
                .tokenServices(tokenService()) // 令牌管理服务,
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST); // 允许GET和POST方式访问TokenEndpoint
    }
}
