package com.apap.tugas1.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Override
	public PegawaiDb getPegawaiDb() {
		return pegawaiDb;
	}
	
	@Override
	public Optional<PegawaiModel> getPegawaiDetailById(Long id) {
		return pegawaiDb.findById(id);
	}
	
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}
	
	@Override
	public List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk) {
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
	}

	@Override
	public String generateNip(long instansiId, String tanggalLahirString, String tahunMasuk, Date tanggalLahir, InstansiModel instansi) {
		String nip = "";
		
		String instansiIdString = Long.toString(instansiId);
		String urutanPegawaiStringSatuan = "0";
		String urutanPegawaiStringPuluhan = "";
		int urutanPegawai = 0;
		
		List<PegawaiModel> listSameDateYearIn = pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
	
		if (listSameDateYearIn.isEmpty()) {
			urutanPegawai += 1;
		} else {
			urutanPegawai = listSameDateYearIn.size() + 1;
		}
		
		if (Integer.toString(urutanPegawai).length() == 1) {
			urutanPegawaiStringSatuan += Integer.toString(urutanPegawai);
			nip = instansiIdString + tanggalLahirString + tahunMasuk + urutanPegawaiStringSatuan;
		} else {
			urutanPegawaiStringPuluhan += Integer.toString(urutanPegawai);
			nip = instansiIdString + tanggalLahirString + tahunMasuk + urutanPegawaiStringPuluhan;
		}
			 
		return nip;
	}

	
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
	}
	
	@Override
	public void deletePegawai(PegawaiModel pegawai) {
		
	}
	
	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		
	}
}
