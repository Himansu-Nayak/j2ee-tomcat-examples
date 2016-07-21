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

package org.apache.openejb.jee.jpa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @Target({TYPE}) @Retention(RUNTIME) public @interface Inheritance { InheritanceType strategy() default SINGLE_TABLE;
 *                 }
 *                 <p/>
 *                 <p/>
 *                 <p/>
 *                 <p>
 *                 Java class for inheritance complex type.
 *                 <p/>
 *                 <p>
 *                 The following schema fragment specifies the expected content contained within this class.
 *                 <p/>
 * 
 *                 <pre>
 * &lt;complexType name="inheritance">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="strategy" type="{http://java.sun.com/xml/ns/persistence/orm}inheritance-type" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 *                 </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inheritance")
public class Inheritance {

    @XmlAttribute
    protected InheritanceType strategy;

    /**
     * Gets the value of the strategy property.
     *
     * @return possible object is {@link InheritanceType }
     */
    public InheritanceType getStrategy() {
        return strategy;
    }

    /**
     * Sets the value of the strategy property.
     *
     * @param value
     *            allowed object is {@link InheritanceType }
     */
    public void setStrategy(final InheritanceType value) {
        this.strategy = value;
    }

}
