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
package org.apache.openejb.client;

import java.io.*;
import java.net.URI;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

/**
 * @version $Rev$ $Date$
 */
public class OverlyStickyConnectionTest extends TestCase {

    private static final ThreadLocal<Connection> connection = new ThreadLocal<Connection>();

    public void test() throws Exception {
        ConnectionManager.registerFactory("mock", new MockConnectionFactory());

        getContext("mock://red:4201").lookup("foo");

        assertEquals(new URI("mock://red:4201"), connection.get().getURI());

        getContext("mock://blue:4201").lookup("foo");

        assertEquals(new URI("mock://blue:4201"), connection.get().getURI());

        getContext("mock://red:4201").lookup("foo");

        assertEquals(new URI("mock://red:4201"), connection.get().getURI());
    }

    private InitialContext getContext(final String uri) throws NamingException {
        final Properties p = new Properties();
        p.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
        p.put("java.naming.provider.url", uri);

        return new InitialContext(p);
    }

    public class MockConnectionFactory implements ConnectionFactory {

        @Override
        public Connection getConnection(final URI uri) throws IOException {
            return new Connection() {
                private final ByteArrayInputStream in;
                private final ByteArrayOutputStream out = new ByteArrayOutputStream();

                {
                    connection.set(this);
                    new ProtocolMetaData().writeExternal(out);
                    final ObjectOutputStream oos = new ObjectOutputStream(out);
                    new ClusterResponse(ClusterResponse.Code.CURRENT).writeExternal(oos);
                    new JNDIResponse(ResponseCodes.JNDI_CONTEXT, null).writeExternal(oos);
                    oos.close();

                    in = new ByteArrayInputStream(out.toByteArray());
                    out.reset();
                }

                @Override
                public URI getURI() {
                    return uri;
                }

                @Override
                public void discard() {
                }

                @Override
                public void close() throws IOException {
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return in;
                }

                @Override
                public OutputStream getOutputStream() throws IOException {
                    return out;
                }
            };
        }
    }

}
