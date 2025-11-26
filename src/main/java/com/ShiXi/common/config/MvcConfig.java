package com.ShiXi.common.config;

import com.ShiXi.common.utils.LoginInterceptor;
import com.ShiXi.common.utils.RefreshTokenInterceptor;
import com.ShiXi.common.utils.IdentificationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //TODO 权限拦截器 权限身份已经保留在Userholder当中 获取用户当前使用的身份


        // token刷新的拦截器
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).addPathPatterns("/**").order(0);

        //登录拦截器 输入restful风格拦截
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/shop/**",
                        "/voucher/**",
                        "/shop-type/**",
                        "/upload/**",
                        "/blog/hot",
                        "/user/code",
                        "/user/login",
                        "/user/login/**",
                        "/student/**",
                        "/ws/**",
                        "/application/**",
                        "/doc.html", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**", "/swagger-resources/**","/doc.html#/home",
                        "/resume/recommendation/recommendByCategory",
                        "/position/query/searchJobs",
                        "/blog/queryHotBlogs"
                ).order(1);

        // 身份认证拦截器
        registry.addInterceptor(new IdentificationInterceptor())
                .addPathPatterns("/**")
                .order(2);





    }
}
