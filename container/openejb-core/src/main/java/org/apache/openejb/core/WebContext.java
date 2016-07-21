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

package org.apache.openejb.core;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionIdListener;

import org.apache.openejb.AppContext;
import org.apache.openejb.Injection;
import org.apache.openejb.InjectionProcessor;
import org.apache.openejb.OpenEJBException;
import org.apache.openejb.cdi.ConstructorInjectionBean;
import org.apache.webbeans.component.InjectionTargetBean;
import org.apache.webbeans.config.WebBeansContext;

public class WebContext {
    private final Collection<Injection> injections = new ArrayList<Injection>();
    private final AppContext appContext;
    private final Map<Object, CreationalContext<?>> creationalContexts = new ConcurrentHashMap<>();
    private final Map<Class<?>, ConstructorInjectionBean<Object>> constructorInjectionBeanCache = new ConcurrentHashMap<Class<?>, ConstructorInjectionBean<Object>>();
    private String id;
    private ClassLoader classLoader;
    private Context jndiEnc;
    private Map<String, Object> bindings;
    private WebBeansContext webbeansContext;
    private String contextRoot;
    private String host;
    private Context initialContext;
    private ServletContext servletContext;

    public WebContext(final AppContext appContext) {
        this.appContext = appContext;
    }

    private static boolean isWeb(final Class<?> beanClass) {
        return Servlet.class.isAssignableFrom(beanClass) || Filter.class.isAssignableFrom(beanClass)
                || HttpSessionAttributeListener.class.isAssignableFrom(beanClass)
                || ServletContextListener.class.isAssignableFrom(beanClass)
                || HttpSessionAttributeListener.class.isAssignableFrom(beanClass)
                || ServletRequestListener.class.isAssignableFrom(beanClass)
                || ServletContextAttributeListener.class.isAssignableFrom(beanClass)
                || HttpSessionIdListener.class.isAssignableFrom(beanClass)
                || ServletRequestAttributeListener.class.isAssignableFrom(beanClass);
    }

    public Context getInitialContext() {
        if (initialContext != null) {
            return initialContext;
        }
        try {
            initialContext = (Context) new InitialContext().lookup("java:");
        } catch (final NamingException e) {
            throw new IllegalStateException(e);
        }
        return initialContext;
    }

