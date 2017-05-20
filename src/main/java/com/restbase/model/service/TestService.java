package com.restbase.model.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restbase.model.cache.CacheNames;
import com.restbase.model.domain.Test;
import com.restbase.model.repository.TestRepository;

@CacheConfig
@Service
@Transactional
public class TestService {

	private static final Logger logger = LoggerFactory.getLogger(TestService.class);

	@Autowired
	private TestRepository testRepository;

	public TestService() {
	}

	public List<Test> list() {
		logger.info("Listing Tests");
		return testRepository.findAll();

	}

	public void save(Test test) {
		logger.info("Listing Tests");
		testRepository.save(test);
	}
	
	public void update(Long id, Test test) {
		logger.info("Updating Test");
		Test toUpdate = findById(id);
		testRepository.save(toUpdate);
	}
	
	public void update(UUID id, Test test) {
		logger.info("Updating Test BY UUID");
		Test toUpdate = findByUuid(id);
		testRepository.save(toUpdate);
	}
	
	public Test findById(Long id) {
		logger.info("Finding Test");
		return testRepository.findOne(id);
	}
	
	public Test findByUuid(UUID uuid) {
		logger.info("Finding Test By UUID");
		return testRepository.findOneByUuid(uuid);
	}
	
	public void delete(Long id) {
		logger.info("Deleting Test");
		testRepository.delete(id);
	}
	
	@Cacheable(CacheNames.TEST)
	public Long getPrimeNumberAtPosition(Long enesimPrime) {
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
