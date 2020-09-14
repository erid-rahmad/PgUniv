package com.mpc.pgateway.model;

import id.ac.univpgri_palembang.ws.InquiryRespon;

import java.util.Date;

public class TempInquiryPGRI {
	private InquiryRespon inquiryRespon;
	private Date inquiryDate;
	
	
	public TempInquiryPGRI(InquiryRespon inquiryResponse, Date inquiryDate) {
		this.inquiryRespon = inquiryResponse;
		this.inquiryDate = inquiryDate;
	}


	public InquiryRespon getInquiryRespon() {
		return inquiryRespon;
	}


	public void setInquiryRespon(InquiryRespon inquiryRespon) {
		this.inquiryRespon = inquiryRespon;
	}


	/**
	 * @return the inquiryDate
	 */
	public Date getInquiryDate() {
		return inquiryDate;
	}


	/**
	 * @param inquiryDate the inquiryDate to set
	 */
	public void setInquiryDate(Date inquiryDate) {
		this.inquiryDate = inquiryDate;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TempInquiry [inquiryResponse=" + inquiryRespon
				+ ", inquiryDate=" + inquiryDate + "]";
	}
	
}
