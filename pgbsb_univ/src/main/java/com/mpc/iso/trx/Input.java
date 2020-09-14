package com.mpc.iso.trx;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

import com.mpc.iso.util.IsoMpcUtil;

public class Input {
	protected static final Log log = LogFactory.getLog(Input.class);
	private int no;
	private String objectName;
	private int start;
	private int end;
	private int length;
	private String padding;
	private List<IsoPart> isoParts = new ArrayList<IsoPart>();

	public final static String SPACE = "SPACE";
	public final static String ZERO = "ZERO";

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

	public List<IsoPart> getIsoParts() {
		return isoParts;
	}

	public void setIsoParts(List<IsoPart> isoParts) {
		this.isoParts = isoParts;
	}

	private String processString(String value) {
		// substring (start - end)
		if (end != 0) {
			value = value.substring(start, end);
		}

		// padding
		if (length != 0) {
			if (padding.equals(SPACE)) {
				value = ISOUtil.strpad(value, length);
			} else if (padding.equals(ZERO)) {
				try {
					value = ISOUtil.zeropad(value, length);
				} catch (ISOException e) {
					log.error("Exception Message", e);
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public String parse(Object o) {
		if (o == null) {
			log.error("object of input was null");
			return "";
		}

		// check string format
		if (objectName.contains(".")) {
			String[] arr = objectName.split("\\.");

			Class<?> c = o.getClass();

			// if(c.getSimpleName().equalsIgnoreCase(arr[0])){
			try {
				// get the value of variable
				Field fields[] = c.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					//String x = fields[i].getName();
					if (fields[i].getName().equalsIgnoreCase(arr[1])) {
						fields[i].setAccessible(true);

						if (fields[i].get(o) != null) {
							String value = fields[i].get(o).toString();
							Object fieldType = fields[i].getType();
							if (fieldType == double.class) {
								value = IsoMpcUtil.ISONUMBERFORMAT.format(Double
										.parseDouble(value) * 100); // use 2 decimal
																	// point
							} else if (fieldType == Date.class) {
								value = IsoMpcUtil.ISODATEFORMAT.format(fields[i]
										.get(o));
							}
							value = processString(value);
							return value;
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

			// }

		} else {
			String value = o.toString();

			value = processString(value);
			return value;
		}
		return "";
	}

	public String parsePart(Map<String, Object> map) {
		String result = "";
		for (int i = 0; i < isoParts.size(); i++) {
			IsoPart isoPart = isoParts.get(i);

			String key = isoPart.getObjectName();
			if (key.contains(".")) {
				key = key.split("\\.")[0];
			}
			Object o = map.get(key);

			result += isoPart.parse(o);
		}

		return result;
	}
}
