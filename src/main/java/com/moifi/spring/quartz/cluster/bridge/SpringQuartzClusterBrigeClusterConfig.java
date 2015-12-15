package com.moifi.spring.quartz.cluster.bridge;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.concurrent.Executors;

/**
 * Created by rodrigo on 15/12/15.
 * Basic configuration for the bridge.
 */
@Configuration
@ComponentScan(basePackages = "com.moifi.spring.quartz.cluster.bridge")
public class SpringQuartzClusterBrigeClusterConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    Environment environment;

    @Autowired
    PropertyUtil propertyUtil;


    @Bean(destroyMethod = "shutdown")
    TaskExecutor taskExecutorQuartz() throws ValidationException {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // Get the values from Environment or default values.
        Optional<Integer> corePool =
                Optional.of(propertyUtil.getInteger(environment,"spring.quartz.cluster.bridge.executor.core.pool"));
        corePool.orElse(5);
        Optional<Integer> maxPool =
                Optional.of(propertyUtil.getInteger(environment,"spring.quartz.cluster.bridge.executor.max.pool"));
        maxPool.orElse(10);
        Optional<Integer> queueCapacity =
                Optional.of(propertyUtil.getInteger(environment,"spring.quartz.cluster.bridge.executor.queue.capacity"));
        queueCapacity.orElse(100);
        // set values and initialize
        executor.setCorePoolSize(corePool.get());
        executor.setMaxPoolSize(maxPool.get());
        executor.setQueueCapacity(queueCapacity.get());
        executor.initialize();
        return executor;
    }

    @Bean
    Scheduler scheduler(){
return null;
    }


}
