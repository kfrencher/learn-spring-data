package com.kfrencher;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EnableJpaRepositories(basePackages="com.kfrencher", repositoryFactoryBeanClass=AccessControlRepositoryFactoryBean.class)
@EnableAutoConfiguration
public class TestAccessControlConfig {

}
