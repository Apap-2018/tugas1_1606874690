package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanDb;

public interface JabatanService {
	JabatanDb getJabatanDb();
	void addJabatan(JabatanModel jabatan);
	void deleteJabatan(JabatanModel jabatan);
	void updateJabatan(JabatanModel dealer);
	boolean cekIsiPegawai(JabatanModel jabatan, List<PegawaiModel> listPegawai);
	JabatanModel getJabatanDetailByNama(String nama);
	JabatanModel getJabatanDetailById(long id);
}
