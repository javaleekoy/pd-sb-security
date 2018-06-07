#### spring-boot shiro

login
````text
localhost/shiro/login
````

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

限制登陆时密码输入错误次数

```java
依赖jar包
<!-- shiro-ehcache -->
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-ehcache</artifactId>
    <version>1.4.0</version>
</dependency>

使用ehcache纪录密码输入次数，设定密码错误是禁止登录时间
<?xml version="1.0" encoding="UTF-8"?>
<ehcache name="pd">
    <diskStore path="java.io.tmpdir"/>
    <cache name="pdRetryLimitPassword"
           maxEntriesLocalHeap="2000"
           eternal="false"
           timeToIdleSeconds="3600"
           timeToLiveSeconds="0"
           overflowToDisk="false"
           statistics="true">
    </cache>
</ehcache>



//继承 HashedCredentialsMatcher 重写 doCredentialsMatch 方法

public class PdCredentialsMatcher extends HashedCredentialsMatcher {

    private Cache cache;

    public PdCredentialsMatcher() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:" + EHCACHE_XML_PATH);
        cache = ehCacheManager.getCache(PASSWORD_RETRY_EHCACHE_NAME);
    }


    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        /****登录密码错误次数严重****/
        String userName = (String) token.getPrincipal();
        Object object = cache.get(userName);
        if (object == null) {
            cache.put(userName, new AtomicInteger(0));
        }
        AtomicInteger atomicInteger = (AtomicInteger) cache.get(userName);
        if (atomicInteger.incrementAndGet() > PASSWORD_RETRY_TIME) {
            throw new ExcessiveAttemptsException();
        }
        /***登陆密码严重***/
        boolean matcher = super.doCredentialsMatch(token, info);
        if (matcher) {
            cache.remove(userName);
        }
        return matcher;
    }
}

```
身份验证和权限验证

```java
//继承 AuthorizingRealm 重写 doGetAuthorizationInfo(权限) 和 doGetAuthenticationInfo(认证)

@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        getAvailablePrincipal(principalCollection);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Principal userInfo = (Principal) principalCollection.getPrimaryPrincipal();
        /*for (SysRole role : userInfo.getRoles()) {
            simpleAuthorizationInfo.addRole(role.getRole());
            for (String permission : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permission);
            }
        }*/
        return simpleAuthorizationInfo;
    }

    /**
     * 登录身份验证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) 
    throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = (String) token.getPrincipal();
        UserDto userDto = userService.queryUserInfo(username);
        if (userDto == null) {
            return null;
        }
        if (userDto.getIsDisabled() == 1 || userDto.getDel() == 1) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                new Principal(userDto),
                userDto.getPassword(),
                ByteSource.Util.bytes(SALT),
                getName()
        );
        return authenticationInfo;
    }

```
泛型

```java
//dao泛型类
public interface CrudDao<T> extends BaseDao {

    T get(Long id);

    T get(T model);

    List<T> queryList(T model);

    Integer insert(T model);

    Integer update(T model);

    Integer del(Long id);

    Integer del(T model);

}

//model泛型类 
public class DataModel<T> extends BaseModel<T> {

    private Integer del;
    private Date createTime;
    private Date updateTime;
    private String remark;

    ......
}

//server泛型类
@Transactional(readOnly = true, rollbackFor = Exception.class)
public abstract class CrudService<D extends CrudDao<T>, T extends DataModel<T>> extends BaseService {

    @Autowired
    protected D dao;

    public T get(Long id) {
        return dao.get(id);
    }

    public List<T> queryList(T model) {
        return dao.queryList(model);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(T model) {
        dao.insert(model);
    }

    ......

}

//example:

 @Service
 @Transactional(readOnly = true, rollbackFor = Exception.class)
 public class UserService extends CrudService<UserMapper, User> {
 
     public UserDto queryUserInfo(String userName) {
         User user = dao.queryInfoByLoginName(userName);
         UserDto dto = UserDto.newInstance();
         BeanUtils.copyProperties(user, dto);
         return dto;
     }
 
 }

```
自定义 sessionDao
```java
//接口扩展类
public interface PdSessionDao extends SessionDAO{
}

//实现类
public class PdRedisSessionDao extends AbstractSessionDAO implements PdSessionDao {
}

```

自定义 cacheManager
```java

//缓存操作类
public class PdRedisCache<K, V> implements Cache<K, V> {
}

//缓存管理类
public class PdRedisCacheManager implements CacheManager {
}
```
shiro非注解授权
```java
@Bean
public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    /**不是使用注解标签时可以这样定义标签权限**/
    filterChainDefinitionMap.put("/user/hello", "perms[pd:hello:view]");
    return shiroFilterFactoryBean;
}
```

shiro注解
```java
/**
 * 开启shiro的注解支持（权限标签）
 *
 * @return
 */
@Bean
public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
    creator.setProxyTargetClass(true);
    return creator;
}

/**
 * 使用shiro框架提供的切面类，用于创建代理对象
 *
 * @param securityManager
 * @return
 */
@Bean
public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
    advisor.setSecurityManager(securityManager);
    return advisor;
}
```
thymeleaf应用shiro标签
```java

注意thymeleaf-extras-shiro与spring的版本
<!-- thymeleaf-extras-shiro -->
<dependency>
    <groupId>com.github.theborakompanioni</groupId>
    <artifactId>thymeleaf-extras-shiro</artifactId>
    <version>1.2.1</version>
</dependency>

/**
 * thymeleaf-shiro:thymeleaf支持shiro标签解析
 *
 * @return
 */
@Bean
public ShiroDialect getShiroDialect() {
    return new ShiroDialect();
}
```