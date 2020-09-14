package com.mpc.pgateway.model;

public class UMPPIdentity {
	protected String kodeBank;
	protected String passwordBank;
	
	public UMPPIdentity() {
		super();
	}

	public UMPPIdentity(String kodeBank, String passwordBank) {
		super();
		this.kodeBank = kodeBank;
		this.passwordBank = passwordBank;
	}
	
	public String getKodeBank() {
		return kodeBank;
	}
	public void setKodeBank(String kodeBank) {
		this.kodeBank = kodeBank;
	}
	public String getPasswordBank() {
		return passwordBank;
	}
	public void setPasswordBank(String passwordBank) {
		this.passwordBank = passwordBank;
	}
	@Override
	public String toString() {
		return "UMPPIdentity [kodeBank=" + kodeBank + ", passwordBank=" + passwordBank + "]";
	}
}
