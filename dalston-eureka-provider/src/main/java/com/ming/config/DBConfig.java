package com.ming.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DBConfig {

    public static final String DB_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "root";
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/Cloud?useUnicode=true&characterEncoding=utf-8&useSSL=false&createDatabaseIfNotExist=true";
    public static final String JDBC_URL_A = "jdbc:mysql://localhost:3306/Cloud1?useUnicode=true&characterEncoding=utf-8&useSSL=false&createDatabaseIfNotExist=true";
    public static final String JDBC_URL_B = "jdbc:mysql://localhost:3306/Cloud2?useUnicode=true&characterEncoding=utf-8&useSSL=false&createDatabaseIfNotExist=true";
    public static final int MAX_INITIAL_SIZE = 5;
    public static final int MIN_IDLE_SIZE = 5;
    public static final int MAX_ACTIVE_SIZE = 5;
    public static final int MAX_POOL_SIZE = 10;
    public static final String DRUID_FILTERS = "stat,wall,slf4j";

    @Profile("peer1")
    @Bean
    public HikariDataSource peer1DataSource(){
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(DB_DRIVER_CLASS_NAME);
        ds.setJdbcUrl(JDBC_URL);
        ds.setUsername(DB_USERNAME);
        ds.setPassword(DB_PASSWORD);
        ds.setMaximumPoolSize(MAX_POOL_SIZE);
        return ds;
    }

    @Profile("peer2")
    @Bean
    public DruidDataSource peer2DataSource(){
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(DB_DRIVER_CLASS_NAME);
        ds.setUrl(JDBC_URL);
        ds.setUsername(DB_USERNAME);
        ds.setPassword(DB_PASSWORD);
        ds.setInitialSize(MAX_INITIAL_SIZE);
        ds.setMinIdle(MIN_IDLE_SIZE);
        ds.setMaxActive(MAX_ACTIVE_SIZE);
        try {
            ds.setFilters(DRUID_FILTERS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    private static final Logger log = LoggerFactory.getLogger(DBConfig.class);

    @Profile("peer2")
    @Bean
    public ServletRegistrationBean druidServlet() {
        log.info("init Druid Servlet Configuration ");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("loginUsername", "admin");// 用户名
        initParameters.put("loginPassword", "admin");// 密码
        initParameters.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
        initParameters.put("allow", ""); // IP白名单 (没有配置或者为空，则允许所有访问)
        //initParameters.put("deny", "192.168.20.38");// IP黑名单 (存在共同时，deny优先于allow)
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

    @Profile("peer2")
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
