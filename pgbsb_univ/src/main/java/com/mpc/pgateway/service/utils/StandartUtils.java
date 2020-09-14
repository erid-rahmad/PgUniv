package com.mpc.pgateway.service.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;
import org.springframework.stereotype.Component;

import com.mpc.pgateway.model.FData;

@Component
public class StandartUtils {
	/*private final static DateFormat format_ddSlMMSlyyyy_hh_mm_ss = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	private final static DateFormat format_ddStMMStyyyy_hh_mm_ss = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	private final static DateFormat format_ddStMMMStyyyy_hh_mm_ss = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
	private final static DateFormat format_ddSpcMMMSpcyyyy_hh_mm_ss = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
	private final static DateFormat format_YYYYSpcMMSpcDD_hh_mm_ss = new SimpleDateFormat("yyyy MM dd hh:mm:ss");*/
	public final static DateFormat format_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	public final static DateFormat format_YYYYStMMStDD_hh_mm_ss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public final static DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	public final static DateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public final static NumberFormat NUMBERFORMAT = new DecimalFormat("######,####");
	public final static Logger log = Logger.getLogger(StandartUtils.class);
	
	public StandartUtils(){
	}
	public static Date getStandartDateParser(String strDate){
		Date date = null;		
		try{ date = format_YYYYStMMStDD_hh_mm_ss.parse(strDate);
		}catch(Exception e5){
			log.error("ERROR WHEN PARSE DATE");
			date = null;
		}
		return date;
	} 
	public static XMLGregorianCalendar getDate(String date) throws ParseException, DatatypeConfigurationException{
			GregorianCalendar greg = new GregorianCalendar();
			greg.setTime(getStandartDateParser(date));
			XMLGregorianCalendar dt =DatatypeFactory.newInstance().newXMLGregorianCalendar(greg);
			//System.out.println(date +"<----->"+dt.toString());
			return dt;
	    }
	public static String getDateCurrent(){
		String dt = formatDate.format(Calendar.getInstance().getTime());
		return dt;
	}
	
	public static String getDateTimeCurrent(){
		String dt = formatDateTime.format(Calendar.getInstance().getTime());
		return dt;
	}
	
	public static Timestamp getTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * 
	 * @param num
	 * @return
	 */
	public static String convertNumToRomawi(int num){
		String returnVal = "";
		switch (num) {
			case 0:		returnVal = "I";	break;
			case 1:		returnVal = "II";	break;
			case 2:		returnVal = "III";	break;
			case 3:		returnVal = "IV";	break;
			case 4:		returnVal = "V";	break;
			case 5:		returnVal = "VI";	break;
			case 6:		returnVal = "VII";	break;
			case 7:		returnVal = "VIII";	break;
			case 8:		returnVal = "IX";	break;
			case 9:		returnVal = "X";	break;
			case 10:	returnVal = "XI";	break;
			case 11:	returnVal = "XII";	break;
			
			default:	returnVal = "";		break;
		}
		return returnVal;
	}
	
	public static String getEncodingBase64(String planText){
		byte[] encodedBytes = Base64.encodeBase64(planText.getBytes());
		return new String(encodedBytes);
		
	}
	
	public static String getDecodingBase64(String chiperText){
		byte[] decodedBytes = Base64.decodeBase64(chiperText.getBytes());
		return new String(decodedBytes);
	}
	
	//==============================DATA FORMATER==========================================
    public static void setDataFormater(Object obj,FData fdata){
    	
    	Class<?> c = obj.getClass();
	    try {
	    	for (Field field : fdata.getClass().getDeclaredFields()) {
			    field.setAccessible(true);
			    String name = field.getName();
			    Object value= field.get(fdata);				
			    //System.out.printf("Field name: %s, Field value: %s, length: %s\n", name, value,value.toString().length());
			    
			    //pengisian otomatis dari fdata ke class data statis/dinamis/others
			    Field fields[] = c.getDeclaredFields();	  
			    for (int i = 0; i < fields.length; i++){ 
					if(fields[i].getName().equalsIgnoreCase(name)){
						fields[i].setAccessible(true); 
						Object fieldType = fields[i].getType();
						if(fieldType == String.class){
							if(value != null){
								value = value.toString().trim();
							}
							fields[i].set(obj,value);								
						}else if(fieldType == Long.class){
							if(value != null){
								value = NUMBERFORMAT.parse(value.toString()).longValue();
							}
							fields[i].set(obj,value);		
						}else if(fieldType == Integer.class){
							if(value != null){
								value = NUMBERFORMAT.parse(value.toString()).intValue();
							}
							fields[i].set(obj,value);		
						}else if(fieldType == Double.class){
							if(value != null){
								value = NUMBERFORMAT.parse(value.toString()).doubleValue();
							}
							fields[i].set(obj,value);		
						}
					}
				}
	    	}
	    } catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}	
    }
    
	//==============================CHECK DIRECTORY==========================================
    public static boolean checkDirectory(String path, boolean createIfNotAvailable){
		File folder = new File(path);
		boolean available =folder.exists();
		
		if(!available &&  createIfNotAvailable){
			folder.mkdir();
			available = true;
		}
		return available;
	}
    
	//==============================CHECK DIRECTORY==========================================
    public static boolean isFileAvailable(File file,String tag){
		boolean available= false;
		if(file.exists()){
			available = true;
			System.out.println("File " + file.getName() + " detected...");
		}else{
			System.out.println("File "+ tag +" not found...");
		}
		return available;
	}
    
    public static Integer getPcode(String pcode){
    	return Integer.parseInt(pcode)/10000;
    }
    
    public static String getDateCurrent(String format) {
        String dt = new SimpleDateFormat(format).format(new Date());
        return dt;
    }
    
    public static Date getISOLocalDateTime(ISOMsg isomsg) {
        Date localdate = new Date();
        try {
            if(isomsg.hasField(12) && isomsg.hasField(13)){
                localdate = format_yyyyMMddHHmmss.parse(
                        getDateCurrent("yyyy")+isomsg.getString(13) + isomsg.getString(12));
            }
        } catch (ParseException e) {
            log.error(e);
        }
        return localdate;
    }
}