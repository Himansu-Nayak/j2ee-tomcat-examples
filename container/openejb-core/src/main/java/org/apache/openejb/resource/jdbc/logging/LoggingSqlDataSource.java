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

package org.apache.openejb.resource.jdbc.logging;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import javax.sql.CommonDataSource;

import org.apache.openejb.resource.jdbc.DelegatableHandler;

public class LoggingSqlDataSource implements DelegatableHandler {
    private static final Class<?>[] INTERFACES = new Class<?>[] { Connection.class };

    private final CommonDataSource delegate;
    private final String[] packages;

    public LoggingSqlDataSource(final CommonDataSource ds, final String[] debugPackages) {
        delegate = ds;
        packages = debugPackages;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (Object.class == method.getDeclaringClass() && "toString".equals(method.getName())) {
            return "Logging[" + delegate.toString() + "]";
        }

        final Object result;
        try {
            result = method.invoke(delegate, args);
        } catch (final InvocationTargetException ite) {
            throw ite.getCause();
        }

        if ("getConnection".equals(method.getName())) {
            return Proxy.newProxyInstance(delegate.getClass().getClassLoader(), INTERFACES,
                    new LoggingSqlConnection((Connection) result, packages));
        }
        return result;
    }

    @Override
    public CommonDataSource getDelegate() {
        return delegate;
    }
}
