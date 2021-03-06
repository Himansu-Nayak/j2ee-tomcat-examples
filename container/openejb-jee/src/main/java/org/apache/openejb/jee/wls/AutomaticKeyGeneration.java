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
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.openejb.jee.wls;

import java.math.BigInteger;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>
 * Java class for automatic-key-generation complex type.
 * <p/>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * 
 * <pre>
 * &lt;complexType name="automatic-key-generation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="generator-type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="generator-name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="key-cache-size" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "automatic-key-generation", propOrder = { "generatorType", "generatorName", "keyCacheSize" })
public class AutomaticKeyGeneration {

    @XmlElement(name = "generator-type", required = true)
    protected String generatorType;
    @XmlElement(name = "generator-name")
    protected String generatorName;
    @XmlElement(name = "key-cache-size")
    protected BigInteger keyCacheSize;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;

    /**
     * Gets the value of the generatorType property.
     *
     * @return possible object is {@link String }
     */
    public String getGeneratorType() {
        return generatorType;
    }

    /**
     * Sets the value of the generatorType property.
     *
     * @param value
     *            allowed object is {@link String }
     */
    public void setGeneratorType(final String value) {
        this.generatorType = value;
    }

    /**
     * Gets the value of the generatorName property.
     *
     * @return possible object is {@link String }
     */
    public String getGeneratorName() {
        return generatorName;
    }

    /**
     * Sets the value of the generatorName property.
     *
     * @param value
     *            allowed object is {@link String }
     */
    public void setGeneratorName(final String value) {
        this.generatorName = value;
    }

    /**
     * Gets the value of the keyCacheSize property.
     *
     * @return possible object is {@link BigInteger }
     */
    public BigInteger getKeyCacheSize() {
        return keyCacheSize;
    }

    /**
     * Sets the value of the keyCacheSize property.
     *
     * @param value
     *            allowed object is {@link BigInteger }
     */
    public void setKeyCacheSize(final BigInteger value) {
        this.keyCacheSize = value;
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

}
