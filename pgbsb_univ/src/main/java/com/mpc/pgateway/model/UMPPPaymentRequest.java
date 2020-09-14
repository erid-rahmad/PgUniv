package com.mpc.pgateway.model;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.core.util.MultivaluedMapImpl;

public class UMPPPaymentRequest extends UMPPBaseRequest {
	private String idTagihan;
	private String waktuTransaksiBank;
	private String totalNominal;
	private String kodeUnikBank;
	private String nomorJunalBank;
	
	public UMPPPaymentRequest() {
		super();
	}

	public String getIdTagihan() {
		return idTagihan;
	}

	public void setIdTagihan(String idTagihan) {
		this.idTagihan = idTagihan;
	}

	public String getWaktuTransaksiBank() {
		return waktuTransaksiBank;
	}

	public void setWaktuTransaksiBank(String waktuTransaksiBank) {
		this.waktuTransaksiBank = waktuTransaksiBank;
	}

	public String getTotalNominal() {
		return totalNominal;
	}

	public void setTotalNominal(String totalNominal) {
		this.totalNominal = totalNominal;
	}

	public String getKodeUnikBank() {
		return kodeUnikBank;
	}

	public void setKodeUnikBank(String kodeUnikBank) {
		this.kodeUnikBank = kodeUnikBank;
	}

	public String getNomorJunalBank() {
		return nomorJunalBank;
	}

	public void setNomorJunalBank(String nomorJunalBank) {
		this.nomorJunalBank = nomorJunalBank;
	}

	@Override
	public String toString() {
		return "UMPPPaymentRequest [idTagihan=" + idTagihan + ", waktuTransaksiBank=" + waktuTransaksiBank
				+ ", totalNominal=" + totalNominal + ", kodeUnikBank=" + kodeUnikBank + ", nomorJunalBank="
				+ nomorJunalBank + ", kodeChannel=" + kodeChannel + ", nomorPembayaran=" + nomorPembayaran
				+ ", kodeBank=" + kodeBank + ", passwordBank=" + passwordBank + "]";
	}

	public MultivaluedMap<String, String> convertToMap() {
		MultivaluedMap<String, String> map = new MultivaluedMapImpl();
		map.add("kodeBank", this.getKodeBank());
		map.add("passwordBank", this.getPasswordBank());
		map.add("idTagihan", this.getIdTagihan());
		map.add("kodeChannel", this.getKodeChannel());
		map.add("nomorPembayaran", this.getNomorPembayaran());
		map.add("waktuTransaksiBank", this.getWaktuTransaksiBank());
		map.add("totalNominal", this.getTotalNominal());
		map.add("kodeUnikBank", this.getKodeUnikBank());
		map.add("nomorJurnalBank", this.getNomorJunalBank());
		return map;
	}
}
