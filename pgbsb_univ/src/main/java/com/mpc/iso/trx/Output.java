package com.mpc.iso.trx;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public class Output {
	protected static final Log log = LogFactory.getLog(Output.class);
	private int no;
	private String objectName;
	private int start;
	private int end;
	private int interval;
	private List<IsoPart> isoParts = new ArrayList<IsoPart>();
	private boolean loop; //looping output in one bit iso8583 (eg. for history isomessage)
	private int startLoop;
	private boolean mandatory = true;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

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

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public List<IsoPart> getIsoParts() {
		return isoParts;
	}

	public void setIsoParts(List<IsoPart> isoParts) {
		this.isoParts = isoParts;
	}
	
	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public int getStartLoop() {
		return startLoop;
	}

	public void setStartLoop(int startLoop) {
		this.startLoop = startLoop;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean optional) {
		this.mandatory = optional;
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
								fields[i].set(o,value);								
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
				} 

//			}

		}
	}
	
	@SuppressWarnings("unchecked")
	public void parsePart(ISOMsg resp, Map<String, Object> map){
		//check if loop fixed value (eg. history transaction) or normal fixed value
		if(loop){
			Object objectLoop = map.get(objectName);
			List<Object> list = new ArrayList<Object>();
			Object o = null;
			if (!(objectLoop instanceof List)) {
				map.put(objectName, list);
			} else {
				list = (List<Object>)objectLoop;
			}
			
			String s = "";
			try {
				s = resp.getValue(no).toString();
				s = s.substring(startLoop);
			} catch (ISOException e1) {
				log.error("Exception Message", e1);
				e1.printStackTrace();
			}
			
			while(s.length() >= interval){
				try {
					if (!(objectLoop instanceof List)) {
						o = objectLoop.getClass().newInstance();
					} else {
						o = list.get(0).getClass().newInstance();
					}
					//set the fields value of object
					for(int i=0;i<isoParts.size();i++){
						IsoPart isoPart = isoParts.get(i);
						
						String key = isoPart.getObjectName();
						if(key.contains(".")){
							key = key.split("\\.")[0];
						}
							
						isoPart.parse(o,s);
					}

					//put object to the list
					list.add(o);
					
				} catch (InstantiationException e) {
					log.error("Exception Message", e);
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					log.error("Exception Message", e);
					e.printStackTrace();
				}
				
				s = s.substring(interval);
			}
			
			
		}else{
			for(int i=0;i<isoParts.size();i++){
				IsoPart isoPart = isoParts.get(i);
				
				String key = isoPart.getObjectName();
				if(key.contains(".")){
					key = key.split("\\.")[0];
				}
				Object o = map.get(key);
				
				try {
					isoPart.parse(o,resp.getValue(no).toString());
				} catch (ISOException e) {
					log.error("Exception Message", e);
					e.printStackTrace();
				}catch (Exception e){
					log.error("Exception Message", e);
					//continue processing output without complete bit
				}
			}
			
		}
		
	}
	
}
