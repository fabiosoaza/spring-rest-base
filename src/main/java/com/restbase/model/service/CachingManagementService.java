package com.restbase.model.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CachingManagementService {

	@Autowired
	private CacheManager cacheManager;

	public Collection<String> listAllCacheNames() {
		Collection<String> cacheNames = cacheManager.getCacheNames();
		return cacheNames;
	}

}