    public void setInitialContext(final Context initialContext) {
        this.initialContext = initialContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(final ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Collection<Injection> getInjections() {
        return injections;
    }

    public Context getJndiEnc() {
        return jndiEnc;
    }

    public void setJndiEnc(final Context jndiEnc) {
        this.jndiEnc = jndiEnc;
    }

    public AppContext getAppContext() {
        return appContext;
    }

    public <T> Instance newWeakableInstance(final Class<T> beanClass) throws OpenEJBException {
        final WebBeansContext webBeansContext = getWebBeansContext();
        final ConstructorInjectionBean<Object> beanDefinition = getConstructorInjectionBean(beanClass, webBeansContext);
        CreationalContext<Object> creationalContext;
        final Object o;
        if (webBeansContext == null) {
            creationalContext = null;
            try {
                o = beanClass.newInstance();
            } catch (final InstantiationException | IllegalAccessException e) {
                throw new OpenEJBException(e);
            }
        } else {
            creationalContext = webBeansContext.getBeanManagerImpl().createCreationalContext(beanDefinition);
            o = beanDefinition.create(creationalContext);
        }

        // Create bean instance
        final Context unwrap = InjectionProcessor.unwrap(getInitialContext());
        final InjectionProcessor injectionProcessor = new InjectionProcessor(o, injections, unwrap);

        final Object beanInstance = injectionProcessor.createInstance();

        if (webBeansContext != null) {
            final InjectionTargetBean<Object> bean = InjectionTargetBean.class.cast(beanDefinition);
            bean.getInjectionTarget().inject(beanInstance, creationalContext);
            if (shouldBeReleased(bean.getScope())) {
                creationalContexts.put(beanInstance, creationalContext);
            }
        }
        return new Instance(beanInstance, creationalContext);
    }

    public Object newInstance(final Class beanClass) throws OpenEJBException {
        final Instance instance = newWeakableInstance(beanClass);
        if (instance.getCreationalContext() != null) {
            creationalContexts.put(instance.getValue(), instance.getCreationalContext());
        }
        return instance.getValue();
    }

    private ConstructorInjectionBean<Object> getConstructorInjectionBean(final Class beanClass,
            final WebBeansContext webBeansContext) {
        if (webBeansContext == null) {
            return null;
        }

        ConstructorInjectionBean<Object> beanDefinition = constructorInjectionBeanCache.get(beanClass);
        if (beanDefinition == null) {
            synchronized (this) {
                beanDefinition = constructorInjectionBeanCache.get(beanClass);
                if (beanDefinition == null) {
                    final AnnotatedType annotatedType = webBeansContext.getAnnotatedElementFactory()
                            .newAnnotatedType(beanClass);
                    if (isWeb(beanClass)) {
                        beanDefinition = new ConstructorInjectionBean<>(webBeansContext, beanClass, annotatedType,
                                false);
                    } else {
                        beanDefinition = new ConstructorInjectionBean<>(webBeansContext, beanClass, annotatedType);
                    }

                    constructorInjectionBeanCache.put(beanClass, beanDefinition);
                }
            }
        }
        return beanDefinition;
    }

    public WebBeansContext getWebBeansContext() {
        if (webbeansContext == null) {
            return getAppContext().getWebBeansContext();
        }
        return webbeansContext;
    }

    public Object inject(final Object o) throws OpenEJBException {

        try {
            final WebBeansContext webBeansContext = getWebBeansContext();

            // Create bean instance
            final Context initialContext = (Context) new InitialContext().lookup("java:");
            final Context unwrap = InjectionProcessor.unwrap(initialContext);
            final InjectionProcessor injectionProcessor = new InjectionProcessor(o, injections, unwrap);

            final Object beanInstance = injectionProcessor.createInstance();

            if (webBeansContext != null) {
                final ConstructorInjectionBean<Object> beanDefinition = getConstructorInjectionBean(o.getClass(),
                        webBeansContext);
                final CreationalContext<Object> creationalContext = webBeansContext.getBeanManagerImpl()
                        .createCreationalContext(beanDefinition);

                final InjectionTargetBean<Object> bean = InjectionTargetBean.class.cast(beanDefinition);
                bean.getInjectionTarget().inject(beanInstance, creationalContext);

                if (shouldBeReleased(beanDefinition.getScope())) {
                    creationalContexts.put(beanInstance, creationalContext);
                }
            }

            return beanInstance;
        } catch (final NamingException e) {
            throw new OpenEJBException(e);
        }
    }

    private boolean shouldBeReleased(final Class<? extends Annotation> scope) {
        return scope == null || !getWebBeansContext().getBeanManagerImpl().isNormalScope(scope);
    }

    public Map<String, Object> getBindings() {
        return bindings;
    }

    public void setBindings(final Map<String, Object> bindings) {
        this.bindings = bindings;
    }

    public WebBeansContext getWebbeansContext() {
        return webbeansContext;
    }

    public void setWebbeansContext(final WebBeansContext webbeansContext) {
        this.webbeansContext = webbeansContext;
    }

    public String getContextRoot() {
        return contextRoot;
    }

    public void setContextRoot(final String contextRoot) {
        this.contextRoot = contextRoot;
    }

    public void destroy(final Object o) {
        final CreationalContext<?> ctx = creationalContexts.remove(o);
        if (ctx != null) {
            ctx.release();
        }
    }

    public static class Instance {
        private final Object value;
        private final CreationalContext<?> cc;

        public Instance(final Object value, final CreationalContext<?> cc) {
            this.value = value;
            this.cc = cc;
        }

        public Object getValue() {
            return value;
        }

        public CreationalContext<?> getCreationalContext() {
            return cc;
        }
    }
}
