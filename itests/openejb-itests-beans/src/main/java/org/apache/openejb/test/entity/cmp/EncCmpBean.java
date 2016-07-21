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
package org.apache.openejb.test.entity.cmp;

import java.rmi.RemoteException;
import java.util.StringTokenizer;

import javax.ejb.EJBException;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import junit.framework.AssertionFailedError;

import org.apache.openejb.test.TestFailureException;
import org.apache.openejb.test.stateful.BasicStatefulBusinessLocal;
import org.apache.openejb.test.stateful.BasicStatefulBusinessRemote;
import org.apache.openejb.test.stateful.BasicStatefulHome;
import org.apache.openejb.test.stateful.BasicStatefulObject;
import org.apache.openejb.test.stateless.BasicStatelessBusinessLocal;
import org.apache.openejb.test.stateless.BasicStatelessBusinessRemote;
import org.apache.openejb.test.stateless.BasicStatelessHome;
import org.apache.openejb.test.stateless.BasicStatelessObject;
import org.junit.Assert;

public class EncCmpBean implements javax.ejb.EntityBean {
    private static int nextId;
    public int primaryKey;
    public String firstName;
    public String lastName;
    public EntityContext ejbContext;

    //=============================
    // Home interface methods
    //    

    /**
     * Maps to EncCmpHome.create
     *
     * @param name
     * @return
     * @throws javax.ejb.CreateException
     * @see EncCmpHome#create
     */
    public Integer ejbCreate(final String name) throws javax.ejb.CreateException {
        primaryKey = nextId++;
        final StringTokenizer st = new StringTokenizer(name, " ");
        firstName = st.nextToken();
        lastName = st.nextToken();
        return null;
    }

    public void ejbPostCreate(final String name) throws javax.ejb.CreateException {
    }

    //    
    // Home interface methods
    //=============================

    //=============================
    // Remote interface methods
    //    

    public void lookupEntityBean() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final BasicCmpHome home = (BasicCmpHome) javax.rmi.PortableRemoteObject
                        .narrow(ctx.lookup("java:comp/env/entity/cmp/beanReferences/cmp_entity"), BasicCmpHome.class);
                Assert.assertNotNull("The EJBHome looked up is null", home);

                final BasicCmpObject object = home.createObject("Enc Bean");
                Assert.assertNotNull("The EJBObject is null", object);
            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupStatefulBean() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final BasicStatefulHome home = (BasicStatefulHome) javax.rmi.PortableRemoteObject.narrow(
                        ctx.lookup("java:comp/env/entity/cmp/beanReferences/stateful"), BasicStatefulHome.class);
                Assert.assertNotNull("The EJBHome looked up is null", home);

                final BasicStatefulObject object = home.createObject("Enc Bean");
                Assert.assertNotNull("The EJBObject is null", object);
            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupStatelessBean() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final BasicStatelessHome home = (BasicStatelessHome) javax.rmi.PortableRemoteObject.narrow(
                        ctx.lookup("java:comp/env/entity/cmp/beanReferences/stateless"), BasicStatelessHome.class);
                Assert.assertNotNull("The EJBHome looked up is null", home);

