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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * connector_1_6.xsd
 * <p/>
 * <p>
 * Java class for transaction-supportType complex type.
 * <p/>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * 
 * <pre>
 * &lt;complexType name="transaction-supportType">
 *   &lt;simpleContent>
 *     &lt;restriction base="&lt;http://java.sun.com/xml/ns/javaee>string">
 *     &lt;/restriction>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */

@XmlEnum
public enum TransactionSupportType {

    @XmlEnumValue("NoTransaction") NO_TRANSACTION("NoTransaction"), @XmlEnumValue("LocalTransaction") LOCAL_TRANSACTION(
            "LocalTransaction"), @XmlEnumValue("XATransaction") XA_TRANSACTION("XATransaction");
    private final String value;

    TransactionSupportType(final String v) {
        value = v;
    }

    public static TransactionSupportType fromValue(final String v) {
        for (final TransactionSupportType c : TransactionSupportType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

    public String value() {
        return value;
    }

}
