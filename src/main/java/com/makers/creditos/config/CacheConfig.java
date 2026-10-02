package com.makers.creditos.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@org.springframework.cache.annotation.EnableCaching
public class CacheConfig {

	public static final String PRESTAMOS = "prestamos";

	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager manager = new CaffeineCacheManager(PRESTAMOS);
		manager.setCaffeine(Caffeine.newBuilder()
				.expireAfterWrite(Duration.ofMinutes(5))
				.maximumSize(500));
		return manager;
	}
}
