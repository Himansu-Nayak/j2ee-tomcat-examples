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

package org.apache.openejb.config.typed.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.openejb.OpenEJBException;
import org.apache.openejb.assembler.classic.*;
import org.apache.openejb.config.ConfigurationFactory;
import org.apache.openejb.config.sys.*;

/**
 * @version $Rev$ $Date$
 */
@XmlRootElement(name = "ServerContext")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServerContext {

    @XmlTransient
    private final ConfigurationFactory factory = new ConfigurationFactory();
    @XmlTransient
    private final Assembler assembler = new Assembler();

    public void createTransactionManager(final TransactionManager service) throws OpenEJBException {
        final TransactionServiceInfo serviceInfo = factory.configureService(service, TransactionServiceInfo.class);
        assembler.createTransactionManager(serviceInfo);
    }

    public void createSecurityService(final SecurityService service) throws OpenEJBException {
        final SecurityServiceInfo serviceInfo = factory.configureService(service, SecurityServiceInfo.class);
        assembler.createSecurityService(serviceInfo);
    }

    public void createResource(final Resource service) throws OpenEJBException {
        final ResourceInfo serviceInfo = factory.configureService(service, ResourceInfo.class);
        assembler.createResource(serviceInfo);
    }

    public void createService(final Service service) throws OpenEJBException {
        final ServiceInfo serviceInfo = factory.configureService(service, ServiceInfo.class);
        assembler.createService(serviceInfo);
    }

    public void createContainer(final Container service) throws OpenEJBException {
        final ContainerInfo serviceInfo = factory.configureService(service, ContainerInfo.class);
        assembler.createContainer(serviceInfo);
    }

    public void createExternalContext(final JndiProvider service) throws OpenEJBException {
        final JndiContextInfo jndiContextInfo = factory.configureService(service, JndiContextInfo.class);
        assembler.createExternalContext(jndiContextInfo);
    }
}
