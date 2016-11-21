package org.xuqi.geophysics.batch;

import java.beans.PropertyVetoException;
import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import org.xuqi.geophysics.mapper.GeophysicsAnomalyMapper;
import org.xuqi.geophysics.mapper.GeophysicsProjectMapper;
import org.xuqi.geophysics.mapper.GeophysicsSurveyAreaMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.xuqi.geophysics.mapper.GeophysicsSurveyLineMapper;


@Configuration
@ComponentScan(basePackages = {"org.xuqi.geophysics.batch"})
@Lazy
@PropertySources(value = {@PropertySource({"classpath:model.properties"})})
public class ModelConfig {
    @Autowired
    public Environment env;

    @Autowired
    private SqlSession sqlSession;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public DataSource getDataSource() throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(env.getProperty("jdbc.driverClassName"));
        comboPooledDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        comboPooledDataSource.setUser(env.getProperty("jdbc.username"));
        comboPooledDataSource.setPassword(env.getProperty("jdbc.password"));
        comboPooledDataSource.setAutoCommitOnClose(false);
        comboPooledDataSource.setCheckoutTimeout(env.getProperty("cpool.checkoutTimeout", Integer.class));
        comboPooledDataSource.setInitialPoolSize(env.getProperty("cpool.minPoolSize", Integer.class));
        comboPooledDataSource.setMinPoolSize(env.getProperty("cpool.minPoolSize", Integer.class));
        comboPooledDataSource.setMaxPoolSize(env.getProperty("cpool.maxPoolSize", Integer.class));
        comboPooledDataSource.setMaxIdleTime(env.getProperty("cpool.maxIdleTime", Integer.class));
        comboPooledDataSource.setAcquireIncrement(env.getProperty("cpool.acquireIncrement", Integer.class));
        comboPooledDataSource
                .setMaxIdleTimeExcessConnections(env.getProperty("cpool.maxIdleTimeExcessConnections", Integer.class));
        return comboPooledDataSource;
    }

    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean() throws PropertyVetoException, IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(getDataSource());
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] mapperLocations = resolver.getResources("classpath:mybatis/**/*.xml");
        sqlSessionFactoryBean.setMapperLocations(mapperLocations);
        return sqlSessionFactoryBean;
    }

    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager() throws PropertyVetoException {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(getDataSource());

        return dataSourceTransactionManager;
    }

    @Bean
    public SqlSession getSqlSessionTemplate() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(getSqlSessionFactoryBean().getObject());
        return sqlSessionTemplate;
    }

    @Bean
    public GeophysicsAnomalyMapper getGeophysicsAnomalyMapper() {
        return sqlSession.getMapper(GeophysicsAnomalyMapper.class);
    }

    @Bean
    public GeophysicsSurveyLineMapper getGeophysicsSurveyLineMapper() {
        return sqlSession.getMapper(GeophysicsSurveyLineMapper.class);
    }

    @Bean
    public GeophysicsSurveyAreaMapper getGeophysicsSurveyAreaMapper() {
        return sqlSession.getMapper(GeophysicsSurveyAreaMapper.class);
    }

    @Bean
    public GeophysicsProjectMapper getGeophysicsProjectMapper() {
        return sqlSession.getMapper(GeophysicsProjectMapper.class);

    }
}
