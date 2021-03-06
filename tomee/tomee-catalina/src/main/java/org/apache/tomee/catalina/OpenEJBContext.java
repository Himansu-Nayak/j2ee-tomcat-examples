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

import java.util.Hashtable;

import javax.naming.*;

import org.apache.naming.ContextBindings;
import org.apache.openejb.assembler.DeployerEjb;
import org.apache.openejb.core.ThreadContext;

/**
 * {@link Context} implementation for using it via Tomcat integration.
 *
 * @version $Rev$ $Date$
 */
public class OpenEJBContext implements Context {

    /**
     * {@inheritDoc}
     */
    public Object lookup(final Name name) throws NamingException {
        return getThreadContext().lookup(name);
    }

    /**
     * {@inheritDoc}
     */
    public Object lookup(final String name) throws NamingException {
        return getThreadContext().lookup(name);
    }

    /**
     * {@inheritDoc}
     */
    public void bind(final Name name, final Object obj) throws NamingException {
        getThreadContext().bind(name, obj);
    }

    /**
     * {@inheritDoc}
     */
    public void bind(final String name, final Object obj) throws NamingException {
        getThreadContext().bind(name, obj);
    }

    /**
     * {@inheritDoc}
     */
    public void rebind(final Name name, final Object obj) throws NamingException {
        getThreadContext().rebind(name, obj);
    }

    /**
     * {@inheritDoc}
     */
    public void rebind(final String name, final Object obj) throws NamingException {
        getThreadContext().rebind(name, obj);
    }

    /**
     * {@inheritDoc}
     */
    public void unbind(final Name name) throws NamingException {
        getThreadContext().unbind(name);
    }

    /**
     * {@inheritDoc}
     */
    public void unbind(final String name) throws NamingException {
        getThreadContext().unbind(name);
    }

    /**
     * {@inheritDoc}
     */
    public void rename(final Name oldName, final Name newName) throws NamingException {
        getThreadContext().rename(oldName, newName);
    }

    /**
     * {@inheritDoc}
     */
    public void rename(final String oldName, final String newName) throws NamingException {
        getThreadContext().rename(oldName, newName);
    }

    /**
     * {@inheritDoc}
     */
    public NamingEnumeration<NameClassPair> list(final Name name) throws NamingException {
        return getThreadContext().list(name);
    }

    /**
     * {@inheritDoc}
     */
    public NamingEnumeration<NameClassPair> list(final String name) throws NamingException {
        return getThreadContext().list(name);
    }

    /**
     * {@inheritDoc}
     */
    public NamingEnumeration<Binding> listBindings(final Name name) throws NamingException {
        return getThreadContext().listBindings(name);
    }

    /**
     * {@inheritDoc}
     */
    public NamingEnumeration<Binding> listBindings(final String name) throws NamingException {
        return getThreadContext().listBindings(name);
    }

    /**
     * {@inheritDoc}
     */
    public void destroySubcontext(final Name name) throws NamingException {
        getThreadContext().destroySubcontext(name);
    }

    /**
     * {@inheritDoc}
     */
    public void destroySubcontext(final String name) throws NamingException {
        getThreadContext().destroySubcontext(name);
    }

    /**
     * {@inheritDoc}
     */
    public Context createSubcontext(final Name name) throws NamingException {
        return getThreadContext().createSubcontext(name);
    }

    /**
     * {@inheritDoc}
     */
    public Context createSubcontext(final String name) throws NamingException {
        return getThreadContext().createSubcontext(name);
    }

    /**
     * {@inheritDoc}
     */
    public Object lookupLink(final Name name) throws NamingException {
        return getThreadContext().lookupLink(name);
    }

    /**
     * {@inheritDoc}
     */
    public Object lookupLink(final String name) throws NamingException {
        return getThreadContext().lookupLink(name);
    }

    /**
     * {@inheritDoc}
     */
    public NameParser getNameParser(final Name name) throws NamingException {
        return getThreadContext().getNameParser(name);
    }

    /**
     * {@inheritDoc}
     */
    public NameParser getNameParser(final String name) throws NamingException {
        return getThreadContext().getNameParser(name);
    }

    /**
     * {@inheritDoc}
     */
    public Name composeName(final Name name, final Name prefix) throws NamingException {
        return getThreadContext().composeName(name, prefix);
    }

    /**
     * {@inheritDoc}
     */
    public String composeName(final String name, final String prefix) throws NamingException {
        return getThreadContext().composeName(name, prefix);
    }

    /**
     * {@inheritDoc}
     */
    public Object addToEnvironment(final String propName, final Object propVal) throws NamingException {
        return getThreadContext().addToEnvironment(propName, propVal);
    }

    /**
     * {@inheritDoc}
     */
    public Object removeFromEnvironment(final String propName) throws NamingException {
        return getThreadContext().removeFromEnvironment(propName);
    }

    /**
     * {@inheritDoc}
     */
    public Hashtable<?, ?> getEnvironment() throws NamingException {
        return getThreadContext().getEnvironment();
    }

    /**
     * {@inheritDoc}
     */
    public void close() throws NamingException {
        getThreadContext().close();
    }

    /**
     * {@inheritDoc}
     */
    public String getNameInNamespace() throws NamingException {
        return "";
    }

    /**
     * Gets current context deployment context.
     *
     * @return context of deployment
     * @throws NamingException
     *             for exception
     */
    private Context getThreadContext() throws NamingException {
        final ThreadContext threadContext = ThreadContext.getThreadContext();
        if (skipEjbContext(threadContext)) {
            return ContextBindings.getClassLoader();
        }
        final Context context = threadContext.getBeanContext().getJndiEnc();
        return context;
    }

    private boolean skipEjbContext(final ThreadContext threadContext) {
        // we use it to deploy so if any lookup is done during the deployment
        // we don't want to get the DeployerEjb JNDI tree
        // since this method is pretty quick that's not an issue to do the test
        return threadContext == null || DeployerEjb.class.equals(threadContext.getBeanContext().getBeanClass());
    }

}
