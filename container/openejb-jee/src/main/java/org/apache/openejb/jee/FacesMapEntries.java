/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.openejb.jee;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * web-facesconfig_2_0.xsd
 * <p/>
 * <p>
 * Java class for faces-config-map-entriesType complex type.
 * <p/>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * 
 * <pre>
 * &lt;complexType name="faces-config-map-entriesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="key-class" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="value-class" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="map-entry" type="{http://java.sun.com/xml/ns/javaee}faces-config-map-entryType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "faces-config-map-entriesType", propOrder = { "keyClass", "valueClass", "mapEntry" })
public class FacesMapEntries {

    @XmlElement(name = "key-class")
    protected java.lang.String keyClass;
    @XmlElement(name = "value-class")
    protected java.lang.String valueClass;
    @XmlElement(name = "map-entry")
    protected List<FacesMapEntry> mapEntry;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected java.lang.String id;

    /**
     * Gets the value of the keyClass property.
     *
     * @return possible object is {@link java.lang.String }
     */
    public java.lang.String getKeyClass() {
        return keyClass;
    }

    /**
     * Sets the value of the keyClass property.
     *
     * @param value
     *            allowed object is {@link java.lang.String }
     */
    public void setKeyClass(final java.lang.String value) {
        this.keyClass = value;
    }

    /**
     * Gets the value of the valueClass property.
     *
     * @return possible object is {@link java.lang.String }
     */
    public java.lang.String getValueClass() {
        return valueClass;
    }

    /**
     * Sets the value of the valueClass property.
     *
     * @param value
     *            allowed object is {@link java.lang.String }
     */
    public void setValueClass(final java.lang.String value) {
        this.valueClass = value;
    }

    /**
     * Gets the value of the mapEntry property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the mapEntry property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getMapEntry().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list {@link FacesMapEntry }
     */
    public List<FacesMapEntry> getMapEntry() {
        if (mapEntry == null) {
            mapEntry = new ArrayList<FacesMapEntry>();
        }
        return this.mapEntry;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is {@link java.lang.String }
     */
    public java.lang.String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *            allowed object is {@link java.lang.String }
     */
    public void setId(final java.lang.String value) {
        this.id = value;
    }

}
