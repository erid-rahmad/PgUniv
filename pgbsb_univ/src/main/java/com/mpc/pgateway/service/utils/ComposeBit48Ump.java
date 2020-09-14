package com.mpc.pgateway.service.utils;

import h2humplg.InquiryResponse;
import h2humplg.PaymentItem;
import id.ac.univpgri_palembang.ws.InquiryRespon;
import id.ac.univpgri_palembang.ws.ItemPembayaran;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;


public class ComposeBit48Ump {
	private static Logger log = Logger.getLogger(ComposeBit48Ump.class);
	@Value("${rekeking.ump}") 
	public static String rekeningUmp;
	
	//INQUIRY REQ FROM IST
	public static final String KODEUNIV				="kodeUniv";
	public final static String NOMORPEMBAYARAN		="nomorPembayaran";
	
	public static final int KODEUNIV_LENGTH			= 4;
	public final static int NOMORPEMBAYARAN_LENGTH	= 20;
	
	//INQ RESP TO IST
	public final static String MESSAGE				="message";
	public final static String NAMA					="nama";
	public final static String TOTALNOMINAL			="totalNominal";
	public static final String SEMESTER		        ="semester";
	public final static String ANGKATAN				="angkatan";
	public final static String FAKULTAS				="fakultas";
	public final static String JURUSAN				="jurusan";
	public static final String JUMLAHITEM	        ="jmlhItem";
	public static final String ITEM_KODETRANSAKSI	="itemKodeTransaksi";
	public static final String ITEM_DESK_PENDEK		="itemDeskPendek";
	public static final String ITEM_DESK_PANJANG	="itemDeskPanjang";
	public static final String ITEM_NOMINALTRX		="nominalTransaksi";
	
	
	//~LENGTH ----
	public final static int MESSAGE_LENGTH			= 30;
	public final static int NAMA_LENGTH				= 50;
	public final static int TOTALNOMINAL_LENGTH		= 12;
	public static final int SEMESTER_LENGTH	        = 2;
	public final static int ANGKATAN_LENGTH			= 4;
	public final static int FAKULTAS_LENGTH			= 30;
	public final static int JURUSAN_LENGTH			= 20;
	public static final int JUMLAHITEM_LENGTH		= 2;
	public static final int ITEM_KODETRANSAKSI_LENGTH	=3;
	public static final int ITEM_DESK_PENDEK_LENGTH		=15;
	public static final int ITEM_DESK_PANJANG_LENGTH	=15;
	public static final int ITEM_NOMINALTRX_LENGTH		=12;
	public static final int ITEM_REK_UNIV			=2;
	
	
	
	//~=======================================================================================
    private static List<ItemMap> getBit48Request(){	
		List<ItemMap> item = new ArrayList<ItemMap>();
		item.add(new ItemMap(KODEUNIV,KODEUNIV_LENGTH));
		item.add(new ItemMap(NOMORPEMBAYARAN,NOMORPEMBAYARAN_LENGTH));
		item.add(new ItemMap(MESSAGE,MESSAGE_LENGTH));						
		item.add(new ItemMap(NAMA,NAMA_LENGTH));				
		item.add(new ItemMap(TOTALNOMINAL,TOTALNOMINAL_LENGTH));
		item.add(new ItemMap(SEMESTER,SEMESTER_LENGTH));
		item.add(new ItemMap(ANGKATAN,ANGKATAN_LENGTH));
		item.add(new ItemMap(FAKULTAS,FAKULTAS_LENGTH));
		item.add(new ItemMap(JURUSAN,JURUSAN_LENGTH));
		item.add(new ItemMap(JUMLAHITEM,JUMLAHITEM_LENGTH));
		return item;
	}
    
    public static Map<String,String> ComposeBit48InquiryRequestToMap(String bit48){
    	Map<String,String> mapItems = new HashMap<String, String>();
		List<ItemMap> fields= getBit48Request();
		
		int from=0,to = 0;
		System.out.println("Mapping bit48:");
		for(ItemMap item : fields){
			try{
				to = from + item.getValue();
				log.info("\t"+item.getKey() + "[" + item.getValue() + "]=>"+bit48.substring(from,to).trim());
				mapItems.put(item.getKey(), bit48.substring(from,to).trim());
				from += item.getValue();
			}catch(Exception e){
				if(item.getKey().equals(NOMORPEMBAYARAN)){
					log.info("\t"+item.getKey() + "[" + item.getValue() + "]=>"+bit48.substring(4,bit48.length()).trim());
					mapItems.put(item.getKey(), bit48.substring(3,bit48.length()).trim());
				}
			}
		}
		return mapItems;
    }
    
