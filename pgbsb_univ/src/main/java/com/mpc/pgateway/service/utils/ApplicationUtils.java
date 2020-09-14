package com.mpc.pgateway.service.utils;

import java.io.File;
import java.util.Enumeration;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

/***
 * @author yovi.putra
 */
public class ApplicationUtils {
public static Properties properties = System.getProperties();
	
	public static String getAppVersion() {
		String version = "";
		
		try {
			File pomFile = ApplicationUtils.getPom();
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder;
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document doc = documentBuilder.parse(pomFile);
			doc.getDocumentElement().normalize();
			version = doc.getElementsByTagName("version").item(0).getTextContent();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return version;
	}
	
	public static void displaySystem() {
		Enumeration<Object> enumeration = properties.keys();
		for (int i = 0; i < properties.size(); i++) {
		    Object obj = enumeration.nextElement();
		    System.out.println(String.format("| %-45s\t| %s",obj,System.getProperty(obj.toString())));
		}
	}
	
	public static String getAppName() {
		String tagFile = "file:";
		String webapps = "/webapps/";
		String resource = ApplicationUtils.class.getResource("/").toString();
		String home     = properties.getProperty("catalina.home");
		resource = resource.substring(home.length()+tagFile.length()+webapps.length());
		int idx = resource.indexOf("/");
		return resource.substring(0, idx < 0 ? 0 : idx);
	}
	
	private static File getPom() {
		File pomFile = ApplicationUtils.getFiles(
				new File(properties.getProperty("catalina.home") 
						+ "/webapps/"+ApplicationUtils.getAppName()
						+ "/META-INF/"));
		return pomFile;
	}
	
	private static File getFiles(File dir){
		for(File f : dir.listFiles()) {
			if(f.isDirectory()) {
				return getFiles(f);
			}else if(f.getName().equals("pom.xml")){
				return f;
			}
		}
		return null;
	}
}
