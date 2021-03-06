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
 * ejb-jar_3_1.xsd
 * <p/>
 * <p>
 * Java class for method-permissionType complex type.
 * <p/>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * 
 * <pre>
 * &lt;complexType name="method-permissionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://java.sun.com/xml/ns/javaee}descriptionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="role-name" type="{http://java.sun.com/xml/ns/javaee}role-nameType" maxOccurs="unbounded"/>
 *           &lt;element name="unchecked" type="{http://java.sun.com/xml/ns/javaee}emptyType"/>
 *         &lt;/choice>
 *         &lt;element name="method" type="{http://java.sun.com/xml/ns/javaee}methodType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "method-permissionType", propOrder = { "descriptions", "roleName", "unchecked", "method" })
public class MethodPermission {

    @XmlTransient
    protected TextMap description = new TextMap();

    @XmlElement(name = "role-name", required = true)
    protected List<String> roleName;
    protected Empty unchecked;
    @XmlElement(required = true)
    protected List<Method> method;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;

    public MethodPermission() {
    }

    public MethodPermission(final String className, final String ejbName, final String methodName,
            final String... roles) {
        this(new Method(ejbName, className, methodName), roles);
    }

    public MethodPermission(final String ejbName, final java.lang.reflect.Method method, final String... roles) {
        this(new Method(ejbName, method), roles);
    }

    public MethodPermission(final Method method, final String... roles) {
        getMethod().add(method);
        for (final String role : roles) {
            getRoleName().add(role);
        }
    }

    public MethodPermission setUnchecked() {
        this.unchecked = new Empty();
        return this;
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

    public List<String> getRoleName() {
        if (roleName == null) {
            roleName = new ArrayList<String>();
        }
        return this.roleName;
    }

    public boolean getUnchecked() {
        return unchecked != null;
    }

    public void setUnchecked(final boolean b) {
        this.unchecked = (b) ? new Empty() : null;
    }

    public List<Method> getMethod() {
        if (method == null) {
            method = new ArrayList<Method>();
        }
        return this.method;
    }

    public String getId() {
        return id;
    }

    public void setId(final String value) {
        this.id = value;
    }

}