                final BasicStatelessObject object = home.createObject();
                Assert.assertNotNull("The EJBObject is null", object);
            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupStatelessBusinessLocal() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final Object o = ctx.lookup("java:comp/env/entity/cmp/beanReferences/stateless-business-local");
                final BasicStatelessBusinessLocal object = (BasicStatelessBusinessLocal) o;
                Assert.assertNotNull("The EJB BusinessLocal is null", object);
            } catch (final Exception e) {
                e.printStackTrace();
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupStatelessBusinessRemote() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final BasicStatelessBusinessRemote object = (BasicStatelessBusinessRemote) javax.rmi.PortableRemoteObject
                        .narrow(ctx.lookup("java:comp/env/entity/cmp/beanReferences/stateless-business-remote"),
                                BasicStatelessBusinessRemote.class);
                Assert.assertNotNull("The EJB BusinessRemote is null", object);
            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupStatefulBusinessLocal() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final BasicStatefulBusinessLocal object = (BasicStatefulBusinessLocal) javax.rmi.PortableRemoteObject
                        .narrow(ctx.lookup("java:comp/env/entity/cmp/beanReferences/stateful-business-local"),
                                BasicStatefulBusinessLocal.class);
                Assert.assertNotNull("The EJB BusinessLocal is null", object);
            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupStatefulBusinessRemote() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final BasicStatefulBusinessRemote object = (BasicStatefulBusinessRemote) javax.rmi.PortableRemoteObject
                        .narrow(ctx.lookup("java:comp/env/entity/cmp/beanReferences/stateful-business-remote"),
                                BasicStatefulBusinessRemote.class);
                Assert.assertNotNull("The EJB BusinessRemote is null", object);
            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupStringEntry() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final String expected = new String("1");
                final String actual = (String) ctx.lookup("java:comp/env/entity/cmp/references/String");

                Assert.assertNotNull("The String looked up is null", actual);
                Assert.assertEquals(expected, actual);

            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupDoubleEntry() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final Double expected = new Double(1.0D);
                final Double actual = (Double) ctx.lookup("java:comp/env/entity/cmp/references/Double");

                Assert.assertNotNull("The Double looked up is null", actual);
                Assert.assertEquals(expected, actual);

            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupLongEntry() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final Long expected = new Long(1L);
                final Long actual = (Long) ctx.lookup("java:comp/env/entity/cmp/references/Long");

                Assert.assertNotNull("The Long looked up is null", actual);
                Assert.assertEquals(expected, actual);

            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupFloatEntry() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final Float expected = new Float(1.0F);
                final Float actual = (Float) ctx.lookup("java:comp/env/entity/cmp/references/Float");

                Assert.assertNotNull("The Float looked up is null", actual);
                Assert.assertEquals(expected, actual);

            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupIntegerEntry() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final Integer expected = new Integer(1);
                final Integer actual = (Integer) ctx.lookup("java:comp/env/entity/cmp/references/Integer");

                Assert.assertNotNull("The Integer looked up is null", actual);
                Assert.assertEquals(expected, actual);

            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupShortEntry() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final Short expected = new Short((short) 1);
                final Short actual = (Short) ctx.lookup("java:comp/env/entity/cmp/references/Short");

                Assert.assertNotNull("The Short looked up is null", actual);
                Assert.assertEquals(expected, actual);

            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupBooleanEntry() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final Boolean expected = new Boolean(true);
                final Boolean actual = (Boolean) ctx.lookup("java:comp/env/entity/cmp/references/Boolean");

                Assert.assertNotNull("The Boolean looked up is null", actual);
                Assert.assertEquals(expected, actual);

            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupByteEntry() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final Byte expected = new Byte((byte) 1);
                final Byte actual = (Byte) ctx.lookup("java:comp/env/entity/cmp/references/Byte");

                Assert.assertNotNull("The Byte looked up is null", actual);
                Assert.assertEquals(expected, actual);

            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupCharacterEntry() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);

                final Character expected = new Character('D');
                final Character actual = (Character) ctx.lookup("java:comp/env/entity/cmp/references/Character");

                Assert.assertNotNull("The Character looked up is null", actual);
                Assert.assertEquals(expected, actual);

            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupResource() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);
                final Object obj = ctx.lookup("java:comp/env/datasource");
                Assert.assertNotNull("The DataSource is null", obj);
                Assert.assertTrue("Not an instance of DataSource", obj instanceof DataSource);
            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupJMSConnectionFactory() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);
                Object obj = ctx.lookup("java:comp/env/jms");
                Assert.assertNotNull("The JMS ConnectionFactory is null", obj);
                Assert.assertTrue("Not an instance of ConnectionFactory", obj instanceof ConnectionFactory);
                final ConnectionFactory connectionFactory = (ConnectionFactory) obj;
                testJmsConnection(connectionFactory.createConnection());

                obj = ctx.lookup("java:comp/env/TopicCF");
                Assert.assertNotNull("The JMS TopicConnectionFactory is null", obj);
                Assert.assertTrue("Not an instance of TopicConnectionFactory", obj instanceof TopicConnectionFactory);
                final TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) obj;
                testJmsConnection(topicConnectionFactory.createConnection());

                obj = ctx.lookup("java:comp/env/QueueCF");
                Assert.assertNotNull("The JMS QueueConnectionFactory is null", obj);
                Assert.assertTrue("Not an instance of QueueConnectionFactory", obj instanceof QueueConnectionFactory);
                final QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) obj;
                testJmsConnection(queueConnectionFactory.createConnection());
            } catch (final Exception e) {
                e.printStackTrace();
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    private void testJmsConnection(final Connection connection) throws JMSException {
        final Session session = connection.createSession(false, Session.DUPS_OK_ACKNOWLEDGE);
        final Topic topic = session.createTopic("test");
        final MessageProducer producer = session.createProducer(topic);
        producer.send(session.createMessage());
        producer.close();
        session.close();
        connection.close();
    }

    public void lookupPersistenceUnit() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);
                final EntityManagerFactory emf = (EntityManagerFactory) ctx
                        .lookup("java:comp/env/persistence/TestUnit");
                Assert.assertNotNull("The EntityManagerFactory is null", emf);

            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    public void lookupPersistenceContext() throws TestFailureException {
        try {
            try {
                final InitialContext ctx = new InitialContext();
                Assert.assertNotNull("The InitialContext is null", ctx);
                final EntityManager em = (EntityManager) ctx.lookup("java:comp/env/persistence/TestContext");
                Assert.assertNotNull("The EntityManager is null", em);

                // call a do nothing method to assure entity manager actually exists
                em.getFlushMode();
            } catch (final Exception e) {
                Assert.fail("Received Exception " + e.getClass() + " : " + e.getMessage());
            }
        } catch (final AssertionFailedError afe) {
            throw new TestFailureException(afe);
        }
    }

    //
    // Remote interface methods
    //=============================

    //================================
    // EntityBean interface methods
    //    

    /**
     * A container invokes this method to instruct the instance to synchronize its state by loading it state from the
     * underlying database.
     */
    public void ejbLoad() throws EJBException, RemoteException {
    }

    /**
     * Set the associated entity context. The container invokes this method on an instance after the instance has been
     * created.
     */
    public void setEntityContext(final EntityContext ctx) throws EJBException, RemoteException {
        ejbContext = ctx;
    }

    /**
     * Unset the associated entity context. The container calls this method before removing the instance.
     */
    public void unsetEntityContext() throws EJBException, RemoteException {
    }

    /**
     * A container invokes this method to instruct the instance to synchronize its state by storing it to the underlying
     * database.
     */
    public void ejbStore() throws EJBException, RemoteException {
    }

    /**
     * A container invokes this method before it removes the EJB object that is currently associated with the instance.
     * This method is invoked when a client invokes a remove operation on the enterprise Bean's home interface or the
     * EJB object's remote interface. This method transitions the instance from the ready state to the pool of available
     * instances.
     */
    public void ejbRemove() throws RemoveException, EJBException, RemoteException {
    }

    /**
     * A container invokes this method when the instance is taken out of the pool of available instances to become
     * associated with a specific EJB object. This method transitions the instance to the ready state.
     */
    public void ejbActivate() throws EJBException, RemoteException {
    }

    /**
     * A container invokes this method on an instance before the instance becomes disassociated with a specific EJB
     * object. After this method completes, the container will place the instance into the pool of available instances.
     */
    public void ejbPassivate() throws EJBException, RemoteException {
    }
    //    
    // EntityBean interface methods
    //================================
}
