package com.rest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T>{
	
	Page<T> getAll(Pageable pageable);
	T add (T o);
	T update(T o,int id);
	T getById(int id);
	T deleteById(int id);
	
	
	
}