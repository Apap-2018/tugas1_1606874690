package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	
	@Autowired
	JabatanDb jabatanDb;
	
	@Override
	public JabatanDb getJabatanDb() {
		return jabatanDb;
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}	
	
	@Override
	public 	void deleteJabatan(JabatanModel jabatan) {
		jabatanDb.delete(jabatan);
	}
	
	@Override
	public boolean cekIsiPegawai(JabatanModel jabatan, List<PegawaiModel> listPegawai) {
		boolean isiPegawai = false;
		
		for (int i = 0; i < listPegawai.size(); i++) {
			if (listPegawai.get(i).getJabatanPegawai().size() > 1) {
				for (int j = 0; j < listPegawai.get(i).getJabatanPegawai().size(); j++) {
					if (listPegawai.get(i).getJabatanPegawai().get(j).getNama().equalsIgnoreCase(jabatan.getNama())) {
						isiPegawai = true;
						return isiPegawai;
					}
				}
				
			} else {
				if (listPegawai.get(i).getJabatanPegawai().get(0).getNama().equalsIgnoreCase(jabatan.getNama())) {
					isiPegawai = true;
					return isiPegawai;
				}
			}
			
		}
		
		return isiPegawai;
	}
	
	@Override
	public int hitungPegawai(JabatanModel jabatan, List<PegawaiModel> listPegawai) {
		int jumlahPegawai = 0;
		
		for (int i = 0; i < listPegawai.size(); i++) {
			if (listPegawai.get(i).getJabatanPegawai().size() > 1) {
				for (int j = 0; j < listPegawai.get(i).getJabatanPegawai().size(); j++) {
					if (listPegawai.get(i).getJabatanPegawai().get(j).getNama().equalsIgnoreCase(jabatan.getNama())) {
						jumlahPegawai += 1;
					}
				}
				
			} else {
				if (listPegawai.get(i).getJabatanPegawai().get(0).getNama().equalsIgnoreCase(jabatan.getNama())) {
					jumlahPegawai += 1;
				}
			}
			
		}
		
		return jumlahPegawai;
	}
	
	@Override
	public void updateJabatan(JabatanModel jabatan) {
		for (int i = 0; i < jabatanDb.findAll().size(); i++) {
			if (jabatanDb.findAll().get(i).getId() == (jabatan.getId())) {
				
				JabatanModel archive = jabatanDb.findAll().get(i);
				int idx = jabatanDb.findAll().indexOf(archive);
				
				jabatanDb.findAll().get(idx).setNama(jabatan.getNama());
				jabatanDb.findAll().get(idx).setDeskripsi(jabatan.getDeskripsi());
				jabatanDb.findAll().get(idx).setGajiPokok(jabatan.getGajiPokok());				
			}
		}
	}
	
	@Override
	public JabatanModel getJabatanDetailByNama(String nama) {
		return jabatanDb.findByNama(nama);
	}
	
	@Override
	public JabatanModel getJabatanDetailById(long id) {
		return jabatanDb.findById(id);
	}
}
