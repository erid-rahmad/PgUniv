package com.mpc.pgateway.model;

import h2humplg.InquiryResponse;

import java.util.Date;

public class TempInquiry {
	private InquiryResponse inquiryResponse;
	private Date inquiryDate;
	
	
	public TempInquiry(InquiryResponse inquiryResponse, Date inquiryDate) {
		this.inquiryResponse = inquiryResponse;
		this.inquiryDate = inquiryDate;
	}


	/**
	 * @return the inquiryResponse
	 */
	public InquiryResponse getInquiryResponse() {
		return inquiryResponse;
	}


	/**
	 * @param inquiryResponse the inquiryResponse to set
	 */
	public void setInquiryResponse(InquiryResponse inquiryResponse) {
		this.inquiryResponse = inquiryResponse;
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
		return "TempInquiry [inquiryResponse=" + inquiryResponse
				+ ", inquiryDate=" + inquiryDate + "]";
	}
	
}
