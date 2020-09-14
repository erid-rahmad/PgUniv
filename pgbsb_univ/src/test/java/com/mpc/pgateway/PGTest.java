package com.mpc.pgateway;

import h2humplg.ObjectFactory;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mpc.iso.util.ISOMsgBit;
import com.mpc.pgateway.service.UMPService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/business-config.xml"})

public class PGTest {
	@Autowired
	private UMPService ump;
	public static ObjectFactory FACTORYUMP = new ObjectFactory();
	@Test
	public void testInquiry() throws ISOException{
		ISOMsg req= new ISOMsg();
		req.setMTI("200");
		req.set(ISOMsgBit.TERMID,"ATM0001");
		req.set(ISOMsgBit.DATA,"120x4112016002");
		req.set(ISOMsgBit.CHANNEL,"6010");
		req.set(ISOMsgBit.TGL2,"0712");
		req.set(ISOMsgBit.JAM,"092212");
		req.set(ISOMsgBit.TRACE,"190023");
		try {
			ump.Inquiry(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
