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
import java.util.Properties;
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

    private final static String DRIVER_DELEGATE_CLASS = "org.quartz.jobStore.driverDelegateClass";
    private final static String TABLE_PREFIX = "org.quartz.jobStore.tablePrefix";
    private final static String MISFIRE_THRESHOLD = "org.quartz.jobStore.misfireThreshold";
    private final static String CLUSTER_CHECK_INTERVAL = "org.quartz.jobStore.clusterCheckInterval";
    private final static String INSTANCE_NAME = "org.quartz.scheduler.instanceName";
    private final static String INSTANCE_ID = "org.quartz.scheduler.instanceId";


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

    @Bean
    Properties quartzProperties(){
        Properties properties =  new Properties();

        addProperty(properties,INSTANCE_NAME,"MyClusteredScheduler");
        addProperty(properties,INSTANCE_ID,"AUTO");
        addProperty(properties,DRIVER_DELEGATE_CLASS,"org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        addProperty(properties,TABLE_PREFIX,"QRTZ_");
        addProperty(properties,MISFIRE_THRESHOLD,"60000");
        addProperty(properties,CLUSTER_CHECK_INTERVAL,"15000");
        properties.setProperty("org.quartz.jobStore.isClustered","true");

        return properties;
    }


    private void addProperty(Properties properties,String key,String defaultValue){
        Optional<String> optional = Optional.of(environment.getProperty(key));
        optional.orElse(defaultValue);
        properties.setProperty(key,optional.get());
    }


}
