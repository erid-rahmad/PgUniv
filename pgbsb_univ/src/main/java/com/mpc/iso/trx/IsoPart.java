package com.mpc.iso.trx;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

import com.mpc.iso.util.IsoMpcUtil;

public class IsoPart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1010008017162283169L;
	protected static final Log log = LogFactory.getLog(IsoPart.class);
	private String objectName;
	private int start;
	private int end;
	private int length;
	private String padding;
	private boolean loop;
	private List<PartItem> partItems;
	
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

	public List<PartItem> getPartItems() {
		return partItems;
	}
	public void setPartItems(List<PartItem> partItems) {
		this.partItems = partItems;
	}
	public boolean isLoop() {
		return loop;
	}
	public void setLoop(boolean loop) {
		this.loop = loop;
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
		
		if(!loop){
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
									value = IsoMpcUtil.ISONUMBERFORMAT.format(Double.parseDouble(value)*100) ;		//use 2 decimal point						
								}
								else if(fieldType == Date.class){
									value = IsoMpcUtil.ISODATEFORMAT.format(fields[i].get(o)) ;								
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
		} 
		//if loop
		else{
			String value = "";
			List<?> list = (List<?>) o;
			for(int i=0;i<list.size();i++){
				Object ox = list.get(i);
				//Class<?> c = ox.getClass();
				
				for(PartItem partItem : partItems){
//					String[] arr = partItem.getObjectName().split("\\.");
					
					value += partItem.parse(ox);
					
//					Field fields[] = c.getDeclaredFields();
//					for (int j = 0; j < fields.length; j++){ 
//						if(fields[j].getName().equalsIgnoreCase(arr[1])){
//							fields[j].setAccessible(true); 
//							
//							try {
//								String valuex = fields[j].get(ox).toString();
//								value += processString(valuex);
//							} catch (IllegalArgumentException e) {
//								e.printStackTrace();
//							} catch (IllegalAccessException e) {
//								e.printStackTrace();
//							}
//							
//						}
//					}
				}
			}
			return value;
			
		}
		
		return "";

	}
	
	public void parse(Object o, String value){
		if(o == null){
			log.error("object of input was null");
			return;
		}
		
		//check string format
		if(objectName.contains(".")){
			String[] arr = objectName.split("\\.");
			
			Class<?> c = o.getClass();

//			if(c.getSimpleName().equalsIgnoreCase(arr[0])){
				try {		    			
					//  get the value of variable
					Field fields[] = c.getDeclaredFields();
					for (int i = 0; i < fields.length; i++){ 
						if(fields[i].getName().equalsIgnoreCase(arr[1])){
							fields[i].setAccessible(true); 
							
							if(end != 0 && value.length() >= end){
								value = value.substring(start,end);
							}
							Object fieldType = fields[i].getType();
							if(fieldType == String.class){
								if(value != null){
									value = value.toString().trim();
								}
								if (fields[i].getName().equalsIgnoreCase("currencycode")) {
									BeanInfo beanInfo = Introspector.getBeanInfo(c);
									PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
									for (PropertyDescriptor p : properties) {
										if (p.getName().equalsIgnoreCase("currencycode")) {
											Method setter = p.getWriteMethod();
											setter.invoke(o, value);
										}
									}
								} else {
									fields[i].set(o,value);		
								}
							}else if(fieldType == double.class){
								fields[i].set(o,Double.parseDouble(value));								
							}else if(fieldType == int.class){
								fields[i].set(o,Integer.parseInt(value));								
							}							
							
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
				} catch (Exception e) {
					log.error("Exception Message", e);
					e.printStackTrace();
				}

//			}

		}
	}
	
}
