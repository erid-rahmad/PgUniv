package com.mpc.pgateway.service.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;

import com.mpc.pgateway.model.UMPPInquiryResponse;

public class ComposeDataUMPP {
	private static Logger log = Logger.getLogger(ComposeDataUMPP.class);
	private static Map<String,String[]> myMemory = new HashMap<String,String[]>();
	
	public static void addMemory(String idTag,ISOMsg iso) {
		String data[] = {idTag,iso.getString(48)};
		myMemory.put(getNomorPembayaran(iso.getString(48)).trim(), data);
		log.trace("Add to memory : ["+idTag+"]->["+myMemory.size()+"]");
	}
	
	public static String[] getMemory(ISOMsg iso) {
		String id = getNomorPembayaran(iso.getString(48)).trim();
		String data[] = myMemory.get(id);
		try {
			log.trace("Get memory : ["+id+"]->["+myMemory.size()+"]");
		}catch(Exception e) {
			data = new String[2];
			data[0] = "";
			data[1] = "";
		}
		return data;
	}
	
	public static void removeMemory(ISOMsg iso) {
		String id = getNomorPembayaran(iso.getString(48)).trim();
		myMemory.remove(id);
		log.trace("Remove memory : ["+id+"]->["+myMemory.size()+"]");
	}
	
	public static void clearMemory() {
		myMemory.clear();
	}
	
	/* Parser utils */
	public static String getNomorPembayaran(String bit48) {
		log.trace("get normor pembayaran from bit48");
		if(bit48.length()>=24) {
			return bit48.substring(4, 24).trim();
		}
		return bit48;
	}
	
	protected String composeDataResponseInqToIST(UMPPInquiryResponse inqResp) {
		log.trace("Componse response inquiry to IST");
		String bit48 = "";
		int year = Integer.parseInt(StandartUtils.getDateCurrent("yyyy"));
		int th   = year - Integer.parseInt(inqResp.getAngkatan().equals("") ? "0" : inqResp.getAngkatan());
		String semester= "1";
		if(th != 0){
			semester = String.valueOf(th*2);
		}
		bit48  = StringUtils.padLeft("3", "0", ComposeBit48Ump.KODEUNIV_LENGTH);
		bit48 += StringUtils.padRight(inqResp.getNomorPembayaran()," ", ComposeBit48Ump.NOMORPEMBAYARAN_LENGTH);
		bit48 += StringUtils.padRight(inqResp.getMessage()," ", ComposeBit48Ump.MESSAGE_LENGTH);
		bit48 += StringUtils.padRight(inqResp.getNama()," ", ComposeBit48Ump.NAMA_LENGTH);
		bit48 += StringUtils.padLeft(inqResp.getTotalNominal(),"0", ComposeBit48Ump.TOTALNOMINAL_LENGTH);
		bit48 += StringUtils.padLeft(semester,"0", ComposeBit48Ump.SEMESTER_LENGTH);
		bit48 += StringUtils.padRight(inqResp.getAngkatan()," ", ComposeBit48Ump.ANGKATAN_LENGTH);
		bit48 += StringUtils.padRight(inqResp.getFakultas()," ", ComposeBit48Ump.FAKULTAS_LENGTH);
		bit48 += StringUtils.padRight(inqResp.getJurusan()," ", ComposeBit48Ump.JURUSAN_LENGTH);
		bit48 += StringUtils.padLeft("1","0",ComposeBit48Ump.JUMLAHITEM_LENGTH);
		bit48 += StringUtils.padLeft("1","0",ComposeBit48Ump.ITEM_KODETRANSAKSI_LENGTH);
		bit48 += StringUtils.padRight(inqResp.getDeskripsi()," ",ComposeBit48Ump.ITEM_DESK_PANJANG_LENGTH);
		bit48 += StringUtils.padLeft(inqResp.getTotalNominal(),"0",ComposeBit48Ump.ITEM_NOMINALTRX_LENGTH);
		bit48 += StringUtils.padLeft("","0",ComposeBit48Ump.ITEM_REK_UNIV);
    	
    	return bit48;
	}
	//tambahkan parameter kodeuniv untuk digunakan univ lain
	protected String composeDataResponseInqToIST(UMPPInquiryResponse inqResp,String kodeUniv) {
		log.trace("Componse response inquiry to IST");
		String bit48 = "";
		int year = Integer.parseInt(StandartUtils.getDateCurrent("yyyy"));
		int th   = year - Integer.parseInt(inqResp.getAngkatan().equals("") ? "0" : inqResp.getAngkatan());
		String semester= "1";
		if(th != 0){
			semester = String.valueOf(th*2);
		}
		bit48  = StringUtils.padLeft(kodeUniv, "0", ComposeBit48Ump.KODEUNIV_LENGTH);
		bit48 += StringUtils.padRight(inqResp.getNomorPembayaran()," ", ComposeBit48Ump.NOMORPEMBAYARAN_LENGTH);
		bit48 += StringUtils.padRight(inqResp.getMessage()," ", ComposeBit48Ump.MESSAGE_LENGTH);
		bit48 += StringUtils.padRight(inqResp.getNama()," ", ComposeBit48Ump.NAMA_LENGTH);
		bit48 += StringUtils.padLeft(inqResp.getTotalNominal(),"0", ComposeBit48Ump.TOTALNOMINAL_LENGTH);
		bit48 += StringUtils.padLeft(semester,"0", ComposeBit48Ump.SEMESTER_LENGTH);
		bit48 += StringUtils.padRight(inqResp.getAngkatan()," ", ComposeBit48Ump.ANGKATAN_LENGTH);
		bit48 += StringUtils.padRight(inqResp.getFakultas()," ", ComposeBit48Ump.FAKULTAS_LENGTH);
		bit48 += StringUtils.padRight(inqResp.getJurusan()," ", ComposeBit48Ump.JURUSAN_LENGTH);
		bit48 += StringUtils.padLeft("1","0",ComposeBit48Ump.JUMLAHITEM_LENGTH);
		bit48 += StringUtils.padLeft("1","0",ComposeBit48Ump.ITEM_KODETRANSAKSI_LENGTH);
		bit48 += StringUtils.padRight(inqResp.getDeskripsi()," ",ComposeBit48Ump.ITEM_DESK_PANJANG_LENGTH);
		bit48 += StringUtils.padLeft(inqResp.getTotalNominal(),"0",ComposeBit48Ump.ITEM_NOMINALTRX_LENGTH);
		bit48 += StringUtils.padLeft("","0",ComposeBit48Ump.ITEM_REK_UNIV);
    	
    	return bit48;
	}
	
	protected String composeDataResponsePayToIST(final String data) {
		log.trace("Componse response payment to IST ["+data.length()+"]");
		if(data.length() >= 172) {
			String beforeJurusan = data.substring(0,152);
			String jurusan = data.substring(152,172);
			String afterJurusan = data.substring(172);
			return beforeJurusan + StringUtils.padRight(jurusan, " ", 36) + afterJurusan;
		}
		return "";
	}
}
