/**
 *
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
package org.apache.openejb.config;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.apache.openejb.OpenEJB;
import org.apache.openejb.assembler.classic.*;
import org.apache.openejb.core.ivm.naming.InitContextFactory;
import org.apache.openejb.jee.*;

/**
 * @version $Revision$ $Date$
 */
public class ModuleNameInjectionTest extends TestCase {

    public void testInjections() throws Exception {
        final InitialContext ctx = new InitialContext();

        final Object object = ctx.lookup("WidgetBeanLocal");

        assertTrue("instanceof widget", object instanceof Widget);

        final Widget widget = (Widget) object;

        assertEquals("myApp", widget.getAppName());
        assertEquals("myEjbModule", widget.getModuleName());
    }

    protected void setUp() throws Exception {
        super.setUp();

        System.setProperty(javax.naming.Context.INITIAL_CONTEXT_FACTORY, InitContextFactory.class.getName());

        final ConfigurationFactory config = new ConfigurationFactory();
        final Assembler assembler = new Assembler();

        assembler.createProxyFactory(config.configureService(ProxyFactoryInfo.class));
        assembler.createTransactionManager(config.configureService(TransactionServiceInfo.class));
        assembler.createSecurityService(config.configureService(SecurityServiceInfo.class));

        // containers
        final StatelessSessionContainerInfo statelessContainerInfo = config
                .configureService(StatelessSessionContainerInfo.class);
        statelessContainerInfo.properties.setProperty("TimeOut", "10");
        statelessContainerInfo.properties.setProperty("MaxSize", "0");
        statelessContainerInfo.properties.setProperty("StrictPooling", "false");
        assembler.createContainer(statelessContainerInfo);

        // Setup the descriptor information

        final StatelessBean bean = new StatelessBean(WidgetBean.class);
        bean.addBusinessLocal(Widget.class.getName());
        bean.addBusinessRemote(RemoteWidget.class.getName());

        final EjbJar ejbJar = new EjbJar();
        ejbJar.setModuleName("myEjbModule");
        ejbJar.addEnterpriseBean(bean);

        EnvEntry entry;

        entry = new EnvEntry("moduleName", (String) null, null);
        entry.setLookupName("java:module/ModuleName");
        entry.getInjectionTarget().add((new InjectionTarget(WidgetBean.class.getName(), "moduleName")));
        bean.getEnvEntry().add(entry);

        entry = new EnvEntry("appName", (String) null, null);
        entry.setLookupName("java:app/AppName");
        entry.getInjectionTarget().add((new InjectionTarget(WidgetBean.class.getName(), "appName")));
        bean.getEnvEntry().add(entry);

        final AppModule app = new AppModule(this.getClass().getClassLoader(), "test-app", new Application("myApp"),
                false);
        app.getEjbModules().add(new EjbModule(ejbJar));

        assembler.createApplication(config.configureApplication(app));
    }

    @Override
    protected void tearDown() throws Exception {
        OpenEJB.destroy();
    }

    public static interface Widget {
        String getModuleName();

        String getAppName();
    }

    public static interface RemoteWidget extends Widget {
    }

    @Stateless
    public static class WidgetBean implements Widget, RemoteWidget {

        private SessionContext sessionContext;

        @Resource
        private String appName;

        @Resource
        private String moduleName;

        public String getAppName() {
            return appName;
        }

        public String getModuleName() {
            return moduleName;
        }
    }
}
