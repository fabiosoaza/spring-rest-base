package com.restbase.model.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.restbase.model.cache.CacheNames;

@CacheConfig
@Service
public class CacheTesterService {
	
	@Cacheable(CacheNames.TEST)
	public Long getPrimeNumberAtPosition(Long enesimPrime) {
		long primeNumber = computePrimeNumber(enesimPrime);
		return primeNumber;
	}

	protected long computePrimeNumber(Long enesimPrime) {
		long primeCounter = 0;
		long primeNumber = 0;
		for (long i = 0; primeCounter <= enesimPrime; i++) {
			if (isPrime(i)) {
				primeCounter++;
				primeNumber = i;
			}
		}
		return primeNumber;
	}

	private boolean isPrime(long n) {
		// check if n is a multiple of 2
		if (n % 2 == 0)
			return false;
		// if not, then just check the odds
		for (long i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

}
