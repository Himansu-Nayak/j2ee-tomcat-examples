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
 * connector_1_6.xsd
 * <p/>
 * <p>
 * Java class for authentication-mechanismType complex type.
 * <p/>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * 
 * <pre>
 * &lt;complexType name="authentication-mechanismType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://java.sun.com/xml/ns/javaee}descriptionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="authentication-mechanism-type" type="{http://java.sun.com/xml/ns/javaee}xsdStringType"/>
 *         &lt;element name="credential-interface" type="{http://java.sun.com/xml/ns/javaee}credential-interfaceType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authentication-mechanismType", propOrder = { "descriptions", "authenticationMechanismType",
        "credentialInterface" })
public class AuthenticationMechanism {

    @XmlTransient
    protected TextMap description = new TextMap();
    @XmlElement(name = "authentication-mechanism-type", required = true)
    protected String authenticationMechanismType;
    @XmlElement(name = "credential-interface", required = true)
    protected String credentialInterface;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;

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

    public String getAuthenticationMechanismType() {
        return authenticationMechanismType;
    }

    public void setAuthenticationMechanismType(final String value) {
        this.authenticationMechanismType = value;
    }

    public String getCredentialInterface() {
        return credentialInterface;
    }

    public void setCredentialInterface(final String value) {
        this.credentialInterface = value;
    }

    public String getId() {
        return id;
    }

    public void setId(final String value) {
        this.id = value;
    }

}
