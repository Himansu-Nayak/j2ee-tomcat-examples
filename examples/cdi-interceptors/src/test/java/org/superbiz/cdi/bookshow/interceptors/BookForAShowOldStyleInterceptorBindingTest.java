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
package org.superbiz.cdi.bookshow.interceptors;

import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.superbiz.cdi.bookshow.beans.BookForAShowOldStyleInterceptorBinding;
import org.superbiz.cdi.bookshow.tracker.InterceptionOrderTracker;

public class BookForAShowOldStyleInterceptorBindingTest {

    public static EJBContainer ejbContainer;
    @EJB
    private BookForAShowOldStyleInterceptorBinding bookForAShowBean;

    /**
     * Bootstrap the Embedded EJB Container
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUp() throws Exception {
        ejbContainer = EJBContainer.createEJBContainer();
    }

    @AfterClass
    public static void tearDown() {
        // clear the lists after each test
        InterceptionOrderTracker.getInterceptedByList().clear();
        InterceptionOrderTracker.getMethodsInterceptedList().clear();
        ejbContainer.close();
    }

    @Before
    public void inject() throws NamingException {
        ejbContainer.getContext().bind("inject", this);
    }

    /**
     * Test basic interception
     */
    @Test
    public void testMethodShouldBeIntercepted() {
        // action
        bookForAShowBean.getMoviesList();
        // verify
        assertTrue(InterceptionOrderTracker.getMethodsInterceptedList().contains("getMoviesList"));
    }
}
