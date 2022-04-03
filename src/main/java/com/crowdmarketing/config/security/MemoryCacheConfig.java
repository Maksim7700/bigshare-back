package com.crowdmarketing.config.security;

import com.crowdmarketing.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@Slf4j
class MemoryCacheConfig {
    @Bean
    public CacheManager cacheManager() {
        log.info("Configuring memory cache");

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache(SecurityUtils.TOKEN_BLACKLIST)));
        return cacheManager;
    }
}