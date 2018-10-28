package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDb;

public interface ProvinsiService {
	ProvinsiDb getProvinsiDb();
	ProvinsiModel getProvinsiDetailById(long id);
	List<PegawaiModel> getListPegawai(List<InstansiModel> listInstansi);
	List<PegawaiModel> getListPegawaiByJabatan(List<PegawaiModel> listPegawaiByProvinsi, JabatanModel jabatan);

}
