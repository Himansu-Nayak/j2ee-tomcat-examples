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
 * Java class for interceptor-orderType complex type.
 * <p/>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * 
 * <pre>
 * &lt;complexType name="interceptor-orderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="interceptor-class" type="{http://java.sun.com/xml/ns/javaee}fully-qualified-classType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "interceptor-orderType", propOrder = { "interceptorClass" })
public class InterceptorOrder {

    @XmlElement(name = "interceptor-class", required = true)
    protected List<String> interceptorClass;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;

    public List<String> getInterceptorClass() {
        if (interceptorClass == null) {
            interceptorClass = new ArrayList<String>();
        }
        return this.interceptorClass;
    }

    public void addInterceptorClass(final String className) {
        getInterceptorClass().add(className);
    }

    public String getId() {
        return id;
    }

    public void setId(final String value) {
        this.id = value;
    }

}