    public static String ParsingRespInquiryToBit48(InquiryResponse inqResp){
    	String bit48="";
    	try{
    		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    		int th = year - Integer.parseInt(inqResp.getAngkatan().equals("") ? "0" : inqResp.getAngkatan());
    		String semester= "1";
    		if(th != 0){
    			semester = String.valueOf(th*2);
    		}
    		
    		bit48 += writeDataBegin(inqResp.getNomorPembayaran()," ",NOMORPEMBAYARAN_LENGTH);
    		bit48 += writeDataBegin(inqResp.getMessage()		," ",MESSAGE_LENGTH);
    		bit48 += writeDataBegin(inqResp.getNama()			," ",NAMA_LENGTH);
    		bit48 += writeDataEnd(inqResp.getTotalNominal()		,"0",TOTALNOMINAL_LENGTH);
    		bit48 += writeDataEnd(semester						,"0",SEMESTER_LENGTH);
    		bit48 += writeDataBegin(inqResp.getAngkatan()		," ",ANGKATAN_LENGTH);
    		bit48 += writeDataBegin(inqResp.getFakultas()		," ",FAKULTAS_LENGTH);
    		bit48 += writeDataBegin(inqResp.getJurusan()		," ",JURUSAN_LENGTH);
    		if(inqResp.getItemPembayaran() != null){
    			bit48 += writeDataEnd(inqResp.getItemPembayaran().getPaymentItem().size()+"","0",JUMLAHITEM_LENGTH);
    			int no = 1;
    			for(PaymentItem item : inqResp.getItemPembayaran().getPaymentItem()){
    				bit48 += writeDataEnd(String.valueOf(no++)				,"0",ITEM_KODETRANSAKSI_LENGTH);
        			bit48 += writeDataBegin(item.getDeskripsiPanjang()		," ",ITEM_DESK_PANJANG_LENGTH);
        			bit48 += writeDataEnd(item.getNominalTransaksi()		,"0",ITEM_NOMINALTRX_LENGTH);
        			bit48 += writeDataEnd(""								,"0",ITEM_REK_UNIV);	
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return bit48;
    }
    
    public static String ParsingRespInquiryToBit48PGRI(InquiryRespon inqResp){
    	String bit48="";
    	try{
//    		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
//    		int th = year - Integer.parseInt(inqResp.getAngkatan().equals("") ? "0" : inqResp.getAngkatan());
    		String semester= "1";
//    		if(th != 0){
//    			semester = String.valueOf(th*2);
//    		}
    		
    		bit48 += writeDataBegin(inqResp.getNomorPembayaran()," ",NOMORPEMBAYARAN_LENGTH);
    		bit48 += writeDataBegin(inqResp.getDeskGlobal()		," ",MESSAGE_LENGTH);
    		bit48 += writeDataBegin(inqResp.getDataMahasiswa().getNama()			," ",NAMA_LENGTH);
    		bit48 += writeDataEnd(inqResp.getTotalNominal()		,"0",TOTALNOMINAL_LENGTH);
    		bit48 += writeDataEnd(inqResp.getDataMahasiswa().getPeriode()		,"0",SEMESTER_LENGTH);  //semester diubah menjadi periode
    		bit48 += writeDataBegin(inqResp.getDataMahasiswa().getAngkatan()		," ",ANGKATAN_LENGTH);
    		bit48 += writeDataBegin(inqResp.getDataMahasiswa().getFakultas()		," ",FAKULTAS_LENGTH);
    		bit48 += writeDataBegin(inqResp.getDataMahasiswa().getJurusan()		," ",JURUSAN_LENGTH);
    		if(inqResp.getItemPembayaran() != null){
    			bit48 += writeDataEnd(inqResp.getJumlahItem()+"","0",JUMLAHITEM_LENGTH);
    			int jml = Integer.parseInt(inqResp.getJumlahItem());
    			int no = 0;
    			for(String kodetrx : inqResp.getItemPembayaran().getKodeTransaksi().getString()){
    				bit48 += writeDataEnd(kodetrx				,"0",ITEM_KODETRANSAKSI_LENGTH);
        			bit48 += writeDataBegin(inqResp.getItemPembayaran().getDeskripsi().getString().get(no)		," ",ITEM_DESK_PANJANG_LENGTH);
        			bit48 += writeDataEnd(inqResp.getItemPembayaran().getNominalTransaksi().getString().get(no)		,"0",ITEM_NOMINALTRX_LENGTH);
        			bit48 += writeDataEnd(inqResp.getItemPembayaran().getKodeRekening().getString().get(no)	,"0",ITEM_REK_UNIV);
        			no++;
        			
        			if(no == jml){
        				break;
        			}
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return bit48;
    }
    
    public static String ParsingRespPaymentToBit48(InquiryResponse inqResp){
    	String bit48="";
    	try{
    		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    		int th = year - Integer.parseInt(inqResp.getAngkatan().equals("") ? "0" : inqResp.getAngkatan());
    		String semester= "1";
    		if(th != 0){
    			semester = String.valueOf(th*2);
    		}
    		
    		bit48 += writeDataBegin(inqResp.getNomorPembayaran()," ",NOMORPEMBAYARAN_LENGTH);
    		bit48 += writeDataBegin(inqResp.getMessage()		," ",MESSAGE_LENGTH);
    		bit48 += writeDataBegin(inqResp.getNama()			," ",NAMA_LENGTH);
    		bit48 += writeDataEnd(inqResp.getTotalNominal()		,"0",TOTALNOMINAL_LENGTH);
    		bit48 += writeDataEnd(semester						,"0",SEMESTER_LENGTH);
    		bit48 += writeDataBegin(inqResp.getAngkatan()		," ",ANGKATAN_LENGTH);
    		bit48 += writeDataBegin(inqResp.getFakultas()		," ",FAKULTAS_LENGTH);
    		bit48 += writeDataBegin(inqResp.getJurusan()		," ",36);
    		if(inqResp.getItemPembayaran() != null){
    			bit48 += writeDataEnd(inqResp.getItemPembayaran().getPaymentItem().size()+"","0",JUMLAHITEM_LENGTH);
    			int no = 1;
    			for(PaymentItem item : inqResp.getItemPembayaran().getPaymentItem()){
    				bit48 += writeDataEnd(String.valueOf(no++)				,"0",ITEM_KODETRANSAKSI_LENGTH);
        			bit48 += writeDataBegin(item.getDeskripsiPanjang()		," ",ITEM_DESK_PANJANG_LENGTH);
        			bit48 += writeDataEnd(item.getNominalTransaksi()		,"0",ITEM_NOMINALTRX_LENGTH);
        			bit48 += writeDataEnd(""								,"0",ITEM_REK_UNIV);
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return bit48;
    }
    public static String ParsingRespPaymentToBit48PGRI(InquiryRespon inqResp){
    	String bit48="";
    	try{
    		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
//    		int th = year - Integer.parseInt(inqResp.getAngkatan().equals("") ? "0" : inqResp.getAngkatan());
    		String semester= "1";
//    		if(th != 0){
//    			semester = String.valueOf(th*2);
//    		}
    		
    		bit48 += writeDataBegin(inqResp.getNomorPembayaran()," ",NOMORPEMBAYARAN_LENGTH);
    		bit48 += writeDataBegin(inqResp.getDeskGlobal()		," ",MESSAGE_LENGTH);
    		bit48 += writeDataBegin(inqResp.getDataMahasiswa().getNama()			," ",NAMA_LENGTH);
    		bit48 += writeDataEnd(inqResp.getTotalNominal()		,"0",TOTALNOMINAL_LENGTH);
    		bit48 += writeDataEnd(semester						,"0",SEMESTER_LENGTH);
    		bit48 += writeDataBegin(inqResp.getDataMahasiswa().getAngkatan()		," ",ANGKATAN_LENGTH);
    		bit48 += writeDataBegin(inqResp.getDataMahasiswa().getFakultas()		," ",FAKULTAS_LENGTH);
    		bit48 += writeDataBegin(inqResp.getDataMahasiswa().getJurusan()		," ",36);
    		if(inqResp.getItemPembayaran() != null){
    			bit48 += writeDataEnd(inqResp.getJumlahItem() +"","0",JUMLAHITEM_LENGTH);
    			int jml = Integer.parseInt(inqResp.getJumlahItem());
    			int no = 0;
    			for(String kodetrx : inqResp.getItemPembayaran().getKodeTransaksi().getString()){
    				bit48 += writeDataEnd(kodetrx			,"0",ITEM_KODETRANSAKSI_LENGTH);
        			bit48 += writeDataBegin(inqResp.getItemPembayaran().getDeskripsi().getString().get(no)	," ",ITEM_DESK_PANJANG_LENGTH);
        			bit48 += writeDataEnd(inqResp.getItemPembayaran().getNominalTransaksi().getString().get(no)	,"0",ITEM_NOMINALTRX_LENGTH);
        			bit48 += writeDataEnd(inqResp.getItemPembayaran().getKodeRekening().getString().get(no)	,"0",ITEM_REK_UNIV);
        			
        			no++;
        			
        			if(no == jml){
        				break;
        			}
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return bit48;
    }
    public static String writeDataBegin(String data,String padding, int length){
    	if(data==null) data="";
    	if(data.length() > length) data= data.substring(0,length);
    	for(int i=data.length();i<length;i++){
    		data += padding;
    	}
    	return data;
    }
    
    public static String writeDataEnd(String data,String padding, int length){
    	String _ret="";
    	if(data==null) data="";
    	if(data.length() > length) data= data.substring(0,length);
    	for(int i=0;i<length - data.length();i++){
    		_ret += padding;
    	}
    	_ret += data;
    	return _ret;
    }
    
    public static String parsePeriode(String periode){
    	String _return="";
    	Date dt = new Date();
		try {
			dt = new SimpleDateFormat("yyyyMM").parse(periode);
			_return = new SimpleDateFormat("MMyyyy").format(dt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return _return; 
    }
}
