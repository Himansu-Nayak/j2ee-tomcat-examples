/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.openejb.resource.activemq.jms2;

import javax.jms.JMSException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.security.auth.Subject;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ra.ActiveMQConnectionRequestInfo;
import org.apache.activemq.ra.ActiveMQManagedConnectionFactory;
import org.apache.activemq.ra.MessageActivationSpec;
import org.apache.activemq.ra.SimpleConnectionManager;

public class TomEEManagedConnectionFactory extends ActiveMQManagedConnectionFactory {
    @Override
    public Object createConnectionFactory(final ConnectionManager manager) throws ResourceException {
        return new TomEERAConnectionFactory(this, manager, getInfo());
    }

    @Override
    public Object createConnectionFactory() throws ResourceException {
        return createConnectionFactory(new SimpleConnectionManager());
    }

    @Override
    protected ActiveMQConnectionFactory createConnectionFactory(
            final ActiveMQConnectionRequestInfo connectionRequestInfo, final MessageActivationSpec activationSpec) {
        final TomEEConnectionFactory connectionFactory = new TomEEConnectionFactory();
        connectionRequestInfo.configure(connectionFactory, activationSpec);
        return connectionFactory;
    }

    @Override
    public ManagedConnection createManagedConnection(final Subject subject,
            final ConnectionRequestInfo connectionRequestInfo) throws ResourceException {
        final ActiveMQConnectionRequestInfo amqInfo;
        if (ActiveMQConnectionRequestInfo.class.isInstance(connectionRequestInfo)) {
            amqInfo = ActiveMQConnectionRequestInfo.class.cast(connectionRequestInfo);
        } else {
            amqInfo = getInfo();
        }
        try {
            return new TomEEManagedConnection(subject, makeConnection(amqInfo), amqInfo);
        } catch (final JMSException e) {
            throw new ResourceException("Could not create connection.", e);
        }
    }

    @Override
    public boolean equals(final Object object) {
        return !(object == null || !getClass().isInstance(object))
                && ((ActiveMQManagedConnectionFactory) object).getInfo().equals(getInfo());
    }
}
