//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.soapbinding.jaxb;


/**
 * Java content class for ProviderType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/lib-arch-soap-binding.xsd line 84)
 * <p>
 * <pre>
 * &lt;complexType name="ProviderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="affiliationID" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="providerID" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute ref="{http://schemas.xmlsoap.org/soap/envelope/}actor"/>
 *       &lt;attribute ref="{http://schemas.xmlsoap.org/soap/envelope/}mustUnderstand"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ProviderType {


    /**
     * Gets the value of the affiliationID property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getAffiliationID();

    /**
     * Sets the value of the affiliationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setAffiliationID(java.lang.String value);

    /**
     * Gets the value of the mustUnderstand property.
     * 
     */
    boolean isMustUnderstand();

    /**
     * Sets the value of the mustUnderstand property.
     * 
     */
    void setMustUnderstand(boolean value);

    /**
     * Gets the value of the actor property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getActor();

    /**
     * Sets the value of the actor property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setActor(java.lang.String value);

    /**
     * Gets the value of the providerID property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getProviderID();

    /**
     * Sets the value of the providerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setProviderID(java.lang.String value);

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getId();

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setId(java.lang.String value);

}
