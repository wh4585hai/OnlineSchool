package com.stylefeng.guns.config.web;

import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.shiro.DefautModularRealm;
import com.stylefeng.guns.core.shiro.ShiroDbRealm;
import com.stylefeng.guns.core.shiro.StudentDbRealm;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * shiro权限管理的配置
 *
 * @author fengshuonan
 * @date 2016年11月14日 下午3:03:44
 */
@Configuration
public class ShiroConfig {

    /**
     * 安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager(CookieRememberMeManager rememberMeManager, CacheManager cacheShiroManager, DefaultWebSessionManager defaultWebSessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
      //  securityManager.setRealm(this.shiroDbRealm());
        List<Realm> realms = new ArrayList<Realm>();
        ShiroDbRealm sr = this.shiroDbRealm();
        StudentDbRealm st=this.studentDbRealm();
        realms.add(sr);
        realms.add(st);
        DefautModularRealm dr = new DefautModularRealm();
        Map<String, Object> definedRealms = new HashMap<String,Object>();
        definedRealms.put("shiroDbRealm", sr);
        definedRealms.put("studentDbRealm", st);
        dr.setDefinedRealms(definedRealms);
        securityManager.setRealms(realms);
        securityManager.setAuthenticator(dr);
        securityManager.setCacheManager(cacheShiroManager);
        securityManager.setRememberMeManager(rememberMeManager);
        securityManager.setSessionManager(defaultWebSessionManager);
        return securityManager;
    }

    /**
     * session管理器
     */
    @Bean
    public DefaultWebSessionManager defaultWebSessionManager(CacheManager cacheShiroManager, GunsProperties gunsProperties) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(cacheShiroManager);
        sessionManager.setSessionValidationInterval(gunsProperties.getSessionValidationInterval() * 1000);
        sessionManager.setGlobalSessionTimeout(gunsProperties.getSessionInvalidateTime() * 1000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setName("shiroCookie");
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }


    /**
     * 缓存管理器 使用Ehcache实现
     */
    @Bean
    public CacheManager getCacheShiroManager(EhCacheManagerFactoryBean ehcache) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(ehcache.getObject());
        return ehCacheManager;
    }

    /**
     * 项目自定义的Realm
     */
    @Bean
    public ShiroDbRealm shiroDbRealm() {
        return new ShiroDbRealm();
    }
    @Bean
    public StudentDbRealm studentDbRealm() {
        return new StudentDbRealm();
    }
    /**
     * rememberMe管理器, cipherKey生成见{@code Base64Test.java}
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decode("Z3VucwAAAAAAAAAAAAAAAA=="));
        manager.setCookie(rememberMeCookie);
        return manager;
    }

    /**
     * 记住密码Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(7 * 24 * 60 * 60);//7天
        return simpleCookie;
    }

    /**
     * Shiro的过滤器链
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        /**
         * 默认的登陆访问url
         */
        shiroFilter.setLoginUrl("/login");
        /**
         * 登陆成功后跳转的url
         */
        shiroFilter.setSuccessUrl("/admin");
        /**
         * 没有权限跳转的url
         */
        shiroFilter.setUnauthorizedUrl("/global/error");
        /**
         * 配置shiro拦截器链
         *
         * anon  不需要认证
         * authc 需要认证
         * user  验证通过或RememberMe登录的都可以
         *
         */
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("/", "anon");
        hashMap.put("/front", "anon");
        hashMap.put("/front/**", "anon");
        hashMap.put("/*/**.jpg", "anon");
        hashMap.put("/static/**", "anon");
        hashMap.put("/login", "anon");
        hashMap.put("/global/sessionError", "anon");
        hashMap.put("/kaptcha", "anon");
        hashMap.put("/admin", "user");
        hashMap.put("/admin/**", "user");
        shiroFilter.setFilterChainDefinitionMap(hashMap);
        return shiroFilter;
    }

    /**
     * 在方法中 注入 securityManager,进行代理控制
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(new Object[]{securityManager});
        return bean;
    }

    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    @DependsOn(value = "lifecycleBeanPostProcessor") //依赖其他bean的初始化
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
