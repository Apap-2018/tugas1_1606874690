package com.apap.tugas1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pegawai")
public class PegawaiModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Size(max = 255)
	@Column(name = "nip", nullable = false, unique= true)
	private String nip;

	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;

	@NotNull
	@Size(max = 255)
	@Column(name = "tempat_lahir", nullable = false)
	private String tempatLahir;
	
	@NotNull
	@Column(name = "tanggal_lahir", nullable = false)
	private Date tanggalLahir;

	@NotNull
	@Size(max = 255)
	@Column(name = "tahun_masuk", nullable = false)
	private String tahunMasuk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_instansi", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private InstansiModel instansi;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "jabatan_pegawai", joinColumns = { @JoinColumn(name = "id_pegawai") }, inverseJoinColumns = { @JoinColumn(name = "id_jabatan") })
	private List<JabatanModel> jabatanPegawai = new ArrayList<JabatanModel>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTempatLahir() {
		return tempatLahir;
	}

	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}

	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	public String getTahunMasuk() {
		return tahunMasuk;
	}

	public void setTahunMasuk(String tahunMasuk) {
		this.tahunMasuk = tahunMasuk;
	}

	public InstansiModel getInstansi() {
		return instansi;
	}

	public void setInstansi(InstansiModel instansi) {
		this.instansi = instansi;
	}

	public List<JabatanModel> getJabatanPegawai() {
		return jabatanPegawai;
	}

	public void setJabatanPegawai(List<JabatanModel> jabatanPegawai) {
		this.jabatanPegawai = jabatanPegawai;
	}

	public Double hitungGaji() {
		double gaji = 0.0;
		double gajiPokok = 0.0;
		
		List<JabatanModel> jabatan = this.jabatanPegawai;
		InstansiModel instansi = this.instansi;
		
		Collections.sort(jabatan, new sortGajiPokok());
		gajiPokok = jabatan.get(jabatan.size()-1).getGajiPokok();
		
		//gaji = gaji pokok + (%tunjangan provinsi x gaji pokok)
		//kalo dua jabatan, gaji pokok = gaji pokok yg lebih besar
		gaji = gajiPokok + ((instansi.getProvinsi().getPresentaseTunjangan() / 100) * gajiPokok);
		
		return gaji;
	}

}

class sortGajiPokok implements Comparator<JabatanModel> { 

	public int compare(JabatanModel a, JabatanModel b) { 
		return (int) (a.getGajiPokok() - b.getGajiPokok()); 
	} 
} 

