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
@XmlRootElement(name = "MessageDrivenContainer")
public class MessageDrivenContainerBuilder extends Container {

    @XmlAttribute
    private String resourceAdapter = "Default JMS Resource Adapter";
    @XmlAttribute
    private String messageListenerInterface = "javax.jms.MessageListener";
    @XmlAttribute
    private String activationSpecClass = "org.apache.activemq.ra.ActiveMQActivationSpec";
    @XmlAttribute
    private int instanceLimit = 10;

    public MessageDrivenContainerBuilder() {
        setClassName("org.apache.openejb.core.mdb.MdbContainer");
        setType("MESSAGE");
        setId("MessageDrivenContainer");

        setConstructor(
                "id, securityService, resourceAdapter, messageListenerInterface, activationSpecClass, instanceLimit");

    }

    public MessageDrivenContainerBuilder id(final String id) {
        setId(id);
        return this;
    }

    public MessageDrivenContainerBuilder withResourceAdapter(final String resourceAdapter) {
        this.resourceAdapter = resourceAdapter;
        return this;
    }

    public String getResourceAdapter() {
        return resourceAdapter;
    }

    public void setResourceAdapter(final String resourceAdapter) {
        this.resourceAdapter = resourceAdapter;
    }

    public MessageDrivenContainerBuilder withMessageListenerInterface(final String messageListenerInterface) {
        this.messageListenerInterface = messageListenerInterface;
        return this;
    }

    public String getMessageListenerInterface() {
        return messageListenerInterface;
    }

    public void setMessageListenerInterface(final String messageListenerInterface) {
        this.messageListenerInterface = messageListenerInterface;
    }

    public MessageDrivenContainerBuilder withActivationSpecClass(final String activationSpecClass) {
        this.activationSpecClass = activationSpecClass;
        return this;
    }

    public String getActivationSpecClass() {
        return activationSpecClass;
    }

    public void setActivationSpecClass(final String activationSpecClass) {
        this.activationSpecClass = activationSpecClass;
    }

    public MessageDrivenContainerBuilder withInstanceLimit(final int instanceLimit) {
        this.instanceLimit = instanceLimit;
        return this;
    }

    public int getInstanceLimit() {
        return instanceLimit;
    }

    public void setInstanceLimit(final int instanceLimit) {
        this.instanceLimit = instanceLimit;
    }

    public Properties getProperties() {
        return Builders.getProperties(this);
    }

}
