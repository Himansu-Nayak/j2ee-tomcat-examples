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

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * web-facesconfig_2_0.xsd
 * <p/>
 * <p>
 * Java class for faces-config-supported-localeType complex type.
 * <p/>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * 
 * <pre>
 * &lt;complexType name="faces-config-supported-localeType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://java.sun.com/xml/ns/javaee>faces-config-localeType">
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "faces-config-supported-localeType", propOrder = { "value" })
public class FacesSupportedLocale {
    //TODO should be String, and class extend FacesLocale??
    @XmlValue
    protected FacesLocale value;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected java.lang.String id;

    /**
     * The localeType defines valid locale defined by ISO-639-1 and ISO-3166.
     *
     * @return possible object is {@link FacesLocale }
     */
    public FacesLocale getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value
     *            allowed object is {@link FacesLocale }
     */
    public void setValue(final FacesLocale value) {
        this.value = value;
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
