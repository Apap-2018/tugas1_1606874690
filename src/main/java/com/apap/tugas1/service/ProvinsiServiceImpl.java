package com.apap.tugas1.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDb;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService{ 
	
	@Autowired
	ProvinsiDb provinsiDb;
	
	@Override
	public ProvinsiDb getProvinsiDb() {
		return provinsiDb;
	}

	@Override
	public ProvinsiModel getProvinsiDetailById(long id) {
		return provinsiDb.findById(id);
	}
}
