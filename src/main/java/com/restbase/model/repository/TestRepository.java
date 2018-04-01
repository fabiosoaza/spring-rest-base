package com.restbase.model.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restbase.model.domain.Test;


@Repository
public interface TestRepository extends CrudRepository<Test, Long> {

		
	List<Test> findAll(); 
	
	Test findOneByUuid(UUID uuid);
}
