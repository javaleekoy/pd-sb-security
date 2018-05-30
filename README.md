#### spring-boot shiro
````text

--> application(请求) 
--> shiroFilter(过滤器验证) 
--> login(登录页面) 
--> SecurityUtils.Subject.login(登录参数) 
--> AuthorizingRealm.doGetAuthenticationInfo(登录验证) 
--> login success(登录成功) 
--> index(登录成功页面) 
--> AuthorizingRealm.doGetAuthorizationInfo(权限认证) 
--> OK

````