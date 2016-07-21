/**
 *
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

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * ejb-jar_3_1.xsd
 * <p/>
 * <p>
 * Java class for cmr-fieldType complex type.
 * <p/>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * 
 * <pre>
 * &lt;complexType name="cmr-fieldType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://java.sun.com/xml/ns/javaee}descriptionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="cmr-field-name" type="{http://java.sun.com/xml/ns/javaee}string"/>
 *         &lt;element name="cmr-field-type" type="{http://java.sun.com/xml/ns/javaee}cmr-field-typeType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cmr-fieldType", propOrder = { "descriptions", "cmrFieldName", "cmrFieldType" })
public class CmrField {

    @XmlTransient
    protected TextMap description = new TextMap();
    @XmlElement(name = "cmr-field-name", required = true)
    protected String cmrFieldName;
    @XmlElement(name = "cmr-field-type")
    protected CmrFieldType cmrFieldType;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;

    public CmrField() {
    }

    public CmrField(final String cmrFieldName, final CmrFieldType cmrFieldType) {
        this.cmrFieldName = cmrFieldName;
        this.cmrFieldType = cmrFieldType;
    }

    @XmlElement(name = "description", required = true)
    public Text[] getDescriptions() {
        return description.toArray();
    }

    public void setDescriptions(final Text[] text) {
        description.set(text);
    }

    public String getDescription() {
        return description.get();
    }

    public String getCmrFieldName() {
        return cmrFieldName;
    }

    public void setCmrFieldName(final String value) {
        this.cmrFieldName = value;
    }

    public CmrFieldType getCmrFieldType() {
        return cmrFieldType;
    }

    public void setCmrFieldType(final CmrFieldType value) {
        this.cmrFieldType = value;
    }

    public String getId() {
        return id;
    }

    public void setId(final String value) {
        this.id = value;
    }

}
