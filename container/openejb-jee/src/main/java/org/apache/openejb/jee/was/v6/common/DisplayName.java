/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.openejb.jee.was.v6.common;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

import org.apache.openejb.jee.was.v6.xmi.Extension;

/**
 * @since J2EE1.4 The display-name type contains a short name that is intended to be displayed by tools. It is used by
 *        display-name elements. The display name need not be unique.
 *        <p/>
 *        Example:
 *        <p/>
 *        ...
 *        <p/>
 * 
 *        <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;display-name xmlns:com.ibm.etools.j2ee.common="common.xmi" xmlns:com.ibm.etools.webservice.wsclient="webservice_client.xmi" xmlns:jaxb="http://java.sun.com/xml/ns/jaxb" xmlns:org.eclipse.jem.java="java.xmi" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xml:lang="en"&gt;
 *
 * 					Employee Self Service
 *
 * 				&lt;/display-name&gt;
 *        </pre>
 *        <p/>
 *        <p/>
 *        The value of the xml:lang attribute is "en" (English) by default.
 *        <p/>
 *        <p/>
 *        <p/>
 *        Java class for DisplayName complex type.
 *        <p/>
 *        <p/>
 *        The following schema fragment specifies the expected content contained within this class.
 *        <p/>
 * 
 *        <pre>
 * &lt;complexType name="DisplayName">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.omg.org/XMI}Extension"/>
 *       &lt;/choice>
 *       &lt;attGroup ref="{http://www.omg.org/XMI}ObjectAttribs"/>
 *       &lt;attribute name="lang" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute ref="{http://www.omg.org/XMI}id"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 *        </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DisplayName", propOrder = { "extensions" })
public class DisplayName {

    @XmlElement(name = "Extension", namespace = "http://www.omg.org/XMI")
    protected List<Extension> extensions;
    @XmlAttribute
    protected String lang;
    @XmlAttribute
    protected String value;
    @XmlAttribute(namespace = "http://www.omg.org/XMI")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;
    @XmlAttribute(namespace = "http://www.omg.org/XMI")
    protected QName type;
    @XmlAttribute(namespace = "http://www.omg.org/XMI")
    protected String version;
    @XmlAttribute
    protected String href;
    @XmlAttribute(namespace = "http://www.omg.org/XMI")
    @XmlIDREF
    protected Object idref;
    @XmlAttribute(namespace = "http://www.omg.org/XMI")
    protected String label;
    @XmlAttribute(namespace = "http://www.omg.org/XMI")
    protected String uuid;

    /**
     * Gets the value of the extensions property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the extensions property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <p/>
     * 
     * <pre>
     * getExtensions().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list {@link Extension }
     */
    public List<Extension> getExtensions() {
        if (extensions == null) {
            extensions = new ArrayList<Extension>();
        }
        return this.extensions;
    }

    /**
     * Gets the value of the lang property.
     *
     * @return possible object is {@link String }
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     *
     * @param value
     *            allowed object is {@link String }
     */
    public void setLang(final String value) {
        this.lang = value;
    }

    /**
     * Gets the value of the value property.
     *
     * @return possible object is {@link String }
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value
     *            allowed object is {@link String }
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *            allowed object is {@link String }
     */
    public void setId(final String value) {
        this.id = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is {@link QName }
     */
    public QName getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *            allowed object is {@link QName }
     */
    public void setType(final QName value) {
        this.type = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return possible object is {@link String }
     */
    public String getVersion() {
        if (version == null) {
            return "2.0";
        } else {
            return version;
        }
    }

    /**
     * Sets the value of the version property.
     *
     * @param value
     *            allowed object is {@link String }
     */
    public void setVersion(final String value) {
        this.version = value;
    }

    /**
     * Gets the value of the href property.
     *
     * @return possible object is {@link String }
     */
    public String getHref() {
        return href;
    }

    /**
     * Sets the value of the href property.
     *
     * @param value
     *            allowed object is {@link String }
     */
    public void setHref(final String value) {
        this.href = value;
    }

    /**
     * Gets the value of the idref property.
     *
     * @return possible object is {@link Object }
     */
    public Object getIdref() {
        return idref;
    }

    /**
     * Sets the value of the idref property.
     *
     * @param value
     *            allowed object is {@link Object }
     */
    public void setIdref(final Object value) {
        this.idref = value;
    }

    /**
     * Gets the value of the label property.
     *
     * @return possible object is {@link String }
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     *
     * @param value
     *            allowed object is {@link String }
     */
    public void setLabel(final String value) {
        this.label = value;
    }

    /**
     * Gets the value of the uuid property.
     *
     * @return possible object is {@link String }
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     *
     * @param value
     *            allowed object is {@link String }
     */
    public void setUuid(final String value) {
        this.uuid = value;
    }

}
