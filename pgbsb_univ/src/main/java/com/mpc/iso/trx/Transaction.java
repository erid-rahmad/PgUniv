package com.mpc.iso.trx;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;
import org.jpos.iso.ISOMsg;

import com.mpc.iso.trx.Input;
import com.mpc.iso.trx.Output;

public class Transaction {

	protected static final Log log = LogFactory.getLog(Transaction.class);
	private String pcode;
	private boolean reversal;
	private Map<Integer, Input> inputs = new HashMap<Integer, Input>();
	private Map<Integer, Output> outputs = new HashMap<Integer, Output>();
	
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public Map<Integer, Input> getInputs() {
		return inputs;
	}
	public void setInputs(Map<Integer, Input> inputs) {
		this.inputs = inputs;
	}
	public Map<Integer, Output> getOutputs() {
		return outputs;
	}
	public void setOutputs(Map<Integer, Output> outputs) {
		this.outputs = outputs;
	}
	public boolean isReversal() {
		return reversal;
	}
	public void setReversal(boolean reversal) {
		this.reversal = reversal;
	}
	
	public void process(ISOMsg m, Map<String,Object> map){
		
		Set<Entry<Integer,Input>> set = inputs.entrySet();
		for(Entry<Integer,Input> x : set){
			//	System.out.println(x.getKey()+" -> "+x.getValue());

			String key = x.getValue().getObjectName();
			if(key.contains(".")){
				key = key.split("\\.")[0];
			}
			Object o = map.get(key);
			
			Input input = x.getValue();
			try {
				if(input.getIsoParts().size() > 0){
					m.set(new ISOField(input.getNo(), input.parsePart(map)));
				}else{
					m.set(new ISOField(input.getNo(), input.parse(o)));
				}
			} catch (ISOException e) {
				log.error("Exception Message", e);
				e.printStackTrace();
			}
		}
	}
	
	public void parseOutput(ISOMsg resp, Map<String, Object> map) {

		Set<Entry<Integer,Output>> set = outputs.entrySet();
		for(Entry<Integer,Output> x : set){
			//System.out.println(x.getKey()+" -> "+x.getValue());

			Output output = x.getValue();
			if (!output.isMandatory() && !resp.hasField(output.getNo()))
				continue;
			
			String key = output.getObjectName();
			if(key.contains(".")){
				key = key.split("\\.")[0];
			}
			Object o = map.get(key);
			
			try {
				if(output.getIsoParts().size() > 0){
					output.parsePart(resp, map);
				}else{
					if(resp.hasField(output.getNo())){
						output.parse(o,resp.getValue(output.getNo()).toString());
					}
				}
			} catch (ISOException e) {
				log.error("Exception Message", e);
				e.printStackTrace();
			}
		}
	}
	
}
