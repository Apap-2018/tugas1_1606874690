package com.apap.tugas1.service;



import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;


public interface PegawaiService {
	Optional<PegawaiModel> getPegawaiDetailById(Long id);
	PegawaiModel getPegawaiDetailByNip(String nip);
	List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
	
	String generateNip(long instansiId, String tanggalLahirString, String tahunMasuk, Date tanggalLahir, InstansiModel instansi);
	void addPegawai(PegawaiModel pegawai);
	void deletePegawai(PegawaiModel pegawai);
	void updatePegawai(PegawaiModel pegawai);
	
	PegawaiDb getPegawaiDb();
}
