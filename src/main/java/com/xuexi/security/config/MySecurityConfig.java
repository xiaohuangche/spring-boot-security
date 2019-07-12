package com.xuexi.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity//开启WebSecurity模式
public class MySecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //定制请求授权规则
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
        //开启自动配置的登录功能,没有登陆没有权限就会来到登陆页面
        http.formLogin().usernameParameter("user").passwordParameter("pwd").
                loginPage("/userlogin");
        /*.loginProcessingUrl("/login")*/
        //1,/login来到登陆页
        //2,重定向到/login?error标识登陆失败
        //3,默认post形式的/login代表处理登陆
        //一旦定制loginPage,那么loginPage的post请求就是登陆

        //开启自动配置的注销功能
        http.logout().logoutSuccessUrl("/");
        //注销成功来到首页
        //1,访问/logout表示用户注销，清空session
        //2,注销成功返回/login?logout

        //开启记住我功能
        http.rememberMe().rememberMeParameter("remeber");
        //登陆成功后将cookie发给浏览器保存，以后访问页面带上这个cookie，只要通过就可以免登陆
        //注销会删除cookie
    }

    /**
     * 认证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        //不加.passwordEncoder(new MyPasswordEncoder())就是不以明文的方式进行匹配会报错
        auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder()).withUser("zhangsan").password("123456").roles("VIP1","VIP2")
                .and()
                .withUser("lisi").password("123456").roles("VIP2","VIP3")
                .and()
                .withUser("wangwu").password("123456").roles("VIP1","VIP3");
    }
}
