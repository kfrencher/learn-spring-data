package com.kfrencher;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;

@Configuration
@ComponentScan
@EnableJpaRepositories(repositoryFactoryBeanClass=JpaRepositoryFactoryBean.class)
@EnableAutoConfiguration
public class TestSimpleJpaConfig {

}
