package com.mpc.iso;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.springframework.core.io.Resource;

import com.mpc.iso.trx.Transaction;


/**
 * 
 * Configure the IsoContainer ini the spring xml:
 * <bean id="isoServer" class="com.mpc.iso.services.Iso8583Server">
		<constructor-arg value="${billing.port}"/>	
	</bean>
	
	<!-- server container services framework-->
	<bean id="serverContainer" class="com.mpc.iso.ServerContainer"
         factory-method="create">
         <constructor-arg ref="isoServer"/>
 		 <constructor-arg value="${billing.config}"/>	
    </bean>
    
     <bean id="billingConnection" class="com.mpc.ebanking.billing.BillingConnection">
		<property name="isoServer" ref="isoServer"/>	
	</bean>
	
	Listener:
	isoServer.addTrxReceiverListener(new ReceiverListener() {
			
			@Override
			public void receive(ReceiverEvent evt) {
				processTransaction(evt);
			}
		});
		
	Using Iso8583Server:
	Map<String,Object> map = new HashMap<String, Object>();
		AccountSource account = new AccountSource();
		map.put("account", account);
		
		try {
			isoServer.parse(evt.getIsomsg(), map);
		} catch (IsoException e) {
			e.printStackTrace();
		} catch (ISOException e) {
			e.printStackTrace();
		}
		
		//process to database and log
		account = (AccountSource) map.get("account");
	
	
 * @author Harda
 *
 */
public class Container {
	protected static final Log log = LogFactory.getLog(Container.class);
	private Map<String, Transaction> transactions = new HashMap<String, Transaction>();

	public Container (Resource pathConfig){
		
		try {
			InputStream ins = getClass().getClassLoader().getResourceAsStream("billing_transaction.xml");
//			InputStream ins = new BufferedInputStream(new FileInputStream(pathConfig.getFile()));
			ConfigTransactions.parse(transactions, ins);
						
			
		} catch (IOException e) {
			log.error("Exception Message", e);
			e.printStackTrace();
		}

	}

	/**
	 * parse isoMsg to map that is defined in xml configuration
	 * @param isoMsg
	 * @param map
	 * @throws IsoException
	 * @throws ISOException
	 */
	public void parse(ISOMsg isoMsg, Map<String,Object> map)
			throws ISOException {
		String pcode = isoMsg.getValue(3).toString();
		String pcodetrx = pcode.substring(0,2) + "00" + pcode.substring(4,6);
		Transaction trx = transactions.get(pcodetrx);
		
		trx.parseOutput(isoMsg, map);

		
	}
	
	/**
	 * process map to isoMsg that is defined in xml configuration
	 * @param isoMsg
	 * @param map
	 * @throws IsoException
	 * @throws ISOException
	 */
	public void process(ISOMsg isoMsg, Map<String,Object> map) 
			throws ISOException{
		String pcode = isoMsg.getValue(3).toString();
		String pcodetrx = pcode.substring(0,2) + "00" + pcode.substring(4,6);
		Transaction trx = transactions.get(pcodetrx);
		
		trx.process(isoMsg, map);
	}

}