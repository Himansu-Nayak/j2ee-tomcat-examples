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
package org.apache.openejb.assembler.classic;

import java.io.File;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.apache.openejb.OpenEJB;
import org.apache.openejb.config.ConfigurationFactory;
import org.apache.openejb.core.LocalInitialContextFactory;
import org.apache.openejb.loader.JarLocation;
import org.apache.openejb.test.stateful.AnnotatedFieldInjectionStatefulBean;
import org.apache.openejb.test.stateful.EncStatefulHome;
import org.apache.openejb.test.stateful.EncStatefulObject;
import org.apache.openejb.test.stateless.BasicStatelessBean;
import org.junit.AfterClass;

/**
 * @version $Rev$ $Date$
 */
public class RedeployTest extends TestCase {

    @AfterClass
    public static void afterClass() throws Exception {
        OpenEJB.destroy();
    }

    @Override
    protected void tearDown() throws Exception {
        OpenEJB.destroy();
    }

    public void test() throws Exception {
        // create reference to openejb itests
        final File file = JarLocation.jarLocation(BasicStatelessBean.class);
        if (!file.exists()) {
            throw new Exception("File not found: " + file);
        }

        // These two objects pretty much encompas all the EJB Container
        final ConfigurationFactory config = new ConfigurationFactory();
        final Assembler assembler = new Assembler();

        assembler.createTransactionManager(config.configureService(TransactionServiceInfo.class));
        assembler.createSecurityService(config.configureService(SecurityServiceInfo.class));

        createAndDestroy(assembler, config, file);
        createAndDestroy(assembler, config, file);
        createAndDestroy(assembler, config, file);
    }

    private void createAndDestroy(final Assembler assembler, final ConfigurationFactory config, final File file)
            throws Exception {

        // Deploy the file
        assembler.createApplication(config.configureApplication(file));

        // Lookup and execute a bean
        final Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, LocalInitialContextFactory.class.getName());
        final InitialContext ctx = new InitialContext(properties);
        final EncStatefulHome home = (EncStatefulHome) ctx
                .lookup(AnnotatedFieldInjectionStatefulBean.class.getSimpleName());
        final EncStatefulObject ejbObject = home.create("foo");
        ejbObject.lookupStringEntry();

        // Undeploy the file
        assembler.destroyApplication(file.getCanonicalPath());

        // Try and execute the bean after it's been undeployed -- should fail
        try {
            ejbObject.lookupStringEntry();
            fail("Proxy should no longer be valid");
        } catch (final Exception e) {
            // this should happen
        }

        try {
            ctx.lookup(AnnotatedFieldInjectionStatefulBean.class.getSimpleName());
            fail("JNDI References should have been cleaned up");
        } catch (final NamingException e) {
            // this also should happen
        }
    }
}
