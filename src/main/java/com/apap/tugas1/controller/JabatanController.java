package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.PegawaiDb;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class JabatanController {

	@Autowired
	JabatanService jabatanService;
	
	@Autowired
	PegawaiService pegawaiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		
		JabatanDb jabatanDb = jabatanService.getJabatanDb();
		List<JabatanModel> listJabatan = jabatanDb.findAll();

		model.addAttribute("title", "Home");
		model.addAttribute("listJabatan", listJabatan);
		
		return "home";
	}
	
	@RequestMapping(value= "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		
		model.addAttribute("jabatan", new JabatanModel());
		model.addAttribute("title", "Tambah Jabatan");
		
		return "add-jabatan";	
	}

	@RequestMapping(value= "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		
		jabatanService.addJabatan(jabatan);
		model.addAttribute("title", "Tambah Jabatan");
		
		return "add-success";
	}
	
	@RequestMapping(value= "/jabatan/hapus/{idJabatan}", method = RequestMethod.POST)
	private String deleteJabatan(@PathVariable(value = "idJabatan") String idJabatan, Model model) {
		
		PegawaiDb pegawaiDb = pegawaiService.getPegawaiDb();
		List<PegawaiModel> listPegawai = pegawaiDb.findAll();
		
		JabatanModel jabatan = jabatanService.getJabatanDetailById(Long.parseLong(idJabatan));
		
		if (jabatanService.cekIsiPegawai(jabatan, listPegawai)) {
			return "deletefail-jabatan";
		}
		
		jabatan.setId(Long.parseLong(idJabatan));
		jabatanService.deleteJabatan(jabatan);
		
		model.addAttribute("title", "Hapus Jabatan");
		return "delete-jabatan";
				
	}

	@RequestMapping(value= "/jabatan/view", method = RequestMethod.GET)
	private String viewJabatan(@RequestParam ("idJabatan") String idJabatan,  Model model) {
		
		JabatanModel jabatan = jabatanService.getJabatanDetailById(Long.parseLong(idJabatan));
		
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("title", "Detail Jabatan");

		return "view-jabatan";
	}
	
	@RequestMapping("/jabatan/viewall")
	public String viewAll (Model model) {
		
		JabatanDb jabatanDb = jabatanService.getJabatanDb();
		List<JabatanModel> listJabatan = jabatanDb.findAll();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Lihat Semua Jabatan");
		return "viewall-jabatan";	
	}
	
}
