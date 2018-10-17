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
	public JabatanModel getJabatanDetailByNama(String nama) {
		return jabatanDb.findByNama(nama);
	}
	
	@Override
	public JabatanModel getJabatanDetailById(long id) {
		return jabatanDb.findById(id);
	}
}
