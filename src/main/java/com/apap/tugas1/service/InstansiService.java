package com.apap.tugas1.service;

import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.repository.InstansiDb;

public interface InstansiService {
	InstansiDb getInstansiDb();
	InstansiModel getInstansiDetailById(long id);	
}
