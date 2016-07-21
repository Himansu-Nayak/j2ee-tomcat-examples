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
package org.apache.openejb.core.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.security.auth.login.LoginException;

import org.apache.openejb.core.ThreadContext;
import org.apache.openejb.jee.EnterpriseBean;
import org.apache.openejb.jee.SingletonBean;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.loader.SystemInstance;
import org.apache.openejb.spi.SecurityService;
import org.apache.openejb.testing.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ApplicationComposer.class)
public class IsLoggedTest {
    @EJB
    private IsCallerInRoleBean bean;

    @Module
    public EnterpriseBean bean() {
        return new SingletonBean(IsCallerInRoleBean.class).localBean();
    }

    @Test
    public void isLogged() throws LoginException {
        final ThreadContext testContext = ThreadContext.getThreadContext();
        testContext.set(AbstractSecurityService.SecurityContext.class, null);

        final SecurityService securityService = SystemInstance.get().getComponent(SecurityService.class);
        final Object id = securityService.login("jonathan", "secret");
        securityService.associate(id);

        assertTrue(bean.isinRole("**"));
        assertFalse(bean.isinRole("whatever"));

        securityService.disassociate();
        securityService.logout(id);

        ThreadContext.enter(testContext);
    }

    @Test
    public void isNotLogged() {
        assertFalse(bean.isinRole("**"));
    }

    @Singleton
    public static class IsCallerInRoleBean {
        @Resource
        private SessionContext ctx;

        public boolean isinRole(final String role) {
            return ctx.isCallerInRole(role);
        }
    }
}
