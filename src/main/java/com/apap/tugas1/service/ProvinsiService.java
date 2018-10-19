package com.apap.tugas1.service;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDb;

public interface ProvinsiService {
	ProvinsiDb getProvinsiDb();
	ProvinsiModel getProvinsiDetailById(long id);
}
