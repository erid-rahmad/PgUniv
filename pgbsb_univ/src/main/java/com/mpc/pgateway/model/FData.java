package com.mpc.pgateway.model;

import java.io.Serializable;

public class FData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 546144401534980698L;
	private String areacode;		/* PDAM Area Code	*/  
	private String id;				/* PDAM Customer ID	*/
	private String name;			/* Customer Name	*/
	private String address;			/* Customer Address	*/	
	private String powerrate;		/* Power Rate		*/
	private String poweruse2;		/* Power Consuming	*/
	private String boxid;			/* Central Box Id	*/
	private String category;		/* Customer Category	*/
	private String hfactor;			/* Hour Factor		*/
	private String mfactor;			/* Meter Factor		*/
	private String totalbill;		/* Total Bill 		*/
	private String billstatus;		/* Bill Status		*/
	
	private String poweruse;		/* Power Consuming	*/
	private String filler;			/* Filler		*/
	private String month;			/* Periode 1		*/
	private String smeter;			/* Stand Meter 1	*/
	private String usage;			/* Usage Amount 1	*/
	private String ppj;				/* PPJ Amount 1		*/
	private String ppn;				/* PPN Amount 1		*/
	private String rent;			/* Rent Amount 1	*/
	private String fine;			/* Fine Amount 1	*/
	private String stamp;			/* Stamp Fee 1		*/
	private String bill;			/* Bill Amount 1	*/
	private String month2;			/* Periode 2		*/
	private String smeter2;			/* Stand Meter 2	*/
	private String usage2;			/* Usage Amount 2	*/
	private String ppj2;			/* PPJ Amount 2		*/
	private String ppn2;			/* PPN Amount 2		*/
	private String rent2;			/* Rent Amount 2	*/
	private String fine2;			/* Fine Amount 2	*/
	private String stamp2;			/* Stamp Fee 2		*/
	private String bill2;			/* Bill Amount 2	*/
	private String month3;			/* Periode 3		*/
	private String smeter3;			/* Stand Meter 3	*/
	private String usage3;			/* Usage Amount 3	*/
	private String ppj3;			/* PPJ Amount 3		*/
	private String ppn3;			/* PPN Amount 3		*/
	private String rent3;			/* Rent Amount 3	*/
	private String fine3;			/* Fine Amount 3	*/
	private String stamp3;			/* Stamp Fee 3		*/
	private String bill3;			/* Bill Amount 3	*/

	
	
	/**
	 * @return the areacode
	 */
	public String getAreacode() {
		return areacode;
	}


	/**
	 * @param areacode the areacode to set
	 */
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}


	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}


	/**
	 * @return the powerrate
	 */
	public String getPowerrate() {
		return powerrate;
	}


	/**
	 * @param powerrate the powerrate to set
	 */
	public void setPowerrate(String powerrate) {
		this.powerrate = powerrate;
	}


	/**
	 * @return the poweruse2
	 */
	public String getPoweruse2() {
		return poweruse2;
	}


	/**
	 * @param poweruse2 the poweruse2 to set
	 */
	public void setPoweruse2(String poweruse2) {
		this.poweruse2 = poweruse2;
	}


	/**
	 * @return the boxid
	 */
	public String getBoxid() {
		return boxid;
	}


	/**
	 * @param boxid the boxid to set
	 */
	public void setBoxid(String boxid) {
		this.boxid = boxid;
	}


	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}


	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}


	/**
	 * @return the hfactor
	 */
	public String getHfactor() {
		return hfactor;
	}


	/**
	 * @param hfactor the hfactor to set
	 */
	public void setHfactor(String hfactor) {
		this.hfactor = hfactor;
	}


	/**
	 * @return the mfactor
	 */
	public String getMfactor() {
		return mfactor;
	}


	/**
	 * @param mfactor the mfactor to set
	 */
	public void setMfactor(String mfactor) {
		this.mfactor = mfactor;
	}


	/**
	 * @return the totalbill
	 */
	public String getTotalbill() {
		return totalbill;
	}


	/**
	 * @param totalbill the totalbill to set
	 */
	public void setTotalbill(String totalbill) {
		this.totalbill = totalbill;
	}


	/**
	 * @return the billstatus
	 */
	public String getBillstatus() {
		return billstatus;
	}


	/**
	 * @param billstatus the billstatus to set
	 */
	public void setBillstatus(String billstatus) {
		this.billstatus = billstatus;
	}


	/**
	 * @return the poweruse
	 */
	public String getPoweruse() {
		return poweruse;
	}


	/**
	 * @param poweruse the poweruse to set
	 */
	public void setPoweruse(String poweruse) {
		this.poweruse = poweruse;
	}


	/**
	 * @return the filler
	 */
	public String getFiller() {
		return filler;
	}


	/**
	 * @param filler the filler to set
	 */
	public void setFiller(String filler) {
		this.filler = filler;
	}


	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}


	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}


	/**
	 * @return the smeter
	 */
	public String getSmeter() {
		return smeter;
	}


	/**
	 * @param smeter the smeter to set
	 */
	public void setSmeter(String smeter) {
		this.smeter = smeter;
	}


	/**
	 * @return the usage
	 */
	public String getUsage() {
		return usage;
	}


	/**
	 * @param usage the usage to set
	 */
	public void setUsage(String usage) {
		this.usage = usage;
	}


	/**
	 * @return the ppj
	 */
	public String getPpj() {
		return ppj;
	}


	/**
	 * @param ppj the ppj to set
	 */
	public void setPpj(String ppj) {
		this.ppj = ppj;
	}


	/**
	 * @return the ppn
	 */
	public String getPpn() {
		return ppn;
	}


	/**
	 * @param ppn the ppn to set
	 */
	public void setPpn(String ppn) {
		this.ppn = ppn;
	}


	/**
	 * @return the rent
	 */
	public String getRent() {
		return rent;
	}


	/**
	 * @param rent the rent to set
	 */
	public void setRent(String rent) {
		this.rent = rent;
	}


	/**
	 * @return the fine
	 */
	public String getFine() {
		return fine;
	}


	/**
	 * @param fine the fine to set
	 */
	public void setFine(String fine) {
		this.fine = fine;
	}


	/**
	 * @return the stamp
	 */
	public String getStamp() {
		return stamp;
	}


	/**
	 * @param stamp the stamp to set
	 */
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}


	/**
	 * @return the bill
	 */
	public String getBill() {
		return bill;
	}


	/**
	 * @param bill the bill to set
	 */
	public void setBill(String bill) {
		this.bill = bill;
	}


	/**
	 * @return the month2
	 */
	public String getMonth2() {
		return month2;
	}


	/**
	 * @param month2 the month2 to set
	 */
	public void setMonth2(String month2) {
		this.month2 = month2;
	}


	/**
	 * @return the smeter2
	 */
	public String getSmeter2() {
		return smeter2;
	}


	/**
	 * @param smeter2 the smeter2 to set
	 */
	public void setSmeter2(String smeter2) {
		this.smeter2 = smeter2;
	}


	/**
	 * @return the usage2
	 */
	public String getUsage2() {
		return usage2;
	}


	/**
	 * @param usage2 the usage2 to set
	 */
	public void setUsage2(String usage2) {
		this.usage2 = usage2;
	}


	/**
	 * @return the ppj2
	 */
	public String getPpj2() {
		return ppj2;
	}


	/**
	 * @param ppj2 the ppj2 to set
	 */
	public void setPpj2(String ppj2) {
		this.ppj2 = ppj2;
	}


	/**
	 * @return the ppn2
	 */
	public String getPpn2() {
		return ppn2;
	}


	/**
	 * @param ppn2 the ppn2 to set
	 */
	public void setPpn2(String ppn2) {
		this.ppn2 = ppn2;
	}


	/**
	 * @return the rent2
	 */
	public String getRent2() {
		return rent2;
	}


	/**
	 * @param rent2 the rent2 to set
	 */
	public void setRent2(String rent2) {
		this.rent2 = rent2;
	}


	/**
	 * @return the fine2
	 */
	public String getFine2() {
		return fine2;
	}


	/**
	 * @param fine2 the fine2 to set
	 */
	public void setFine2(String fine2) {
		this.fine2 = fine2;
	}


	/**
	 * @return the stamp2
	 */
	public String getStamp2() {
		return stamp2;
	}


	/**
	 * @param stamp2 the stamp2 to set
	 */
	public void setStamp2(String stamp2) {
		this.stamp2 = stamp2;
	}


	/**
	 * @return the bill2
	 */
	public String getBill2() {
		return bill2;
	}


	/**
	 * @param bill2 the bill2 to set
	 */
	public void setBill2(String bill2) {
		this.bill2 = bill2;
	}


	/**
	 * @return the month3
	 */
	public String getMonth3() {
		return month3;
	}


	/**
	 * @param month3 the month3 to set
	 */
	public void setMonth3(String month3) {
		this.month3 = month3;
	}


	/**
	 * @return the smeter3
	 */
	public String getSmeter3() {
		return smeter3;
	}


	/**
	 * @param smeter3 the smeter3 to set
	 */
	public void setSmeter3(String smeter3) {
		this.smeter3 = smeter3;
	}


	/**
	 * @return the usage3
	 */
	public String getUsage3() {
		return usage3;
	}


	/**
	 * @param usage3 the usage3 to set
	 */
	public void setUsage3(String usage3) {
		this.usage3 = usage3;
	}


	/**
	 * @return the ppj3
	 */
	public String getPpj3() {
		return ppj3;
	}


	/**
	 * @param ppj3 the ppj3 to set
	 */
	public void setPpj3(String ppj3) {
		this.ppj3 = ppj3;
	}


	/**
	 * @return the ppn3
	 */
	public String getPpn3() {
		return ppn3;
	}


	/**
	 * @param ppn3 the ppn3 to set
	 */
	public void setPpn3(String ppn3) {
		this.ppn3 = ppn3;
	}


	/**
	 * @return the rent3
	 */
	public String getRent3() {
		return rent3;
	}


	/**
	 * @param rent3 the rent3 to set
	 */
	public void setRent3(String rent3) {
		this.rent3 = rent3;
	}


	/**
	 * @return the fine3
	 */
	public String getFine3() {
		return fine3;
	}


	/**
	 * @param fine3 the fine3 to set
	 */
	public void setFine3(String fine3) {
		this.fine3 = fine3;
	}


	/**
	 * @return the stamp3
	 */
	public String getStamp3() {
		return stamp3;
	}


	/**
	 * @param stamp3 the stamp3 to set
	 */
	public void setStamp3(String stamp3) {
		this.stamp3 = stamp3;
	}


	/**
	 * @return the bill3
	 */
	public String getBill3() {
		return bill3;
	}


	/**
	 * @param bill3 the bill3 to set
	 */
	public void setBill3(String bill3) {
		this.bill3 = bill3;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FData [areacode=" + areacode + ", id=" + id + ", name=" + name
				+ ", address=" + address + ", powerrate=" + powerrate
				+ ", poweruse2=" + poweruse2 + ", boxid=" + boxid
				+ ", category=" + category + ", hfactor=" + hfactor
				+ ", mfactor=" + mfactor + ", totalbill=" + totalbill
				+ ", billstatus=" + billstatus + ", poweruse=" + poweruse
				+ ", filler=" + filler + ", month=" + month + ", smeter="
				+ smeter + ", usage=" + usage + ", ppj=" + ppj + ", ppn=" + ppn
				+ ", rent=" + rent + ", fine=" + fine + ", stamp=" + stamp
				+ ", bill=" + bill + ", month2=" + month2 + ", smeter2="
				+ smeter2 + ", usage2=" + usage2 + ", ppj2=" + ppj2 + ", ppn2="
				+ ppn2 + ", rent2=" + rent2 + ", fine2=" + fine2 + ", stamp2="
				+ stamp2 + ", bill2=" + bill2 + ", month3=" + month3
				+ ", smeter3=" + smeter3 + ", usage3=" + usage3 + ", ppj3="
				+ ppj3 + ", ppn3=" + ppn3 + ", rent3=" + rent3 + ", fine3="
				+ fine3 + ", stamp3=" + stamp3 + ", bill3=" + bill3 + "]";
	}
	
	

}
