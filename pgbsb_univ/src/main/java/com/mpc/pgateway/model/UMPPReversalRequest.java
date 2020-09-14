package com.mpc.pgateway.model;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.core.util.MultivaluedMapImpl;

public class UMPPReversalRequest extends UMPPIdentity{
	private String idTagihan;
	private String waktuReversal;
	
	public UMPPReversalRequest() {
		super();
	}
	
	public UMPPReversalRequest(String idTagihan, String waktuReversal) {
		super();
		this.idTagihan = idTagihan;
		this.waktuReversal = waktuReversal;
	}
	public String getIdTagihan() {
		return idTagihan;
	}
	public void setIdTagihan(String idTagihan) {
		this.idTagihan = idTagihan;
	}
	public String getWaktuReversal() {
		return waktuReversal;
	}
	public void setWaktuReversal(String waktuReversal) {
		this.waktuReversal = waktuReversal;
	}
	@Override
	public String toString() {
		return "UMPPReversalRequest [idTagihan=" + idTagihan + ", waktuReversal=" + waktuReversal + "]";
	}

	public MultivaluedMap<String, String> convertToMap() {
		MultivaluedMap<String, String> map = new MultivaluedMapImpl();
		map.add("kodeBank", this.getKodeBank());
		map.add("passwordBank", this.getPasswordBank());
		map.add("waktuReversal", this.getWaktuReversal());
		map.add("idTransaksi", this.getIdTagihan());
		return map;
	}
}
