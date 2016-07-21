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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * web-common_3_0.xsd
 * <p/>
 * <p>
 * Java class for security-constraintType complex type.
 * <p/>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * 
 * <pre>
 * &lt;complexType name="security-constraintType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="display-name" type="{http://java.sun.com/xml/ns/javaee}display-nameType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="web-resource-collection" type="{http://java.sun.com/xml/ns/javaee}web-resource-collectionType" maxOccurs="unbounded"/>
 *         &lt;element name="auth-constraint" type="{http://java.sun.com/xml/ns/javaee}auth-constraintType" minOccurs="0"/>
 *         &lt;element name="user-data-constraint" type="{http://java.sun.com/xml/ns/javaee}user-data-constraintType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "security-constraintType", propOrder = { "displayName", "webResourceCollection", "authConstraint",
        "userDataConstraint" })
public class SecurityConstraint {

    @XmlElement(name = "display-name")
    protected List<String> displayName;
    @XmlElement(name = "web-resource-collection", required = true)
    protected List<WebResourceCollection> webResourceCollection;
    @XmlElement(name = "auth-constraint")
    protected AuthConstraint authConstraint;
    @XmlElement(name = "user-data-constraint")
    protected UserDataConstraint userDataConstraint;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;

    public List<String> getDisplayName() {
        if (displayName == null) {
            displayName = new ArrayList<String>();
        }
        return this.displayName;
    }

    public List<WebResourceCollection> getWebResourceCollection() {
        if (webResourceCollection == null) {
            webResourceCollection = new ArrayList<WebResourceCollection>();
        }
        return this.webResourceCollection;
    }

    public AuthConstraint getAuthConstraint() {
        return authConstraint;
    }

    public void setAuthConstraint(final AuthConstraint value) {
        this.authConstraint = value;
    }

    public UserDataConstraint getUserDataConstraint() {
        return userDataConstraint;
    }

    public void setUserDataConstraint(final UserDataConstraint value) {
        this.userDataConstraint = value;
    }

    public String getId() {
        return id;
    }

    public void setId(final String value) {
        this.id = value;
    }

}
