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
package org.apache.openejb.activemq;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;

import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.apache.openejb.jee.MessageDrivenBean;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Configuration;
import org.apache.openejb.testing.Module;
import org.apache.openejb.testng.PropertiesBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ApplicationComposer.class)
public class AMQXASupportTest {
    private static final String TEXT = "foo";
    @Resource(name = "target")
    private Queue destination;
    @Resource(name = "xaCf")
    private XAConnectionFactory xacf;
    @Resource(name = "cf")
    private ConnectionFactory cf;

    @Configuration
    public Properties config() {
        return new PropertiesBuilder()

        .p("amq", "new://Resource?type=ActiveMQResourceAdapter").p("amq.DataSource", "")
                .p("amq.BrokerXmlConfig", "broker:(vm://localhost)")

        .p("target", "new://Resource?type=Queue")

        .p("mdbs", "new://Container?type=MESSAGE").p("mdbs.ResourceAdapter", "amq")

        .p("cf", "new://Resource?type=" + ConnectionFactory.class.getName()).p("cf.ResourceAdapter", "amq")

        .p("xaCf", "new://Resource?class-name=" + ActiveMQXAConnectionFactory.class.getName())
                .p("xaCf.BrokerURL", "vm://localhost")

        .build();
    }

    @Module
    public MessageDrivenBean jar() {
        return new MessageDrivenBean(Listener.class);
    }

    @Before
    public void resetLatch() {
        Listener.reset();
    }

    @Test
    public void standardCode() throws Exception {
        assertNotNull(cf);

        final Connection connection = cf.createConnection();
        testConnection(connection);
    }

    @Test
    public void xaCode() throws Exception {
        assertNotNull(xacf);

        final Connection connection = xacf.createXAConnection();
        assertThat(connection, instanceOf(XAConnection.class));
        testConnection(connection);
    }

    private void testConnection(final Connection connection) throws JMSException, InterruptedException {
        try {
            final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            final MessageProducer producer = session.createProducer(destination);
            producer.send(session.createTextMessage(TEXT));
            assertTrue(Listener.sync());
        } finally {
            try {
                connection.close();
            } catch (final JMSException e) {
                //no-op
            }
        }
    }

    @MessageDriven(activationConfig = {
            @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
            @ActivationConfigProperty(propertyName = "destination", propertyValue = "target") })
    public static class Listener implements MessageListener {
        public static CountDownLatch latch;
        public static boolean ok = false;

        public static void reset() {
            latch = new CountDownLatch(1);
            ok = false;
        }

        public static boolean sync() throws InterruptedException {
            latch.await(1, TimeUnit.MINUTES);
            return ok;
        }

        @Override
        public void onMessage(final Message message) {
            try {
                try {
                    ok = TextMessage.class.isInstance(message)
                            && TEXT.equals(TextMessage.class.cast(message).getText());
                } catch (final JMSException e) {
                    // no-op
                }
            } finally {
                latch.countDown();
            }
        }
    }
}
