package com.mpc.pgateway.model;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.core.util.MultivaluedMapImpl;

public class UMPPInquiryRequest extends UMPPBaseRequest{
	private String kodeTerminal;
	private String idTransaksi;
	private String tanggalTransaksi;
	
	public UMPPInquiryRequest() {
		super();
	}

	public String getKodeTerminal() {
		return kodeTerminal;
	}

	public void setKodeTerminal(String kodeTerminal) {
		this.kodeTerminal = kodeTerminal;
	}

	public String getIdTransaksi() {
		return idTransaksi;
	}

	public void setIdTransaksi(String idTransaksi) {
		this.idTransaksi = idTransaksi;
	}

	public String getTanggalTransaksi() {
		return tanggalTransaksi;
	}

	public void setTanggalTransaksi(String tanggalTransaksi) {
		this.tanggalTransaksi = tanggalTransaksi;
	}
	
	public MultivaluedMap<String,String> convertToMap() {
		MultivaluedMap<String, String> map = new MultivaluedMapImpl();
		map.add("kodeBank", this.getKodeBank());
		map.add("passwordBank", this.getPasswordBank());
		map.add("kodeChannel", this.getKodeChannel());
		map.add("kodeTerminal", this.getKodeTerminal());
		map.add("nomorPembayaran", this.getNomorPembayaran());
		map.add("tanggalTransaksi", this.getTanggalTransaksi());
		map.add("idTransaksi", this.getIdTransaksi());
		return map;
	}

	@Override
	public String toString() {
		return "UMPPInquiryRequest [kodeTerminal=" + kodeTerminal + ", idTransaksi=" + idTransaksi
				+ ", tanggalTransaksi=" + tanggalTransaksi + ", kodeChannel=" + kodeChannel + ", nomorPembayaran="
				+ nomorPembayaran + ", kodeBank=" + kodeBank + ", passwordBank=" + passwordBank + "]";
	}
}
