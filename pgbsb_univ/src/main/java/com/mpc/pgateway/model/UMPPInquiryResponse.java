package com.mpc.pgateway.model;

public class UMPPInquiryResponse extends UMPPBaseResponse{
	private String nomorPembayaran;
	private String origin;
	private String idTagihan;
	private String nama;
	private String fakultas;
	private String jurusan;
	private String angkatan;
	private String totalNominal;
	private String deskripsi;

	public String getNomorPembayaran() {
		return nomorPembayaran;
	}

	public void setNomorPembayaran(String nomorPembayaran) {
		this.nomorPembayaran = nomorPembayaran;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getIdTagihan() {
		return idTagihan;
	}

	public void setIdTagihan(String idTagihan) {
		this.idTagihan = idTagihan;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getFakultas() {
		return fakultas;
	}

	public void setFakultas(String fakultas) {
		this.fakultas = fakultas;
	}

	public String getJurusan() {
		return jurusan;
	}

	public void setJurusan(String jurusan) {
		this.jurusan = jurusan;
	}

	public String getAngkatan() {
		return angkatan;
	}

	public void setAngkatan(String angkatan) {
		this.angkatan = angkatan;
	}

	public String getTotalNominal() {
		return totalNominal;
	}

	public void setTotalNominal(String totalNominal) {
		this.totalNominal = totalNominal;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	@Override
	public String toString() {
		return "UMPPInquiryResponse [nomorPembayaran=" + nomorPembayaran + ", origin=" + origin + ", idTagihan="
				+ idTagihan + ", nama=" + nama + ", fakultas=" + fakultas + ", jurusan=" + jurusan + ", angkatan="
				+ angkatan + ", code=" + code + ", message=" + message + ", totalNominal=" + totalNominal
				+ ", deskripsi=" + deskripsi + "]";
	}
}
