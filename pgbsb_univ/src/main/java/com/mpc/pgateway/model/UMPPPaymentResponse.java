package com.mpc.pgateway.model;

public class UMPPPaymentResponse extends UMPPBaseResponse{
	private String origin;
	private String idTagihan;
	private String nomorPembayaran;
	private String totalNominal;

	public UMPPPaymentResponse() {
		super();
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

	public String getNomorPembayaran() {
		return nomorPembayaran;
	}

	public void setNomorPembayaran(String nomorPembayaran) {
		this.nomorPembayaran = nomorPembayaran;
	}

	public String getTotalNominal() {
		return totalNominal;
	}

	public void setTotalNominal(String totalNominal) {
		this.totalNominal = totalNominal;
	}

	@Override
	public String toString() {
		return "UMPPPaymentResponse [origin=" + origin + ", idTagihan=" + idTagihan + ", nomorPembayaran="
				+ nomorPembayaran + ", totalNominal=" + totalNominal + ", code=" + code + ", message=" + message + "]";
	}
}
