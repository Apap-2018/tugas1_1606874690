package com.apap.tugas1.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.ProvinsiDb;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;


@Controller
public class PegawaiController {

	private String nipSementara;

	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value= "/pegawai", method = RequestMethod.GET)
	private String viewPegawai(@RequestParam ("nip") String nip,  Model model) {
		
		double gaji = 0.0;
		
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		InstansiModel instansi = pegawai.getInstansi();
		
		List<JabatanModel> listJabatan = pegawai.getJabatanPegawai();
		
		gaji = pegawai.hitungGaji();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("instansi", instansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("gaji", gaji);
		model.addAttribute("title", "Data Pegawai");
		
		return "view-pegawai";
	}
	

	@RequestMapping(value= "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String viewTermudaTertua(@RequestParam ("idInstansi") String idInstansi, Model model) {
		
		InstansiModel instansi = instansiService.getInstansiDetailById(Long.parseLong(idInstansi));
		
		List<PegawaiModel> listPegawai = instansi.getListPegawai();
		Collections.sort(listPegawai);
		
		PegawaiModel termuda = listPegawai.get(listPegawai.size()-1);
		PegawaiModel tertua = listPegawai.get(0);
		
		List<JabatanModel> listJabatanTermuda = termuda.getJabatanPegawai();
		List<JabatanModel> listJabatanTertua = tertua.getJabatanPegawai();
		
		model.addAttribute("termuda", termuda);
		model.addAttribute("tertua", tertua);
		model.addAttribute("instansi", instansi);
		model.addAttribute("listJabatanTermuda", listJabatanTermuda);
		model.addAttribute("listJabatanTertua", listJabatanTertua);
		model.addAttribute("title", "Lihat Pegawai Termuda dan Tertua");

		return "view-tertuatermuda";
	}
	
	@RequestMapping(value= "/pegawai/cari", method = RequestMethod.GET)
	private String search(@RequestParam (value = "idProvinsi", required = false) String idProvinsi, 
			@RequestParam (value = "idInstansi", required = false) String idInstansi,
			@RequestParam (value = "idJabatan", required = false) String idJabatan, Model model) {
		
		if (idProvinsi != null && idInstansi != null && idJabatan != null) {
			InstansiModel instansi = instansiService.getInstansiDetailById(Long.parseLong(idInstansi));
			JabatanModel jabatan = jabatanService.getJabatanDetailById(Long.parseLong(idJabatan));
			List<PegawaiModel> listPegawai = pegawaiService.findByInstansiAndJabatanPegawai(instansi, jabatan);
			
			model.addAttribute("listPegawai", listPegawai);

		} 
				
		ProvinsiDb provinsiDb = provinsiService.getProvinsiDb();
		List<ProvinsiModel> listProvinsi = provinsiDb.findAll();
		model.addAttribute("listProvinsi", listProvinsi);

		InstansiDb instansiDb = instansiService.getInstansiDb();
		List<InstansiModel> listInstansi = instansiDb.findAll();
		model.addAttribute("listInstansi", listInstansi);

		JabatanDb jabatanDb = jabatanService.getJabatanDb();
		List<JabatanModel> listJabatan = jabatanDb.findAll();
		model.addAttribute("listJabatan", listJabatan);

		model.addAttribute("title", "Tambah Pegawai");

		return "search-pegawai";	
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
	 
	@RequestMapping(value= "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		
		PegawaiModel pegawai = new PegawaiModel();
	
		List<JabatanModel> jabatanPegawai = new ArrayList<JabatanModel>();
		pegawai.setJabatanPegawai(jabatanPegawai);
		pegawai.getJabatanPegawai().add(new JabatanModel());
		
		InstansiModel instansi = new InstansiModel();
		pegawai.setInstansi(instansi);
		
		ProvinsiDb provinsiDb = provinsiService.getProvinsiDb();
		List<ProvinsiModel> listProvinsi = provinsiDb.findAll();
		model.addAttribute("listProvinsi", listProvinsi);

		InstansiDb instansiDb = instansiService.getInstansiDb();
		List<InstansiModel> listInstansi = instansiDb.findAll();
		model.addAttribute("listInstansi", listInstansi);

		JabatanDb jabatanDb = jabatanService.getJabatanDb();
		List<JabatanModel> listJabatan = jabatanDb.findAll();
		model.addAttribute("listJabatan", listJabatan);

		model.addAttribute("pegawai", pegawai);
		model.addAttribute("instansi", instansi);
		model.addAttribute("title", "Tambah Pegawai");

		return "add-pegawai";	
	}

	@RequestMapping(value= "/pegawai/tambah", method = RequestMethod.POST,  params= {"simpan"})
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		
		SimpleDateFormat date = new SimpleDateFormat("dd-MM-yy");
		String tanggalLahirString = date.format(pegawai.getTanggalLahir());
		tanggalLahirString = tanggalLahirString.replace("-", "");

		String nip = pegawaiService.generateNip(pegawai.getInstansi().getId(), tanggalLahirString, pegawai.getTahunMasuk(), pegawai.getTanggalLahir(), pegawai.getInstansi());
		pegawai.setNip(nip);
	
		pegawaiService.addPegawai(pegawai);
		
		model.addAttribute("title", "Tambah Pegawai");
		model.addAttribute("nip", pegawai.getNip());
		
		return "add-pegawai-success";
	}
	
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.POST, params= {"tambahbaris"})
	public String addRowTambah(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		
		pegawai.getJabatanPegawai().add(new JabatanModel());
		
		ProvinsiDb provinsiDb = provinsiService.getProvinsiDb();
		List<ProvinsiModel> listProvinsi = provinsiDb.findAll();
		
		InstansiDb instansiDb = instansiService.getInstansiDb();
		List<InstansiModel> listInstansi = instansiDb.findAll();
		
		JabatanDb jabatanDb = jabatanService.getJabatanDb();
		List<JabatanModel> listJabatan = jabatanDb.findAll();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("title", "Tambah Pegawai");

		return "add-pegawai";	
	}
	
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.POST, params={"hapusbaris"})
	public String removeRowTambah(@ModelAttribute PegawaiModel pegawai, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
	  
		final Integer rowId = Integer.valueOf(req.getParameter("hapusbaris"));
		pegawai.getJabatanPegawai().remove(rowId.intValue());
	    
		ProvinsiDb provinsiDb = provinsiService.getProvinsiDb();
		List<ProvinsiModel> listProvinsi = provinsiDb.findAll();
		
		InstansiDb instansiDb = instansiService.getInstansiDb();
		List<InstansiModel> listInstansi = instansiDb.findAll();
		
		JabatanDb jabatanDb = jabatanService.getJabatanDb();
		List<JabatanModel> listJabatan = jabatanDb.findAll();
		
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("title", "Tambah Pegawai");

	    return "add-pegawai";
	}
	
	@RequestMapping(value= "/pegawai/ubah", method = RequestMethod.GET)
	private String update(@RequestParam (value = "nip") String nip, Model model) {

		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		this.nipSementara = nip;
				
		ProvinsiDb provinsiDb = provinsiService.getProvinsiDb();
		List<ProvinsiModel> listProvinsi = provinsiDb.findAll();
		model.addAttribute("listProvinsi", listProvinsi);

		InstansiDb instansiDb = instansiService.getInstansiDb();
		List<InstansiModel> listInstansi = instansiDb.findAll();
		model.addAttribute("listInstansi", listInstansi);

		JabatanDb jabatanDb = jabatanService.getJabatanDb();
		List<JabatanModel> listJabatan = jabatanDb.findAll();
		model.addAttribute("listJabatan", listJabatan);

		model.addAttribute("pegawai", pegawai);
		model.addAttribute("title", "Ubah Data Pegawai");

		return "update-pegawai";				
	}
	
	@RequestMapping(value="/pegawai/ubah", method = RequestMethod.POST, params= {"tambahbaris"})
	public String addRowUbah(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		
		pegawai.getJabatanPegawai().add(new JabatanModel());
		
		ProvinsiDb provinsiDb = provinsiService.getProvinsiDb();
		List<ProvinsiModel> listProvinsi = provinsiDb.findAll();
		
		InstansiDb instansiDb = instansiService.getInstansiDb();
		List<InstansiModel> listInstansi = instansiDb.findAll();
		
		JabatanDb jabatanDb = jabatanService.getJabatanDb();
		List<JabatanModel> listJabatan = jabatanDb.findAll();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("title", "Ubah Data Pegawai");

		return "update-pegawai";	
	}
	
	@RequestMapping(value="/pegawai/ubah", method = RequestMethod.POST, params={"hapusbaris"})
	public String removeRowUbah(@ModelAttribute PegawaiModel pegawai, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
	  
		final Integer rowId = Integer.valueOf(req.getParameter("hapusbaris"));
		pegawai.getJabatanPegawai().remove(rowId.intValue());
	    
		ProvinsiDb provinsiDb = provinsiService.getProvinsiDb();
		List<ProvinsiModel> listProvinsi = provinsiDb.findAll();
		
		InstansiDb instansiDb = instansiService.getInstansiDb();
		List<InstansiModel> listInstansi = instansiDb.findAll();
		
		JabatanDb jabatanDb = jabatanService.getJabatanDb();
		List<JabatanModel> listJabatan = jabatanDb.findAll();
		
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("title", "Ubah Data Pegawai");

	    return "update-pegawai";
	}
	
	@RequestMapping(value= "/pegawai/ubah", method = RequestMethod.POST, params= {"simpan"})
	private String updateJabatanSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {

		pegawai.setNip(nipSementara);
		pegawaiService.updatePegawai(pegawai, nipSementara);
		model.addAttribute("title", "Ubah Data Pegawai");
		model.addAttribute("nip", nipSementara);

		return "update-pegawai-success";
	}
}
