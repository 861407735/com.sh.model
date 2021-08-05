package com.sh.model.common;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;

/**
 * description:配置分页插件
 * 
 * @author yangyingdong
 * @date 2018年11月12日
 */
@Configuration
public class PaginationConfig {
	//mybatise plus
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
	//mybatise pagehelper
	@Bean
	public PageInterceptor pageInterceptor() {
		PageInterceptor pageInterceptor = new PageInterceptor();
		Properties properties = new Properties();
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("returnPageInfo", "check");
		properties.setProperty("params", "count=countSql");
		pageInterceptor.setProperties(properties);
		return pageInterceptor;
	}
	//mybatise pagehelper
	@Bean
	public PageHelper pageHelper() {
		PageHelper ph = new PageHelper();
		Properties p = new Properties();
		p.setProperty("offsetAsPageNum", "true");
		p.setProperty("rowBoundsWithCount", "true");
		p.setProperty("reasonable", "true");
		ph.setProperties(p);
		return ph;
	}

}