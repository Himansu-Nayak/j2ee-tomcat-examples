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
package org.apache.openejb.testing;

import static org.junit.Assert.*;

import java.net.URL;

import org.apache.openejb.jee.EjbJar;
import org.apache.openejb.junit.ApplicationComposer;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ApplicationComposer.class)
public class RandomPortTest {
    @RandomPort("httpejb")
    private int port;
    @RandomPort("httpejb")
    private URL portUrl;

    @Module
    public EjbJar jar() {
        return new EjbJar();
    }

    @Test
    public void checkRandom() {
        assertTrue(port > 0);
        assertNotNull(portUrl);
        assertEquals(port, portUrl.getPort());
    }
}
