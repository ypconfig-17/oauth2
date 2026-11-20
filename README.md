# spring oauth2

## 项目入口与验证

这个 Java 8 示例把 OAuth2 授权端、客户端和资源服务器放在单独的 Maven 模块中。面试或阅读时，可沿着「发起授权 → 获取令牌 → 调用受保护资源」三段流程分别查看实现。

- [授权服务器入口](oauth-server/src/main/java/auth/server/ServerApplication.java)
- [客户端入口](oauth-client/src/main/java/auth/client/ClientApplication.java)
- [资源服务器入口](oauth-resource/src/main/java/auth/reource/ResourceApplication.java)

使用 JDK 8 和 Maven，可在仓库根目录以 `mvn clean verify` 检查聚合构建。联调前核对三个模块的端口、回调地址和测试凭据。

### 跟踪一次授权请求

1. 从[授权服务器配置](oauth-server/src/main/java/auth/server/config/AuthorizationServerConfig.java) 查看授权服务和客户端注册。
2. 从[客户端控制器](oauth-client/src/main/java/auth/client/config/OAuth2Controller.java) 查看登录后如何读取用户信息。
3. 从[资源服务器配置](oauth-resource/src/main/java/auth/reource/config/ResourceServerConfig.java) 和 [`/getUser` 实现](oauth-resource/src/main/java/auth/reource/web/UserController.java) 查看资源端如何校验令牌。

本地联调时，可分别检查未携带令牌和携带有效测试令牌的请求。示例中的令牌占位符需要换成自己在测试环境申请的短期令牌。离线编译在当前环境缺少 Maven 父 POM，需要可解析旧版依赖的 Maven 仓库。

### 三个模块各自决定什么

| 模块 | 读代码时先看什么 |
| --- | --- |
| 授权端 | 客户端如何注册，授权规则和签发配置放在哪里。 [AuthorizationServerConfig](oauth-server/src/main/java/auth/server/config/AuthorizationServerConfig.java) |
| 客户端 | 何处启用 OAuth2 登录，登录完成后控制器如何读取用户。 [SecurityConfig](oauth-client/src/main/java/auth/client/config/SecurityConfig.java) · [OAuth2Controller](oauth-client/src/main/java/auth/client/config/OAuth2Controller.java) |
| 资源端 | 资源端如何配置令牌校验，哪些请求进入用户查询。 [ResourceServerConfig](oauth-resource/src/main/java/auth/reource/config/ResourceServerConfig.java) · [UserController](oauth-resource/src/main/java/auth/reource/web/UserController.java) |

调试时可以依次检查客户端注册与回调地址、授权服务器签发结果、资源服务器校验结果。每一步都对应独立模块，便于定位登录跳转正常但 API 请求失败的情况。

下面是原有协议和配置笔记。










## 版本

