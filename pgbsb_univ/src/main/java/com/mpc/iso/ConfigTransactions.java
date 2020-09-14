package com.mpc.iso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mpc.iso.trx.Input;
import com.mpc.iso.trx.IsoPart;
import com.mpc.iso.trx.Output;
import com.mpc.iso.trx.PartItem;
import com.mpc.iso.trx.Transaction;

public class ConfigTransactions {
	private final static Log log = LogFactory.getLog(ConfigTransactions.class);

	/**
	 * Reads the XML from the stream and configures the container and its transaction with the values
	 * @param container The Container containing transaction to be configured
	 * @param stream The InputStream containing the XML configuration
	 * @throws IOException
	 */
	public static void parse(Map<String, Transaction> transactions, InputStream stream) throws IOException {
		final DocumentBuilderFactory docfact = DocumentBuilderFactory.newInstance();
		DocumentBuilder docb = null;
		Document doc = null;
		try {
			docb = docfact.newDocumentBuilder();
			doc = docb.parse(stream);
		} catch (ParserConfigurationException ex) {
			log.error("Cannot parse XML configuration", ex);
			return;
		} catch (SAXException ex) {
			log.error("Parsing XML configuration", ex);
		}
		final Element root = doc.getDocumentElement();
			
//		Map<String, Transaction> transactions = new HashMap<String, Transaction>();
		
		//Read the transaction configuration
		NodeList nodes = root.getElementsByTagName("trx");
		Element elem = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			elem = (Element)nodes.item(i);
			Transaction transaction = new Transaction();
			
			String pcode = elem.getAttribute("pcode");
			transaction.setPcode(pcode);
			
			String reversal = elem.getAttribute("reversal");
			if("true".equalsIgnoreCase(reversal)){
				transaction.setReversal(true);
			}
			
			//configure input every transaction
			NodeList input = elem.getElementsByTagName("input");
			Element elem2 = null;
			for(int j=0; j<input.getLength(); j++){
				elem2 = (Element)input.item(j);

				NodeList bits = elem2.getElementsByTagName("bit");
				Map<Integer, Input> inputs = new HashMap<Integer, Input>();
				for (int k = 0; k < bits.getLength(); k++) {
					Element f = (Element)bits.item(k);
					Input inx = new Input();
					
					int num = Integer.parseInt(f.getAttribute("no"));
					inx.setNo(num);
					
					String obj = f.getAttribute("object");
					inx.setObjectName(obj);
					
					String start = f.getAttribute("start");
					if(start != null && !start.equals("")){
						inx.setStart(Integer.parseInt(start));
					}
					
					String end = f.getAttribute("end");
					if(end != null && !end.equals("")){
						inx.setEnd(Integer.parseInt(end));
					}
					
					String length = f.getAttribute("length");
					if(length != null && !length.equals("")){
						inx.setLength(Integer.parseInt(length));
					}
					
					String padding = f.getAttribute("padding");
					if(padding != null && !padding.equals("")){
						inx.setPadding(padding);
					}
					
					String listPart = f.getAttribute("list");
					if(listPart != null && listPart.equalsIgnoreCase("true")){
						
						NodeList isopartNode = f.getElementsByTagName("part");
						List<IsoPart> isoParts = new ArrayList<IsoPart>();
						for (int l = 0; l < isopartNode.getLength(); l++) {
							Element fx = (Element)isopartNode.item(l);
							IsoPart isoPart = new IsoPart();
														
							String objx = fx.getAttribute("object");
							isoPart.setObjectName(objx);
							
							String startx = fx.getAttribute("start");
							if(startx != null && !startx.equals("")){
								isoPart.setStart(Integer.parseInt(startx));
							}
							
							String endx = fx.getAttribute("end");
							if(endx != null && !endx.equals("")){
								isoPart.setEnd(Integer.parseInt(endx));
							}
							
							String lengthx = fx.getAttribute("length");
							if(lengthx != null && !lengthx.equals("")){
								isoPart.setLength(Integer.parseInt(lengthx));
							}
							
							String paddingx = fx.getAttribute("padding");
							if(paddingx != null && !paddingx.equals("")){
								isoPart.setPadding(paddingx);
							}
							
							//loop inside iso-part tag
							String loopx = fx.getAttribute("loop");
							if(loopx != null && loopx.equalsIgnoreCase("true")){
								isoPart.setLoop(true);
								
								NodeList isopartItem = f.getElementsByTagName("item");
								List<PartItem> partItems = new ArrayList<PartItem>();
								for (int m = 0; m < isopartItem.getLength(); m++) {
									Element fxx = (Element)isopartItem.item(m);
									PartItem partItem = new PartItem();
									
									String objxx = fxx.getAttribute("object");
									partItem.setObjectName(objxx);
									
									String startxx = fxx.getAttribute("start");
									if(startxx != null && !startxx.equals("")){
										partItem.setStart(Integer.parseInt(startxx));
									}
									
									String endxx = fxx.getAttribute("end");
									if(endxx != null && !endxx.equals("")){
										partItem.setEnd(Integer.parseInt(endxx));
									}
									
									String lengthxx = fxx.getAttribute("length");
									if(lengthxx != null && !lengthxx.equals("")){
										partItem.setLength(Integer.parseInt(lengthxx));
									}
									
									String paddingxx = fxx.getAttribute("padding");
									if(paddingxx != null && !paddingxx.equals("")){
										partItem.setPadding(paddingxx);
									}
									
									partItems.add(partItem);
								}
								isoPart.setPartItems(partItems);
								
							}
							
							
							
							isoParts.add(isoPart);
						}
						
						inx.setIsoParts(isoParts);
					}
					
//					//looping of output in one iso8583 bit
//					String loopPart = f.getAttribute("loop");
//					if(loopPart != null && loopPart.equalsIgnoreCase("true")){
//						
//						NodeList isopartNode = f.getElementsByTagName("part");
//						List<IsoPart> isoParts = new ArrayList<IsoPart>();
//						for (int l = 0; l < isopartNode.getLength(); l++) {
//							Element fx = (Element)isopartNode.item(l);
//							IsoPart isoPart = new IsoPart();
//														
//							String objx = fx.getAttribute("object");
//							isoPart.setObjectName(objx);
//							
//							String startx = fx.getAttribute("start");
//							if(startx != null && !startx.equals("")){
//								isoPart.setStart(Integer.parseInt(startx));
//							}
//							
//							String endx = fx.getAttribute("end");
//							if(endx != null && !endx.equals("")){
//								isoPart.setEnd(Integer.parseInt(endx));
//							}
//							
//							isoParts.add(isoPart);
//						}
//						
//						String startLoop = f.getAttribute("start");
//						inx.setStartLoop(Integer.parseInt(startLoop));
//						inx.setLoop(true);
//						inx.setIsoParts(isoParts);
//					}
					
					inputs.put(num, inx);
				}
				transaction.setInputs(inputs);
			}
			
			//configure output every transaction
			NodeList output = elem.getElementsByTagName("output");
			Element elem3 = null;
			for(int j=0; j<output.getLength(); j++){
				elem3 = (Element)output.item(j);

				NodeList bits = elem3.getElementsByTagName("bit");
				Map<Integer, Output> outputs = new HashMap<Integer, Output>();
				for (int k = 0; k < bits.getLength(); k++) {
					Element f = (Element)bits.item(k);
					Output outx = new Output();
					
					int num = Integer.parseInt(f.getAttribute("no"));
					outx.setNo(num);
					
					String obj = f.getAttribute("object");
					outx.setObjectName(obj);
					
					String start = f.getAttribute("start");
					if(start != null && !start.equals("")){
						outx.setStart(Integer.parseInt(start));
					}
					
					String end = f.getAttribute("end");
					if(end != null && !end.equals("")){
						outx.setEnd(Integer.parseInt(end));
					}
					
					String interval = f.getAttribute("interval");
					if(interval != null && !interval.equals("")){
						outx.setInterval(Integer.parseInt(interval));
					}
					
					String mandatory = f.getAttribute("mandatory");
					if (mandatory != null && !mandatory.equals("")) {
						if ("FALSE".equals(mandatory) || "false".equals(mandatory))
							outx.setMandatory(false);
					}
					
					//list of output in one iso8583 bit
					String listPart = f.getAttribute("list");
					if(listPart != null && listPart.equalsIgnoreCase("true")){
						
						NodeList isopartNode = f.getElementsByTagName("part");
						List<IsoPart> isoParts = new ArrayList<IsoPart>();
						for (int l = 0; l < isopartNode.getLength(); l++) {
							Element fx = (Element)isopartNode.item(l);
							IsoPart isoPart = new IsoPart();
														
							String objx = fx.getAttribute("object");
							isoPart.setObjectName(objx);
							
							String startx = fx.getAttribute("start");
							if(startx != null && !startx.equals("")){
								isoPart.setStart(Integer.parseInt(startx));
							}
							
							String endx = fx.getAttribute("end");
							if(endx != null && !endx.equals("")){
								isoPart.setEnd(Integer.parseInt(endx));
							}
							
							isoParts.add(isoPart);
						}
						
						outx.setIsoParts(isoParts);
					}
					
					//looping of output in one iso8583 bit
					String loopPart = f.getAttribute("loop");
					if(loopPart != null && loopPart.equalsIgnoreCase("true")){
						
						NodeList isopartNode = f.getElementsByTagName("part");
						List<IsoPart> isoParts = new ArrayList<IsoPart>();
						for (int l = 0; l < isopartNode.getLength(); l++) {
							Element fx = (Element)isopartNode.item(l);
							IsoPart isoPart = new IsoPart();
														
							String objx = fx.getAttribute("object");
							isoPart.setObjectName(objx);
							
							String startx = fx.getAttribute("start");
							if(startx != null && !startx.equals("")){
								isoPart.setStart(Integer.parseInt(startx));
							}
							
							String endx = fx.getAttribute("end");
							if(endx != null && !endx.equals("")){
								isoPart.setEnd(Integer.parseInt(endx));
							}
							
							isoParts.add(isoPart);
						}
						
						String startLoop = f.getAttribute("start");
						outx.setStartLoop(Integer.parseInt(startLoop));
						outx.setLoop(true);
						outx.setIsoParts(isoParts);
					}
					
					outputs.put(num, outx);
				}
				transaction.setOutputs(outputs);
			}
			
			transactions.put(transaction.getPcode(), transaction);
		}
		
		
	}
}
