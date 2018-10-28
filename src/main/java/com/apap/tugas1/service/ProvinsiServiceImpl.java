package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
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
	
	@Override
	public List<PegawaiModel> getListPegawai(List<InstansiModel> listInstansi) {
		
		List<PegawaiModel> listPegawai = new ArrayList<>();
		
		for (int i = 0; i < listInstansi.size(); i++) {
			InstansiModel instansiDummy = listInstansi.get(i);
			
			for (int j = 0; j < instansiDummy.getListPegawai().size(); j++) {
				listPegawai.add(instansiDummy.getListPegawai().get(j));
			}
		}
		
		return listPegawai;
	}
	
	@Override
	public 	List<PegawaiModel> getListPegawaiByJabatan(List<PegawaiModel> listPegawaiByProvinsi, JabatanModel jabatan) {
		
		List<PegawaiModel> listPegawai = new ArrayList<>();

		for (int i = 0; i < listPegawaiByProvinsi.size(); i++) {
			if (listPegawaiByProvinsi.get(i).getJabatanPegawai().size() > 1) {
				for (int j = 0; j < listPegawaiByProvinsi.get(i).getJabatanPegawai().size(); j++) {
					if (listPegawaiByProvinsi.get(i).getJabatanPegawai().get(j).getNama().equalsIgnoreCase(jabatan.getNama())) {
						listPegawai.add(listPegawaiByProvinsi.get(i));
					}
				}
				
			} else {
				if (listPegawaiByProvinsi.get(i).getJabatanPegawai().get(0).getNama().equalsIgnoreCase(jabatan.getNama())) {
					listPegawai.add(listPegawaiByProvinsi.get(i));
				}
			}
			
		}
		
		return listPegawai;

	}
}