2018 年初，我们[宣布](https://spring.io/blog/2018/01/30/next-generation-oauth-2-0-support-with-spring-security)Spring Security OAuth 项目正式进入维护模式。我们已经停止支持 2.0.x，与 Boot 的 1.x End-of-Life (EOL) 以及 2.1.x 和 2.2.x 一致。我们的计划是在不久的将来停止剩余的支持。

当前支持的分支是 2.3.x 和 2.4.x。2.3.x 系列将于 2020 年 3 月停产。我们将在达到功能对等后至少一年支持 2.4.x 系列。

为此，随着 Spring Security 5.2 的发布，我们强烈鼓励用户[开始将其遗留的 OAuth 2.0 客户端和资源服务器](https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Migration-Guide)应用程序[迁移](https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Migration-Guide)到 Spring Security 5.2 中的新支持。

spring-security 5.2.x 并不能完全替代以上的项目提供的功能，只是将上面中部分主要的功能先集成进来

预计在未来 spring-security 5.3.x(5.3第一个版本大约在2020.3) 之后的版本，将可以只使用 spring-security，不需要在学习以上的其他的部分


```java
原因是Oauth2 落地混乱：Spring Security OAuth、Spring Cloud Security、Spring Boot 1.5.x 以及当时最新的 Spring Security5.x 中都提供了对 OAuth2 的实现。因此官方要统一放在一个地方。
```



```java
在 `Spring Security 5`中，现在已经不提供了 `授权服务器` 的配置，但是 **授权服务器** 在我们平时的开发过程中用的还是比较多的。不过 Spring 官方提供了一个 由Spring官方主导，社区驱动的授权服务 `spring-authorization-server`，目前已经到了 0.1.2 的版本，不过该项目还是一个实验性的项目，不可在生产环境中使用，此处来使用项目搭建一个简单的授权服务器。
```









**springcloud 版本** 

```xml
<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-oauth2 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-oauth2</artifactId>
    <version>2.2.4.RELEASE</version>
</dependency>
```

**springboot 版本**

```xml
<dependency>
    <groupId>org.springframework.security.oauth</groupId>
    <artifactId>spring-security-oauth2</artifactId>
    <version>2.5.0.RELEASE</version>
</dependency>
```





**springsecurity5**

```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-oauth2-core</artifactId>
    <version>5.5.0</version>
</dependency>
<dependency>
  <groupId>org.springframework.security</groupId>
  <artifactId>spring-security-oauth2-client</artifactId>
</dependency>

<dependency>
  <groupId>org.springframework.security</groupId>
  <artifactId>spring-security-oauth2-resource-server</artifactId>
</dependency>
```





**最新版本 授权服务器** 

实验项目2020-8月--2021-7月

```xml
<dependency>
    <groupId>org.springframework.security.experimental</groupId>
    <artifactId>spring-security-oauth2-authorization-server</artifactId>
    <version>0.1.2</version>
</dependency>

```

正式项目 2021-8月 开始

```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-oauth2-authorization-server</artifactId>
    <version>0.2.1</version>
</dependency>
```



结论:

- 优先选择 Spring 官方推荐的 spring security 5.2.x +
- 在 spring security5.3.x 前可以选择使用 `spring-security-oauth`（未来终将被 spring security5.x取代）
- 不要再使用 `spring-security-xxx`、`spring-social`等
- 不推荐使用 `spring-cloud-starter-oauth2`、`spring-cloud-starter-security`（有计划被废弃）



**spring-security-oauth2 版本的教程网上有很多，这里不多赘述，但是已经不推荐使用**

**个人觉得原因**

- 流程不够标准
- 组件不够清
- 安全性不如新版本



接下来分析 spring security 包下Oauth2

1. **spring-security-oauth2-client    客户端**
2. **spring-security-oauth2-resource-server 资源服务器**
3. **spring-security-oauth2-authorization-server 授权服务器（spring 妥协用户开发的新版授权服务器）**
4. **spring-security-oauth2-jose     Javascript 对象签名和加密（包括jwt kws jwe jwk）**

## 授权模式

- OAuth2有4种授权模式：1授权码模式 2简化模式 3密码模式 4客户端模式（在 2.1版本 密码模式和简化模式被移除）
- OAuth2分为3个部分：1认证服务器  2资源服务器 3第三方应用

| **GRANT_TYPE**     | **Description**                                              | 备注 |
| :----------------- | ------------------------------------------------------------ | ---- |
| authorization_code | 授权码模式(即先登录获取code，再获取token) [最常用]           |      |
| password           | 密码模式(将用户名、密码传过去，直接获取token) [适用于移动设备] |      |
| client_credentials | 客户端模式(无用户，用户向客户端注册，然后客户端以自己的名义向'服务端'获取资源) |      |
| implicit           | 简化模式(在redirect_uri的Hash传递token，客户端运行在浏览器中，如JS、Flash) |      |
| refresh_token      | 更新access_token                                             |      |

### 端点 endpoint

**/oauth2/authorize 获取授权码**

```xml
http://localhost:9000/oauth2/authorize?response_type=code&client_id=cilentId&scope=read&redirect_uri=http://www.baidu.com&state=state
```

**/oauth2/token 获取access_token. 需要 http basic 加密**

```xml
"Authorization: Basic Y2lsZW50SWQxOnNlY3JldDE="

http://localhost:9000/oauth2/token?grant_type=authorization_code&code=38YHBf-KWEkWV1gR6PSYhc2fYtQF5IhRY--uZnj2gQnJvK0vUl2b7dx6mCXRjNEFZgU2-SLn3yZf-FmeJiE8XSBJOYMOnw7uOA2qn9b7ebIVFn6ttDtUlp0IXSCBQ-T3&redirect_uri=http://www.baidu.com
```

**请求资源 需要 http basic 加密**

```xml
"Authorization: Bearer <ACCESS_TOKEN>"


http://localhost:8000/api/getResource 
```



### spring-security-oauth2-client  

**spring-security-oauth2-client 客户端 支持openid connect 1.0 协议**

ClientRegistrationRepository 是客户端 三方登录的 元信息

可以外部化配置  

```java
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        // registerId 指定服务的名称 一般github 为github
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("registerId")
                .clientId("cilentId")//三方服务分配的一个cilentId
                .clientSecret("secret")//三方服务分配的一个secret
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)//(4) 服务端支持的认证方式
                .clientName("clientName")//随便写
                .tokenUri("http://127.0.0.1:9000/oauth2/token")
                .authorizationUri("http://127.0.0.1:9000/oauth2/authorize")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("openid")  // (10)
                .userNameAttributeName("username")  // (11)
                .userInfoUri("http://127.0.0.1:9000/getUser")
                .redirectUri("http://127.0.0.1:9001/login/oauth2/code/registerId")
                .jwkSetUri("")  // (13)
                .build();
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }
```



**核心过滤器**

```java
OAuth2AuthorizationRequestRedirectFilter  oauth授权请求重定向过滤器 默认uri "/oauth2/authorization"
  
OAuth2AuthorizationCodeGrantFilter   OAuth2 授权码授予过滤器
  
OAuth2LoginAuthenticationFilter    OAuth2 登录认证过滤器
```

 

**自动装配 org.springframework.boot.autoconfigure.security.oauth2.client.servlet. 包下,类**



```java
OAuth2ClientAutoConfiguration 配置入口

OAuth2ClientRegistrationRepositoryConfiguration  三方授权服务器相关配置
  
OAuth2WebSecurityConfiguration  web配置
```



**首先获取配置的选项 有没有公共三方配置,目前支持 GITHUB, GOOGLE,OKTA**

```java
public enum CommonOAuth2Provider {
    GOOGLE {
        public Builder getBuilder(String registrationId) {
            Builder builder = this.getBuilder(registrationId, ClientAuthenticationMethod.BASIC, "{baseUrl}/{action}/oauth2/code/{registrationId}");
            builder.scope(new String[]{"openid", "profile", "email"});
            builder.authorizationUri("https://accounts.google.com/o/oauth2/v2/auth");
            builder.tokenUri("https://www.googleapis.com/oauth2/v4/token");
            builder.jwkSetUri("https://www.googleapis.com/oauth2/v3/certs");
            builder.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo");
            builder.userNameAttributeName("sub");
            builder.clientName("Google");
            return builder;
        }
    },
    GITHUB {
        public Builder getBuilder(String registrationId) {
            Builder builder = this.getBuilder(registrationId, ClientAuthenticationMethod.BASIC, "{baseUrl}/{action}/oauth2/code/{registrationId}");
            builder.scope(new String[]{"read:user"});
            builder.authorizationUri("https://github.com/login/oauth/authorize");
            builder.tokenUri("https://github.com/login/oauth/access_token");
            builder.userInfoUri("https://api.github.com/user");
            builder.userNameAttributeName("id");
            builder.clientName("GitHub");
            return builder;
        }
    },
  ........
```



**核心 处理器**

```java
OAuth2LoginAuthenticationProvider
```



### spring-security-oauth2-resource-server 资源服务器



 资源服务器的 核心配置是在core 包， 资源服务器  使核心过滤器拦截 jwt 的token 并且解析 认证

自动装配

```
OAuth2ResourceServerAutoConfiguration
```

过滤器配置

```java
@Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
   http
         .authorizeRequests()
            .mvcMatchers("/getUser")
     				// oauth2 权限是以 SCOPE_ 开头
            .access("hasAuthority('SCOPE_read')")
            .and()
      .oauth2ResourceServer()
         .jwt();
   DefaultSecurityFilterChain build = http.build();
   return build;
}
```

核心过滤器，如果请求头包含bearerToken 则拦截

```java
BearerTokenAuthenticationFilter
```

```java
bearerTokenString token;
try {
	 // 获取bearerToken
   token = this.bearerTokenResolver.resolve(request);
}
catch (OAuth2AuthenticationException invalid) {
   this.logger.trace("Sending to authentication entry point since failed to resolve bearer token", invalid);
   this.authenticationEntryPoint.commence(request, response, invalid);
   return;
}
if (token == null) {
   this.logger.trace("Did not process request since did not find bearer token");
   filterChain.doFilter(request, response);
   return;
}
```

核心处理器 能根据jwt算法和加密参数 正常解析 bearerToken则 认证通过 

```
JwtAuthenticationProvider
```

```java
@Override
public Authentication authenticate(Authentication authentication) throws AuthenticationException {
   BearerTokenAuthenticationToken bearer = (BearerTokenAuthenticationToken) authentication;
   Jwt jwt = getJwt(bearer);
  //  解析jwt 加密数据
   AbstractAuthenticationToken token = this.jwtAuthenticationConverter.convert(jwt);
   token.setDetails(bearer.getDetails());
   this.logger.debug("Authenticated token");
   return token;
}
```





### spring-security-oauth2-authorization-server  

新版授权服务器

### 

获取code  端点 DEFAULT_AUTHORIZATION_ENDPOINT_URI = "/oauth2/authorize";

```
OAuth2AuthorizationEndpointFilter
```

处理器

```java
class OAuth2AuthorizationCodeRequestAuthenticationProvider{

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		OAuth2AuthorizationCodeRequestAuthenticationToken authorizationCodeRequestAuthentication =
				(OAuth2AuthorizationCodeRequestAuthenticationToken) authentication;
		//isConsent() 是否授权同意
		return authorizationCodeRequestAuthentication.isConsent() ? 
				authenticateAuthorizationConsent(authentication) :
				authenticateAuthorizationRequest(authentication);
	}

}


```



获取token  端点DEFAULT_TOKEN_ENDPOINT_URI = "/oauth2/token";

过滤器

```
OAuth2ClientAuthenticationFilter  // 校验 cilent_id 和serct

OAuth2TokenEndpointFilter //校验code
```

处理器

```
OAuth2ClientAuthenticationProvider

OAuth2AuthorizationCodeAuthenticationProvider
```



