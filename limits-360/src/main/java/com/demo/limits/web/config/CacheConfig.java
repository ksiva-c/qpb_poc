package com.demo.limits.web.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 
 */

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        List<CacheManager> cacheManagers = new ArrayList<>();
        cacheManagers.add(simpleCacheManager());
        cacheManagers.add(noOpCacheManager());
        compositeCacheManager.setCacheManagers(cacheManagers);
        compositeCacheManager.setFallbackToNoOpCache(true);
        compositeCacheManager.afterPropertiesSet();
        return compositeCacheManager;
    }

    private NoOpCacheManager noOpCacheManager(){
        return new NoOpCacheManager();
    }

    private  SimpleCacheManager simpleCacheManager(){
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        final Collection<ConcurrentMapCache> caches = new ArrayList<ConcurrentMapCache>(Arrays.asList(
                new ConcurrentMapCache("meta"),
                new ConcurrentMapCache("template"),
                new ConcurrentMapCache("data-labels"),
                new ConcurrentMapCache("text-labels"),
                new ConcurrentMapCache("scenario-meta")
        ));
        cacheManager.setCaches(caches);
        cacheManager.afterPropertiesSet();
        return cacheManager;
    }
}
