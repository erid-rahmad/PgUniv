package com.mpc.pgateway.model;

public class UMPPBaseRequest extends UMPPIdentity{
	protected String kodeChannel;
	protected String nomorPembayaran;
	
	public UMPPBaseRequest() {
		super();
	}

	public String getKodeChannel() {
		return kodeChannel;
	}

	public void setKodeChannel(String kodeChannel) {
		this.kodeChannel = kodeChannel;
	}

	public String getNomorPembayaran() {
		return nomorPembayaran;
	}

	public void setNomorPembayaran(String nomorPembayaran) {
		this.nomorPembayaran = nomorPembayaran;
	}

	@Override
	public String toString() {
		return "UMPPBaseRequest [kodeChannel=" + kodeChannel + ", nomorPembayaran=" + nomorPembayaran + "]";
	}
}
