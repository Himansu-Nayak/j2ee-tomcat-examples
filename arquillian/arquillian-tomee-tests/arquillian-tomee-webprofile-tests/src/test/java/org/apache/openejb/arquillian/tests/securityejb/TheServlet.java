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
package org.apache.openejb.arquillian.tests.securityejb;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test")
public class TheServlet extends HttpServlet {
    @EJB
    private TheEJb ejb;

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String webIn = web(request);
        final String ejbIn = ejb();

        request.logout();

        final String webOut = web(request);
        final String ejbOut = ejb();

        response.getWriter().write(webIn + ejbIn + webOut + ejbOut);

    }

    private String web(final HttpServletRequest request) {
        String name = null;
        if (request.getUserPrincipal() != null) {
            name = request.getUserPrincipal().getName();
        }
        return name;
    }

    private String ejb() {
        String name = "";
        try {
            name = ejb.name();
        } catch (final Exception e) {
            // no-op
        }
        return name;
    }

}
