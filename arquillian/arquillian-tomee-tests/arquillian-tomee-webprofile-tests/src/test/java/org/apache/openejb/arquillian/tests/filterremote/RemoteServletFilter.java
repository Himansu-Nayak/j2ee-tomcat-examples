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

package org.apache.openejb.arquillian.tests.filterremote;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.*;

import org.apache.openejb.arquillian.tests.TestRun;
import org.junit.Assert;

public class RemoteServletFilter implements Filter {

    @EJB
    private CompanyRemote remoteCompany;

    public void init(FilterConfig config) {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        TestRun.run(req, resp, this);
    }

    public void testEjb() {
        Assert.assertNotNull(remoteCompany);
        remoteCompany.employ("test");
    }

}