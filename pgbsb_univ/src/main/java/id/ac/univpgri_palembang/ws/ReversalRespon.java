//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.03.23 at 03:29:45 PM ICT 
//


package id.ac.univpgri_palembang.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reversalRespon complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reversalRespon">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nomorPembayaran" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataMahasiswa" type="{http://ws.univpgri-palembang.ac.id/}Datamhs" minOccurs="0"/>
 *         &lt;element name="jumlahItem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemPembayaranReversal" type="{http://ws.univpgri-palembang.ac.id/}itemPembayaran" minOccurs="0"/>
 *         &lt;element name="totalNominal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="applicationfault" type="{http://ws.univpgri-palembang.ac.id/}applicationfault" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reversalRespon", propOrder = {
    "nomorPembayaran",
    "dataMahasiswa",
    "jumlahItem",
    "itemPembayaranReversal",
    "totalNominal",
    "applicationfault"
})
public class ReversalRespon {

    protected String nomorPembayaran;
    protected Datamhs dataMahasiswa;
    protected String jumlahItem;
    protected ItemPembayaran itemPembayaranReversal;
    protected String totalNominal;
    protected Applicationfault applicationfault;

    /**
     * Gets the value of the nomorPembayaran property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomorPembayaran() {
        return nomorPembayaran;
    }

    /**
     * Sets the value of the nomorPembayaran property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomorPembayaran(String value) {
        this.nomorPembayaran = value;
    }

    /**
     * Gets the value of the dataMahasiswa property.
     * 
     * @return
     *     possible object is
     *     {@link Datamhs }
     *     
     */
    public Datamhs getDataMahasiswa() {
        return dataMahasiswa;
    }

    /**
     * Sets the value of the dataMahasiswa property.
     * 
     * @param value
     *     allowed object is
     *     {@link Datamhs }
     *     
     */
    public void setDataMahasiswa(Datamhs value) {
        this.dataMahasiswa = value;
    }

    /**
     * Gets the value of the jumlahItem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJumlahItem() {
        return jumlahItem;
    }

    /**
     * Sets the value of the jumlahItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJumlahItem(String value) {
        this.jumlahItem = value;
    }

    /**
     * Gets the value of the itemPembayaranReversal property.
     * 
     * @return
     *     possible object is
     *     {@link ItemPembayaran }
     *     
     */
    public ItemPembayaran getItemPembayaranReversal() {
        return itemPembayaranReversal;
    }

    /**
     * Sets the value of the itemPembayaranReversal property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemPembayaran }
     *     
     */
    public void setItemPembayaranReversal(ItemPembayaran value) {
        this.itemPembayaranReversal = value;
    }

    /**
     * Gets the value of the totalNominal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalNominal() {
        return totalNominal;
    }

    /**
     * Sets the value of the totalNominal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalNominal(String value) {
        this.totalNominal = value;
    }

    /**
     * Gets the value of the applicationfault property.
     * 
     * @return
     *     possible object is
     *     {@link Applicationfault }
     *     
     */
    public Applicationfault getApplicationfault() {
        return applicationfault;
    }

    /**
     * Sets the value of the applicationfault property.
     * 
     * @param value
     *     allowed object is
     *     {@link Applicationfault }
     *     
     */
    public void setApplicationfault(Applicationfault value) {
        this.applicationfault = value;
    }

}
