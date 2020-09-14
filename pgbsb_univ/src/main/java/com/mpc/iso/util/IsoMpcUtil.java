package com.mpc.iso.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class IsoMpcUtil {
	public static final NumberFormat ISONUMBERFORMAT = new DecimalFormat("###");
	public static final DateFormat ISODATEFORMAT = new SimpleDateFormat("ddMMyy");
}
