package com.mpc.pgateway.service.utils;

public class StringUtils {
	public static String padRight(String data,String padding, int length){
		if(data == null) data = "";
    	if(data.length() > length) data= data.substring(0,length);
    	for(int i=data.length();i<length;i++){
    		data += padding;
    	}
    	return data;
    }
    
    public static String padLeft(String data, String padding,int length){
    	String _ret="";
    	if(data == null) 
    		data = "";

    	if(data.length() > length) 
    		data= data.substring(0,length);
		for(int i=0;i<length - data.length();i++){
			_ret += padding;
		}
		_ret += data;
    	return _ret;
    }
    
    public static int getIndex(String string, String var, int no){
		int first = 0;
		int second = 0;
		
		int value = 0;
		
		for (int i = 0; i < no; i++) {
			first = string.indexOf(var);
			value = value + second;
			
			second = string.indexOf(var, first + 1);
			string = string.substring(second);
		}
		if(no == 1){
			return first;
		}else{
			return value;
		}
	}
}
