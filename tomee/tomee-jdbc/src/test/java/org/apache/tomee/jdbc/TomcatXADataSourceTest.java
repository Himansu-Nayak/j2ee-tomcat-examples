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
package org.apache.tomee.jdbc;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.sql.DataSource;

import org.apache.openejb.jee.EjbJar;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.resource.jdbc.managed.local.ManagedDataSource;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.Configuration;
import org.apache.openejb.testing.Module;
import org.apache.openejb.testng.PropertiesBuilder;
import org.hsqldb.jdbc.pool.JDBCXAConnectionWrapper;
import org.hsqldb.jdbc.pool.JDBCXADataSource;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ApplicationComposer.class)
public class TomcatXADataSourceTest {
    @Resource(name = "xadb")
    private DataSource ds;

    @EJB
    private TxP tx;

    @Module
    @Classes(TxP.class)
    public EjbJar mandatory() {
        return new EjbJar();
    }

    @Configuration
    public Properties props() {
        return new PropertiesBuilder().p("openejb.jdbc.datasource-creator", TomEEDataSourceCreator.class.getName())

        .p("txMgr", "new://TransactionManager?type=TransactionManager").p("txMgr.txRecovery", "true")
                .p("txMgr.logFileDir", "target/test/xa/howl")

        // real XA datasources
                .p("xa", "new://Resource?class-name=" + JDBCXADataSource.class.getName())
                .p("xa.url", "jdbc:hsqldb:mem:tomcat-xa").p("xa.user", "sa").p("xa.password", "")
                .p("xa.SkipImplicitAttributes", "true").p("xa.SkipPropertiesFallback", "true") // otherwise goes to connection properties

        .p("xadb", "new://Resource?type=DataSource").p("xadb.xaDataSource", "xa").p("xadb.JtaManaged", "true")
                .p("xadb.MaxIdle", "25").p("xadb.MaxActive", "25").p("xadb.InitialSize", "3")

        .build();
    }

    @Test
    public void check() throws SQLException {
        assertNotNull(ds);
        final TomEEDataSourceCreator.TomEEDataSource tds = TomEEDataSourceCreator.TomEEDataSource.class
                .cast(ManagedDataSource.class.cast(ds).getDelegate());

        assertEquals(3, tds.getIdle()); // InitSize

        try (final Connection c = ds.getConnection()) {
            assertNotNull(c);

            final Connection connection = c.getMetaData().getConnection(); // just to do something and force the connection init
            assertThat(connection, instanceOf(JDBCXAConnectionWrapper.class));
        } // here we close the connection so we are back in the initial state

        assertEquals(0, tds.getActive());
        assertEquals(3, tds.getIdle());

        for (int it = 0; it < 5; it++) { // ensures it always works and not only the first time
            final Collection<Connection> connections = new ArrayList<>(25);
            for (int i = 0; i < 25; i++) {
                final Connection connection = ds.getConnection();
                connections.add(connection);
                connection.getMetaData(); // trigger connection retrieving otherwise nothing is done (pool is not used)
            }
            assertEquals(25, tds.getActive());
            assertEquals(0, tds.getIdle());
            for (final Connection toClose : connections) {
                toClose.close();
            }
            assertEquals(0, tds.getActive());
            assertEquals(25, tds.getIdle());
        }

        // in tx
        for (int it = 0; it < 5; it++) { // ensures it always works and not only the first time
            for (int i = 0; i < 25; i++) {
                tx.run(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Connection c = null;
                            for (int i = 0; i < 25; i++) {
                                final Connection connection = ds.getConnection();
                                connection.getMetaData(); // trigger connection retrieving otherwise nothing is done (pool is not used)
                                if (c != null) {
                                    assertEquals(c, connection);
                                } else {
                                    c = connection;
                                }
                            }
                        } catch (final SQLException sql) {
                            fail(sql.getMessage());
                        }
                    }
                });
            }
            assertEquals(0, tds.getActive());
            assertEquals(25, tds.getIdle());
        }
    }

    @Singleton
    public static class TxP {
        public void run(final Runnable r) {
            r.run();
        }
    }
}
