//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.03.23 at 03:29:45 PM ICT 
//


package id.ac.univpgri_palembang.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reversalResult" type="{http://ws.univpgri-palembang.ac.id/}reversalRespon" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "reversalResult"
})
@XmlRootElement(name = "reversalResponse")
public class ReversalResponse {

    protected ReversalRespon reversalResult;

    /**
     * Gets the value of the reversalResult property.
     * 
     * @return
     *     possible object is
     *     {@link ReversalRespon }
     *     
     */
    public ReversalRespon getReversalResult() {
        return reversalResult;
    }

    /**
     * Sets the value of the reversalResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReversalRespon }
     *     
     */
    public void setReversalResult(ReversalRespon value) {
        this.reversalResult = value;
    }

}
