/*
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

package org.apache.openejb.config.typed;

import java.util.Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.openejb.config.sys.Container;
import org.apache.openejb.config.typed.util.Builders;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BmpEntityContainer")
public class BmpEntityContainerBuilder extends Container {

    @XmlAttribute
    private int poolSize = 10;

    public BmpEntityContainerBuilder() {
        setClassName("org.apache.openejb.core.entity.EntityContainer");
        setType("BMP_ENTITY");
        setId("BmpEntityContainer");

        setConstructor("id, securityService, poolSize");

    }

    public BmpEntityContainerBuilder id(final String id) {
        setId(id);
        return this;
    }

    public BmpEntityContainerBuilder withPoolSize(final int poolSize) {
        this.poolSize = poolSize;
        return this;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(final int poolSize) {
        this.poolSize = poolSize;
    }

    public Properties getProperties() {
        return Builders.getProperties(this);
    }

}
