package auth.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(a -> a
                .antMatchers("/").permitAll()
                .anyRequest().authenticated())
            .csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            .logout(l -> l.logoutSuccessUrl("/").permitAll())
            .oauth2Login(o -> o
                .successHandler((request, response, authentication) -> {
                   RespUtils.responseJson(response, authentication);
                }));
    }


    /**
     *  授权服务器 和 cilent 要在两台机器 不然cookie 会覆盖（覆盖cookie 是以ip 为单位）
     *  下面的 ip地址要填写相对应的ip
     * @return
     */
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {

        // 配置码云 授权服务器
        // registerId 指定服务的名称，例子中，是 aaa。这是个ID，名字随便取。主要用于区分环境中存在种OAuth2服务。
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("gitee")
                .clientId("8adcd9dcdb1d0972cc7ab211560345adf4f8b396b174271fc6d1f0e3a320ecab")// clientId是服务分配的一个client_id，在OAuth2服务那里注册Application时分配cilentId
                .clientSecret("2d2415a0e2fff94a9385a5f6c79e8f700c5f1de00861c45c12313f3154278a9a")
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)//(4) 服务端支持的认证方式
                .clientName("clientName")//随便写
                .tokenUri("https://gitee.com/oauth/token")
                .authorizationUri("https://gitee.com/oauth/authorize")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .scope("openid")  // (10)
                .userNameAttributeName("name")  // (11)
                .userInfoUri("https://gitee.com/api/v5/user")
                .redirectUriTemplate("http://127.0.0.1:8888/login/oauth2/code/gitee")
                .jwkSetUri("")  // (13)
                .build();

        // 配置自定义授权 服务器
        ClientRegistration clientRegistration1 = ClientRegistration.withRegistrationId("merchant")
                .clientId("cilentId")// clientId是服务分配的一个client_id，在OAuth2服务那里注册Application时分配cilentId
                .clientSecret("secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)//(4) 服务端支持的认证方式
                .clientName("clientName")//随便写
                .tokenUri("http://127.0.0.1:9000/oauth2/token") // 授权服务器获取token接口
                .authorizationUri("http://127.0.0.1:9000/oauth2/authorize") // 授权服务器获取code 接口
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope(OidcScopes.OPENID)  // (10)
                .scope("read")
                .userNameAttributeName("username")  // (11)
                .userInfoUri("https://127.0.0.1:9000/getUser")// 资源服务器 获取用户
                .redirectUri("http://127.0.0.1:8888/login/oauth2/code/merchant")  // 客户端回调
                .jwkSetUri("")  // (13)
                .build();
        return new InMemoryClientRegistrationRepository(clientRegistration,clientRegistration1);
    }

}
