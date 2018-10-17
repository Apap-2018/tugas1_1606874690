package com.apap.tugas1.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.repository.InstansiDb;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
	
	@Autowired
	InstansiDb instansiDb;
	
	@Override
	public InstansiDb getInstansiDb() {
		return instansiDb;
	}

}
