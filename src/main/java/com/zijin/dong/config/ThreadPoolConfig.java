package com.zijin.dong.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolConfig.class);
    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 10;
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 100;
    /**
     * 允许线程空闲时间
     */
    private static final int KEEP_ALIVE_TIME = 10;
    /**
     * 缓冲队列数
     */
    private static final int QUEUE_CAPACITY = 200;
    /**
     * 线程池名称前缀
     */
    private static final String THREAD_NAME_PREFIX = "Async-Server-";

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        logger.info("---------- 线程池开始加载 ----------");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        logger.info("---------- 线程池加载完成 ----------");
        return executor;
    }

}
