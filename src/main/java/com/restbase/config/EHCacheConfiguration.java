package com.restbase.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Configuration;

import com.restbase.model.cache.CacheNames;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;

@EnableCaching
@Configuration
public class EHCacheConfiguration {

	@Value("${cache.defaultCacheConfig.timeToLiveInSeconds:50}")
	private Integer defaultCacheConfigTimeToLiveInSeconds;
	
	@Value("${cache.defaultCacheConfig.maxElementsInMemory:50}")
	private Integer defaultCacheConfigMaxEntriesLocalHeapElementsInMemory;
	
	@Autowired
	private EhCacheCacheManager ehCacheManager;
	
	@PostConstruct
	public void init(){
		net.sf.ehcache.CacheManager cacheManager = ehCacheManager.getCacheManager();	
		
		addCacheConfig(cacheManager, CacheNames.TEST, defaultCacheConfigTimeToLiveInSeconds, defaultCacheConfigMaxEntriesLocalHeapElementsInMemory);
		
		
	}

	/**
	 * 
	 * @param cacheManager
	 * @param cacheName
	 * @param timeToLiveSeconds
	 * @param maxEntriesLocalHeap
	 */
	private void addCacheConfig(net.sf.ehcache.CacheManager cacheManager, String cacheName, long timeToLiveSeconds, long maxEntriesLocalHeap) {
		CacheConfiguration config = new CacheConfiguration();
		config.setTimeToLiveSeconds(timeToLiveSeconds);
		config.setMaxEntriesLocalHeap(maxEntriesLocalHeap);
		config.setName(cacheName);
		
		Cache cache =  new Cache(config);		
		cacheManager.addCache(cache);
	}
	
	
	
	
	
	
}
