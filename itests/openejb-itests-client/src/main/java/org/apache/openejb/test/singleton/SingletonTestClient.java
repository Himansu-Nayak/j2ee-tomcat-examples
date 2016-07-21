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
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.openejb.test.singleton;

import java.util.Properties;

import javax.naming.InitialContext;

import org.apache.openejb.test.TestClient;
import org.apache.openejb.test.TestManager;

public abstract class SingletonTestClient extends TestClient {

    public SingletonTestClient(final String name) {
        super("Singleton." + name);
    }

    /**
     * Sets up the fixture, for example, open a network connection. This method is called before a test is executed.
     */
    protected void setUp() throws Exception {

        final Properties properties = TestManager.getServer().getContextEnvironment();
        //properties.put(Context.SECURITY_PRINCIPAL, "SINGLETON_test00_CLIENT");
        //properties.put(Context.SECURITY_CREDENTIALS, toString() );
        initialContext = new InitialContext(properties);
    }

}
