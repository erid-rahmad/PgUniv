package com.mpc.iso.trx;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

import com.mpc.iso.util.IsoMpcUtil;

public class PartItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3144445364024861140L;
	protected static final Log log = LogFactory.getLog(PartItem.class);
	private String objectName;
	private int start;
	private int end;
	private int length;
	private String padding;
	
	public final static String SPACE = "SPACE";
	public final static String ZERO = "ZERO";
	public final static String ZERO2DEC = "ZERO2DEC";
	
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getPadding() {
		return padding;
	}
	public void setPadding(String padding) {
		this.padding = padding;
	}
	private String processString(String value){
		//substring (start - end)
		if(end != 0){
			value = value.substring(start,end);
		}
		
		//padding
		if(length != 0){
			if(value.length() > length){
				value = value.substring(0,0+length);
			}
			
			if(padding.equals(SPACE)){
				value = ISOUtil.strpad(value, length);
			}else if(padding.equals(ZERO)){
				try {
					value = ISOUtil.zeropad(value, length);
				} catch (ISOException e) {
					log.error("Exception Message", e);
					e.printStackTrace();
				}
			}
			else if(padding.equals(ZERO2DEC)){
				try {
					value = value + "00";
					value = ISOUtil.zeropad(value, length);
				} catch (ISOException e) {
					log.error("Exception Message", e);
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	public String parse(Object o){
		if(o == null){
			log.error("object of input was null");
			return "";
		}
		
		//check string format
		if(objectName.contains(".")){
			String[] arr = objectName.split("\\.");
			
			Class<?> c = o.getClass();

//				if(c.getSimpleName().equalsIgnoreCase(arr[0])){
				try {		    			
					//  get the value of variable
					Field fields[] = c.getDeclaredFields();
					for (int i = 0; i < fields.length; i++){ 
						if(fields[i].getName().equalsIgnoreCase(arr[1])){
							fields[i].setAccessible(true); 
							
							String value = fields[i].get(o).toString();
							Object fieldType = fields[i].getType();
							if(fieldType == double.class){
								value = IsoMpcUtil.ISONUMBERFORMAT.format(Double.parseDouble(value)*100) ; //use 2 decimal point								
							}
							
							value = processString(value);
							
							return value;
						}
					}

				} catch (SecurityException e) {
					log.error("Exception Message", e);
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					log.error("Exception Message", e);
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					log.error("Exception Message", e);
					e.printStackTrace();
				} catch (Exception e){
					log.error("Exception Message", e);
					//continue if parsing not completed
				}

//				}

		}else{
			String value = o.toString();
			
			value = processString(value);
			return value;
		}
		
		
		
		return "";

	}
}
