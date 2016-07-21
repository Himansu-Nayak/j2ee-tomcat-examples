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
package org.apache.openejb.config;

import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.openejb.config.sys.Resource;
import org.apache.openejb.jee.EjbJar;
import org.junit.Assert;
import org.junit.Test;

public class AutoConfigTest {

    private final String[] list = new String[] { "def-monitor-internal/jdbc/appmgr-jta", "jdbc/appmgr-jta",
            "def-monitor-internal/jdbc/erp_global-jta", "jdbc/erp_global-jta", "def-monitor-internal/jdbc/catalog-jta",
            "jdbc/catalog-jta", "def-monitor-internal/jdbc/erp-jta", "jdbc/erp-jta",
            "def-monitor-internal/jdbc/ghi-jta", "jdbc/ghi-jta", "def-monitor-internal/jdbc/usr-jta", "jdbc/usr-jta",
            "def-monitor-internal/jdbc/email-jta", "jdbc/email-jta", "def-monitor-internal/jdbc/webcode-jta",
            "jdbc/webcode-jta", "def-monitor-internal/jdbc/webcode-download-jta", "jdbc/webcode-download-jta",
            "def-monitor-internal/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "def-monitor-internal/jdbc/clipsafe-jta", "jdbc/clipsafe-jta", "def-monitor-internal/jdbc/hazmat-jta",
            "jdbc/hazmat-jta", "def-monitor-internal/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta",
            "appmgr/jdbc/appmgr-jta", "jdbc/appmgr-jta", "appmgr/jdbc/erp_global-jta", "jdbc/erp_global-jta",
            "appmgr/jdbc/catalog-jta", "jdbc/catalog-jta", "appmgr/jdbc/erp-jta", "jdbc/erp-jta", "appmgr/jdbc/ghi-jta",
            "jdbc/ghi-jta", "appmgr/jdbc/usr-jta", "jdbc/usr-jta", "appmgr/jdbc/email-jta", "jdbc/email-jta",
            "appmgr/jdbc/webcode-jta", "jdbc/webcode-jta", "appmgr/jdbc/webcode-download-jta",
            "jdbc/webcode-download-jta", "appmgr/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "appmgr/jdbc/clipsafe-jta", "jdbc/clipsafe-jta", "appmgr/jdbc/hazmat-jta", "jdbc/hazmat-jta",
            "appmgr/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta", "abc-catalog-opq-uvw-jkl-web/jdbc/appmgr-jta",
            "jdbc/appmgr-jta", "abc-catalog-opq-uvw-jkl-web/jdbc/erp_global-jta", "jdbc/erp_global-jta",
            "abc-catalog-opq-uvw-jkl-web/jdbc/catalog-jta", "jdbc/catalog-jta",
            "abc-catalog-opq-uvw-jkl-web/jdbc/erp-jta", "jdbc/erp-jta", "abc-catalog-opq-uvw-jkl-web/jdbc/ghi-jta",
            "jdbc/ghi-jta", "abc-catalog-opq-uvw-jkl-web/jdbc/usr-jta", "jdbc/usr-jta",
            "abc-catalog-opq-uvw-jkl-web/jdbc/email-jta", "jdbc/email-jta",
            "abc-catalog-opq-uvw-jkl-web/jdbc/webcode-jta", "jdbc/webcode-jta",
            "abc-catalog-opq-uvw-jkl-web/jdbc/webcode-download-jta", "jdbc/webcode-download-jta",
            "abc-catalog-opq-uvw-jkl-web/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "abc-catalog-opq-uvw-jkl-web/jdbc/clipsafe-jta", "jdbc/clipsafe-jta",
            "abc-catalog-opq-uvw-jkl-web/jdbc/hazmat-jta", "jdbc/hazmat-jta",
            "abc-catalog-opq-uvw-jkl-web/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta",
            "abc-catapp-product-rest-web/jdbc/appmgr-jta", "jdbc/appmgr-jta",
            "abc-catapp-product-rest-web/jdbc/erp_global-jta", "jdbc/erp_global-jta",
            "abc-catapp-product-rest-web/jdbc/catalog-jta", "jdbc/catalog-jta",
            "abc-catapp-product-rest-web/jdbc/erp-jta", "jdbc/erp-jta", "abc-catapp-product-rest-web/jdbc/ghi-jta",
            "jdbc/ghi-jta", "abc-catapp-product-rest-web/jdbc/usr-jta", "jdbc/usr-jta",
            "abc-catapp-product-rest-web/jdbc/email-jta", "jdbc/email-jta",
            "abc-catapp-product-rest-web/jdbc/webcode-jta", "jdbc/webcode-jta",
            "abc-catapp-product-rest-web/jdbc/webcode-download-jta", "jdbc/webcode-download-jta",
            "abc-catapp-product-rest-web/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "abc-catapp-product-rest-web/jdbc/clipsafe-jta", "jdbc/clipsafe-jta",
            "abc-catapp-product-rest-web/jdbc/hazmat-jta", "jdbc/hazmat-jta",
            "abc-catapp-product-rest-web/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta",
            "abc-checkout-uvw-jkl-web/jdbc/appmgr-jta", "jdbc/appmgr-jta",
            "abc-checkout-uvw-jkl-web/jdbc/erp_global-jta", "jdbc/erp_global-jta",
            "abc-checkout-uvw-jkl-web/jdbc/catalog-jta", "jdbc/catalog-jta", "abc-checkout-uvw-jkl-web/jdbc/erp-jta",
            "jdbc/erp-jta", "abc-checkout-uvw-jkl-web/jdbc/ghi-jta", "jdbc/ghi-jta",
            "abc-checkout-uvw-jkl-web/jdbc/usr-jta", "jdbc/usr-jta", "abc-checkout-uvw-jkl-web/jdbc/email-jta",
            "jdbc/email-jta", "abc-checkout-uvw-jkl-web/jdbc/webcode-jta", "jdbc/webcode-jta",
            "abc-checkout-uvw-jkl-web/jdbc/webcode-download-jta", "jdbc/webcode-download-jta",
            "abc-checkout-uvw-jkl-web/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "abc-checkout-uvw-jkl-web/jdbc/clipsafe-jta", "jdbc/clipsafe-jta",
            "abc-checkout-uvw-jkl-web/jdbc/hazmat-jta", "jdbc/hazmat-jta",
            "abc-checkout-uvw-jkl-web/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta",
            "abc-clip-uvw-jkl-web/jdbc/appmgr-jta", "jdbc/appmgr-jta", "abc-clip-uvw-jkl-web/jdbc/erp_global-jta",
            "jdbc/erp_global-jta", "abc-clip-uvw-jkl-web/jdbc/catalog-jta", "jdbc/catalog-jta",
            "abc-clip-uvw-jkl-web/jdbc/erp-jta", "jdbc/erp-jta", "abc-clip-uvw-jkl-web/jdbc/ghi-jta", "jdbc/ghi-jta",
            "abc-clip-uvw-jkl-web/jdbc/usr-jta", "jdbc/usr-jta", "abc-clip-uvw-jkl-web/jdbc/email-jta",
            "jdbc/email-jta", "abc-clip-uvw-jkl-web/jdbc/webcode-jta", "jdbc/webcode-jta",
            "abc-clip-uvw-jkl-web/jdbc/webcode-download-jta", "jdbc/webcode-download-jta",
            "abc-clip-uvw-jkl-web/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "abc-clip-uvw-jkl-web/jdbc/clipsafe-jta", "jdbc/clipsafe-jta", "abc-clip-uvw-jkl-web/jdbc/hazmat-jta",
            "jdbc/hazmat-jta", "abc-clip-uvw-jkl-web/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta",
            "abc-countrydata-uvw-jkl-external-web/jdbc/appmgr-jta", "jdbc/appmgr-jta",
            "abc-countrydata-uvw-jkl-external-web/jdbc/erp_global-jta", "jdbc/erp_global-jta",
            "abc-countrydata-uvw-jkl-external-web/jdbc/catalog-jta", "jdbc/catalog-jta",
            "abc-countrydata-uvw-jkl-external-web/jdbc/erp-jta", "jdbc/erp-jta",
            "abc-countrydata-uvw-jkl-external-web/jdbc/ghi-jta", "jdbc/ghi-jta",
            "abc-countrydata-uvw-jkl-external-web/jdbc/usr-jta", "jdbc/usr-jta",
            "abc-countrydata-uvw-jkl-external-web/jdbc/email-jta", "jdbc/email-jta",
            "abc-countrydata-uvw-jkl-external-web/jdbc/webcode-jta", "jdbc/webcode-jta",
            "abc-countrydata-uvw-jkl-external-web/jdbc/webcode-download-jta", "jdbc/webcode-download-jta",
            "abc-countrydata-uvw-jkl-external-web/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "abc-countrydata-uvw-jkl-external-web/jdbc/clipsafe-jta", "jdbc/clipsafe-jta",
            "abc-countrydata-uvw-jkl-external-web/jdbc/hazmat-jta", "jdbc/hazmat-jta",
            "abc-countrydata-uvw-jkl-external-web/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta",
            "abc-ghi-uvw-jkl-web/jdbc/appmgr-jta", "jdbc/appmgr-jta", "abc-ghi-uvw-jkl-web/jdbc/erp_global-jta",
            "jdbc/erp_global-jta", "abc-ghi-uvw-jkl-web/jdbc/catalog-jta", "jdbc/catalog-jta",
            "abc-ghi-uvw-jkl-web/jdbc/erp-jta", "jdbc/erp-jta", "abc-ghi-uvw-jkl-web/jdbc/ghi-jta", "jdbc/ghi-jta",
            "abc-ghi-uvw-jkl-web/jdbc/usr-jta", "jdbc/usr-jta", "abc-ghi-uvw-jkl-web/jdbc/email-jta", "jdbc/email-jta",
            "abc-ghi-uvw-jkl-web/jdbc/webcode-jta", "jdbc/webcode-jta", "abc-ghi-uvw-jkl-web/jdbc/webcode-download-jta",
            "jdbc/webcode-download-jta", "abc-ghi-uvw-jkl-web/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "abc-ghi-uvw-jkl-web/jdbc/clipsafe-jta", "jdbc/clipsafe-jta", "abc-ghi-uvw-jkl-web/jdbc/hazmat-jta",
            "jdbc/hazmat-jta", "abc-ghi-uvw-jkl-web/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta",
            "abc-hazmat-uvw-jkl-web/jdbc/appmgr-jta", "jdbc/appmgr-jta", "abc-hazmat-uvw-jkl-web/jdbc/erp_global-jta",
            "jdbc/erp_global-jta", "abc-hazmat-uvw-jkl-web/jdbc/catalog-jta", "jdbc/catalog-jta",
            "abc-hazmat-uvw-jkl-web/jdbc/erp-jta", "jdbc/erp-jta", "abc-hazmat-uvw-jkl-web/jdbc/ghi-jta",
            "jdbc/ghi-jta", "abc-hazmat-uvw-jkl-web/jdbc/usr-jta", "jdbc/usr-jta",
            "abc-hazmat-uvw-jkl-web/jdbc/email-jta", "jdbc/email-jta", "abc-hazmat-uvw-jkl-web/jdbc/webcode-jta",
            "jdbc/webcode-jta", "abc-hazmat-uvw-jkl-web/jdbc/webcode-download-jta", "jdbc/webcode-download-jta",
            "abc-hazmat-uvw-jkl-web/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "abc-hazmat-uvw-jkl-web/jdbc/clipsafe-jta", "jdbc/clipsafe-jta", "abc-hazmat-uvw-jkl-web/jdbc/hazmat-jta",
            "jdbc/hazmat-jta", "abc-hazmat-uvw-jkl-web/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta",
            "abc-oc-download-uvw-jkl-web/jdbc/appmgr-jta", "jdbc/appmgr-jta",
            "abc-oc-download-uvw-jkl-web/jdbc/erp_global-jta", "jdbc/erp_global-jta",
            "abc-oc-download-uvw-jkl-web/jdbc/catalog-jta", "jdbc/catalog-jta",
            "abc-oc-download-uvw-jkl-web/jdbc/erp-jta", "jdbc/erp-jta", "abc-oc-download-uvw-jkl-web/jdbc/ghi-jta",
            "jdbc/ghi-jta", "abc-oc-download-uvw-jkl-web/jdbc/usr-jta", "jdbc/usr-jta",
            "abc-oc-download-uvw-jkl-web/jdbc/email-jta", "jdbc/email-jta",
            "abc-oc-download-uvw-jkl-web/jdbc/webcode-jta", "jdbc/webcode-jta",
            "abc-oc-download-uvw-jkl-web/jdbc/webcode-download-jta", "jdbc/webcode-download-jta",
            "abc-oc-download-uvw-jkl-web/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "abc-oc-download-uvw-jkl-web/jdbc/clipsafe-jta", "jdbc/clipsafe-jta",
            "abc-oc-download-uvw-jkl-web/jdbc/hazmat-jta", "jdbc/hazmat-jta",
            "abc-oc-download-uvw-jkl-web/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta",
            "abc-xy-itemdetail-accessories-uvw-jkl-web/jdbc/appmgr-jta", "jdbc/appmgr-jta",
            "abc-xy-itemdetail-accessories-uvw-jkl-web/jdbc/erp_global-jta", "jdbc/erp_global-jta",
            "abc-xy-itemdetail-accessories-uvw-jkl-web/jdbc/catalog-jta", "jdbc/catalog-jta",
            "abc-xy-itemdetail-accessories-uvw-jkl-web/jdbc/erp-jta", "jdbc/erp-jta",
            "abc-xy-itemdetail-accessories-uvw-jkl-web/jdbc/ghi-jta", "jdbc/ghi-jta",
            "abc-xy-itemdetail-accessories-uvw-jkl-web/jdbc/usr-jta", "jdbc/usr-jta",
            "abc-xy-itemdetail-accessories-uvw-jkl-web/jdbc/email-jta", "jdbc/email-jta",
            "abc-xy-itemdetail-accessories-uvw-jkl-web/jdbc/webcode-jta", "jdbc/webcode-jta",
            "abc-xy-itemdetail-accessories-uvw-jkl-web/jdbc/webcode-download-jta", "jdbc/webcode-download-jta",
            "abc-xy-itemdetail-accessories-uvw-jkl-web/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "abc-xy-itemdetail-accessories-uvw-jkl-web/jdbc/clipsafe-jta", "jdbc/clipsafe-jta",
            "abc-xy-itemdetail-accessories-uvw-jkl-web/jdbc/hazmat-jta", "jdbc/hazmat-jta",
            "abc-xy-itemdetail-accessories-uvw-jkl-web/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta",
            "abc-xy-itemdetail-header-uvw-jkl-web/jdbc/appmgr-jta", "jdbc/appmgr-jta",
            "abc-xy-itemdetail-header-uvw-jkl-web/jdbc/erp_global-jta", "jdbc/erp_global-jta",
            "abc-xy-itemdetail-header-uvw-jkl-web/jdbc/catalog-jta", "jdbc/catalog-jta",
            "abc-xy-itemdetail-header-uvw-jkl-web/jdbc/erp-jta", "jdbc/erp-jta",
            "abc-xy-itemdetail-header-uvw-jkl-web/jdbc/ghi-jta", "jdbc/ghi-jta",
            "abc-xy-itemdetail-header-uvw-jkl-web/jdbc/usr-jta", "jdbc/usr-jta",
            "abc-xy-itemdetail-header-uvw-jkl-web/jdbc/email-jta", "jdbc/email-jta",
            "abc-xy-itemdetail-header-uvw-jkl-web/jdbc/webcode-jta", "jdbc/webcode-jta",
            "abc-xy-itemdetail-header-uvw-jkl-web/jdbc/webcode-download-jta", "jdbc/webcode-download-jta",
            "abc-xy-itemdetail-header-uvw-jkl-web/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "abc-xy-itemdetail-header-uvw-jkl-web/jdbc/clipsafe-jta", "jdbc/clipsafe-jta",
            "abc-xy-itemdetail-header-uvw-jkl-web/jdbc/hazmat-jta", "jdbc/hazmat-jta",
            "abc-xy-itemdetail-header-uvw-jkl-web/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta",
            "abc-resilience-hystrix-config-web/jdbc/appmgr-jta", "jdbc/appmgr-jta",
            "abc-resilience-hystrix-config-web/jdbc/erp_global-jta", "jdbc/erp_global-jta",
            "abc-resilience-hystrix-config-web/jdbc/catalog-jta", "jdbc/catalog-jta",
            "abc-resilience-hystrix-config-web/jdbc/erp-jta", "jdbc/erp-jta",
            "abc-resilience-hystrix-config-web/jdbc/ghi-jta", "jdbc/ghi-jta",
            "abc-resilience-hystrix-config-web/jdbc/usr-jta", "jdbc/usr-jta",
            "abc-resilience-hystrix-config-web/jdbc/email-jta", "jdbc/email-jta",
            "abc-resilience-hystrix-config-web/jdbc/webcode-jta", "jdbc/webcode-jta",
            "abc-resilience-hystrix-config-web/jdbc/webcode-download-jta", "jdbc/webcode-download-jta",
            "abc-resilience-hystrix-config-web/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "abc-resilience-hystrix-config-web/jdbc/clipsafe-jta", "jdbc/clipsafe-jta",
            "abc-resilience-hystrix-config-web/jdbc/hazmat-jta", "jdbc/hazmat-jta",
            "abc-resilience-hystrix-config-web/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta",
            "abc-resilience-hystrix-monitoring-web/jdbc/appmgr-jta", "jdbc/appmgr-jta",
            "abc-resilience-hystrix-monitoring-web/jdbc/erp_global-jta", "jdbc/erp_global-jta",
            "abc-resilience-hystrix-monitoring-web/jdbc/catalog-jta", "jdbc/catalog-jta",
            "abc-resilience-hystrix-monitoring-web/jdbc/erp-jta", "jdbc/erp-jta",
            "abc-resilience-hystrix-monitoring-web/jdbc/ghi-jta", "jdbc/ghi-jta",
            "abc-resilience-hystrix-monitoring-web/jdbc/usr-jta", "jdbc/usr-jta",
            "abc-resilience-hystrix-monitoring-web/jdbc/email-jta", "jdbc/email-jta",
            "abc-resilience-hystrix-monitoring-web/jdbc/webcode-jta", "jdbc/webcode-jta",
            "abc-resilience-hystrix-monitoring-web/jdbc/webcode-download-jta", "jdbc/webcode-download-jta",
            "abc-resilience-hystrix-monitoring-web/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "abc-resilience-hystrix-monitoring-web/jdbc/clipsafe-jta", "jdbc/clipsafe-jta",
            "abc-resilience-hystrix-monitoring-web/jdbc/hazmat-jta", "jdbc/hazmat-jta",
            "abc-resilience-hystrix-monitoring-web/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta",
            "abc-jkl-uvw-jkl-web/jdbc/appmgr-jta", "jdbc/appmgr-jta", "abc-jkl-uvw-jkl-web/jdbc/erp_global-jta",
            "jdbc/erp_global-jta", "abc-jkl-uvw-jkl-web/jdbc/catalog-jta", "jdbc/catalog-jta",
            "abc-jkl-uvw-jkl-web/jdbc/erp-jta", "jdbc/erp-jta", "abc-jkl-uvw-jkl-web/jdbc/ghi-jta", "jdbc/ghi-jta",
            "abc-jkl-uvw-jkl-web/jdbc/usr-jta", "jdbc/usr-jta", "abc-jkl-uvw-jkl-web/jdbc/email-jta", "jdbc/email-jta",
            "abc-jkl-uvw-jkl-web/jdbc/webcode-jta", "jdbc/webcode-jta", "abc-jkl-uvw-jkl-web/jdbc/webcode-download-jta",
            "jdbc/webcode-download-jta", "abc-jkl-uvw-jkl-web/jdbc/countrydata-jta", "jdbc/countrydata-jta",
            "abc-jkl-uvw-jkl-web/jdbc/clipsafe-jta", "jdbc/clipsafe-jta", "abc-jkl-uvw-jkl-web/jdbc/hazmat-jta",
            "jdbc/hazmat-jta", "abc-jkl-uvw-jkl-web/jdbc/waab-refapp-jta", "jdbc/waab-refapp-jta" };

    @Test
    public void testFirstMatching() throws Exception {
        final ConfigurationFactory cf = new ConfigurationFactory();
        final AutoConfig ac = new AutoConfig(cf);

        final AppModule appModule = new AppModule(new EjbModule(new EjbJar()));

        for (final String s : list) {
            appModule.getResources().add(new Resource(s, "DataSource"));
        }

        final AutoConfig.AppResources resources = new AutoConfig.AppResources(appModule);

        final Method m = ac.getClass().getDeclaredMethod("firstMatching", String.class, String.class, Properties.class,
                AutoConfig.AppResources.class);
        m.setAccessible(true);

        for (final String s : list) {
            final String prefix = s.substring(0, s.indexOf('/'));
            final String result = (String) m.invoke(ac, prefix, "DataSource", new Properties(), resources);
            Assert.assertTrue(result.startsWith(prefix));
        }

        String result = (String) m.invoke(ac, "abc-jkl-uvw-jkl-web", "DataSource", new Properties(), resources);
        Assert.assertEquals("abc-jkl-uvw-jkl-web/jdbc/appmgr-jta", result);

        result = (String) m.invoke(ac, "jdbc", "DataSource", new Properties(), resources);
        Assert.assertEquals("jdbc/appmgr-jta", result);
    }
}