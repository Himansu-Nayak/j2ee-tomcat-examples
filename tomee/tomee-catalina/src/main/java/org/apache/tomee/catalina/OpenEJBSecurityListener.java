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
package org.apache.tomee.catalina;

import java.io.IOException;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;

import org.apache.catalina.connector.Request;

public class OpenEJBSecurityListener implements AsyncListener {
    public static final ThreadLocal<Request> requests = new ThreadLocal<>();

    private TomcatSecurityService securityService;
    private Object oldState;
    private Request request;

    public OpenEJBSecurityListener(final TomcatSecurityService service, final Request req) {
        securityService = service;
        request = req;
    }

    @Override
    public void onComplete(final AsyncEvent asyncEvent) throws IOException {
        exit();
    }

    @Override
    public void onError(final AsyncEvent asyncEvent) throws IOException {
        exit();
    }

    @Override
    public void onStartAsync(final AsyncEvent asyncEvent) throws IOException {
        asyncEvent.getAsyncContext().addListener(this); // super vicious isnt it? that's in servlet spec, start != end events.
        enter();
    }

    @Override
    public void onTimeout(final AsyncEvent asyncEvent) throws IOException {
        exit();
    }

    public void enter() {
        requests.set(request);
        if (securityService != null && request.getWrapper() != null) {
            oldState = securityService.enterWebApp(request.getWrapper().getRealm(), request.getPrincipal(),
                    request.getWrapper().getRunAs());
        }
    }

    public void exit() {
        requests.remove();
        if (securityService != null) {
            securityService.exitWebApp(oldState);
        }
    }
}
