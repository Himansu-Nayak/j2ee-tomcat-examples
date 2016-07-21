/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jug.routing;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

@WebFilter(displayName = "routing-filter", urlPatterns = { "/*" })
public class RoutingFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(RoutingFilter.class.getName());
    private static final AtomicInteger COUNTER = new AtomicInteger();

    @Resource(name = "ClientRouter", type = PollingRouter.class)
    private PollingRouter router;

    @Inject
    private DataSourceInitializer init;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        init.init();
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
            final FilterChain filterChain) throws IOException, ServletException {
        String client = servletRequest.getParameter("client");
        if (client == null) {
            client = getRandomClient();
        }
        LOGGER.info("using client " + client);
        router.setDataSource(client);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            router.clear();
        }
    }

    private String getRandomClient() {
        return "client" + (1 + COUNTER.getAndIncrement() % 2); // 2 clients
    }

    @Override
    public void destroy() {
        // no-op
    }
}
