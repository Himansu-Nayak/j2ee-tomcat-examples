/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.openejb.jee.was.v6.java;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

import org.apache.openejb.jee.was.v6.ecore.EClassifier;

/**
 * Describes a Java Array type For multi-dimensional arrays, it is unlikely that the component type will be specified
 * directly. This would require instantiating a chain of component types such as
 * String[][][][]->String[][][]->String[][]->String[]->String.
 * <p/>
 * The component type relationship will be computed if the finalComponentType and array dimensions is specified.
 * <p/>
 * For this reason, the preferred way to create is through the JavaRefFactory factory method: createArrayType(JavaClass
 * finalComponentType, int dimensions)
 * <p/>
 * <p/>
 * <p/>
 * Java class for ArrayType complex type.
 * <p/>
 * <p/>
 * The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * 
 * <pre>
 * &lt;complexType name="ArrayType">
 *   &lt;complexContent>
 *     &lt;extension base="{java.xmi}JavaClass">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="componentType" type="{http://www.eclipse.org/emf/2002/Ecore}EClassifier"/>
 *       &lt;/choice>
 *       &lt;attribute name="arrayDimensions" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="componentType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayType", propOrder = { "componentTypes" })
public class ArrayType extends JavaClass {

    @XmlElement(name = "componentType")
    protected List<EClassifier> componentTypes;
    @XmlAttribute
    protected Integer arrayDimensions;
    @XmlAttribute
    protected String componentType;

    /**
     * Gets the value of the componentTypes property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the componentTypes property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <p/>
     * 
     * <pre>
     * getComponentTypes().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list {@link EClassifier }
     */
    public List<EClassifier> getComponentTypes() {
        if (componentTypes == null) {
            componentTypes = new ArrayList<EClassifier>();
        }
        return this.componentTypes;
    }

    /**
     * Gets the value of the arrayDimensions property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getArrayDimensions() {
        return arrayDimensions;
    }

    /**
     * Sets the value of the arrayDimensions property.
     *
     * @param value
     *            allowed object is {@link Integer }
     */
    public void setArrayDimensions(final Integer value) {
        this.arrayDimensions = value;
    }

    /**
     * Gets the value of the componentType property.
     *
     * @return possible object is {@link String }
     */
    public String getComponentType() {
        return componentType;
    }

    /**
     * Sets the value of the componentType property.
     *
     * @param value
     *            allowed object is {@link String }
     */
    public void setComponentType(final String value) {
        this.componentType = value;
    }

}
