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

import org.apache.openejb.config.sys.SecurityService;
import org.apache.openejb.config.typed.util.Builders;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SecurityService")
public class SecurityServiceBuilder extends SecurityService {

    @XmlAttribute
    private String defaultUser = "guest         ";

    public SecurityServiceBuilder() {
        setClassName("org.apache.openejb.core.security.SecurityServiceImpl");
        setType("SecurityService");
        setId("SecurityService");

    }

    public SecurityServiceBuilder id(final String id) {
        setId(id);
        return this;
    }

    public SecurityServiceBuilder withDefaultUser(final String defaultUser) {
        this.defaultUser = defaultUser;
        return this;
    }

    public String getDefaultUser() {
        return defaultUser;
    }

    public void setDefaultUser(final String defaultUser) {
        this.defaultUser = defaultUser;
    }

    public Properties getProperties() {
        return Builders.getProperties(this);
    }

}
