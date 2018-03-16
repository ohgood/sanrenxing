package com.sanrenxing.shop.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created on 2017/8/1.
 * @author tony
 */
@Configuration
@MapperScan(basePackages = "com.sanrenxing.shop.db.shop", sqlSessionFactoryRef = "shopSqlSessionFactory")
public class ShopDBConfig {

    @Bean
    @ConfigurationProperties(prefix = "datasource.shop")
    @Primary
    public DataSource shopDataSource() {
        return new HikariDataSource();
    }

    @Bean
    @Primary
    public SqlSessionFactory shopSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(shopDataSource());
        bean.setTypeAliasesPackage("com.sanrenxing.shop.db.shop.bean");
        bean.getObject().getConfiguration().addMappers("com.sanrenxing.shop.db.shop.mapper");
        org.apache.ibatis.session.Configuration configuration = bean.getObject().getConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager shopTransactionManager() throws SQLException {
        return new DataSourceTransactionManager(shopDataSource());
    }

}
