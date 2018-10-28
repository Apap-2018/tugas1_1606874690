package com.apap.tugas1.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
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
	public List<PegawaiModel> findByJabatanPegawai(List<JabatanModel> jabatanPegawai) {
		return pegawaiDb.findByJabatanPegawai(jabatanPegawai);
	}
	
	@Override
	public List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk) {
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
	}
	
	@Override
	public List<PegawaiModel> findByInstansiAndJabatanPegawai(InstansiModel instansi, JabatanModel jabatanPegawai) {
		 
		List<PegawaiModel> listPegawaiByInstansiAndJabatanPegawai = new ArrayList<>();
		List<PegawaiModel> listPegawaiByInstansi = pegawaiDb.findByInstansi(instansi);
		
		for (int i = 0; i < listPegawaiByInstansi.size(); i++) {
			if (listPegawaiByInstansi.get(i).getJabatanPegawai().contains(jabatanPegawai)) {
				listPegawaiByInstansiAndJabatanPegawai.add(listPegawaiByInstansi.get(i));
			}
		}
		
		return listPegawaiByInstansiAndJabatanPegawai;
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
	public void updatePegawai(PegawaiModel pegawai, String nipLama) {
		
		PegawaiModel pegawaiLama = pegawaiDb.findByNip(nipLama);
		int idx = pegawaiDb.findAll().indexOf(pegawaiLama);
		
		String nipBaru = "";
		
		SimpleDateFormat date = new SimpleDateFormat("dd-MM-yy");
		String tanggalLahirString = date.format(pegawai.getTanggalLahir());
		tanggalLahirString = tanggalLahirString.replace("-", "");
		String instansiIdString = Long.toString(pegawai.getInstansi().getId());
		String urutanPegawaiStringSatuan = "0";
		String urutanPegawaiStringPuluhan = "";
		int urutanPegawai = 0;
		
		
		List<PegawaiModel> listSameDateYearIn = pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk());
	
		if (listSameDateYearIn.isEmpty()) {
			urutanPegawai += 1;
		} else {
			urutanPegawai = listSameDateYearIn.size() + 1;
		}
		
		if (Integer.toString(urutanPegawai).length() == 1) {
			urutanPegawaiStringSatuan += Integer.toString(urutanPegawai);
			nipBaru = instansiIdString + tanggalLahirString + pegawai.getTahunMasuk() + urutanPegawaiStringSatuan;
		} else {
			urutanPegawaiStringPuluhan += Integer.toString(urutanPegawai);
			nipBaru = instansiIdString + tanggalLahirString + pegawai.getTahunMasuk() + urutanPegawaiStringPuluhan;
		}
		
		pegawaiDb.findAll().get(idx).setNama(pegawai.getNama());
		pegawaiDb.findAll().get(idx).setTempatLahir(pegawai.getTempatLahir());
		pegawaiDb.findAll().get(idx).setTanggalLahir(pegawai.getTanggalLahir());
		pegawaiDb.findAll().get(idx).setTahunMasuk(pegawai.getTahunMasuk());
		pegawaiDb.findAll().get(idx).setInstansi(pegawai.getInstansi());
		pegawaiDb.findAll().get(idx).setJabatanPegawai(pegawai.getJabatanPegawai());
		pegawaiDb.findAll().get(idx).setNip(nipBaru);
		
	}
}
