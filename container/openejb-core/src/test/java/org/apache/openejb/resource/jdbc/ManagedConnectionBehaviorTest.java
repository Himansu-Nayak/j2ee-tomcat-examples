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
package org.apache.openejb.resource.jdbc;

import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.sql.DataSource;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.apache.geronimo.transaction.manager.GeronimoTransactionManager;
import org.apache.openejb.resource.GeronimoTransactionManagerFactory;
import org.apache.openejb.resource.TransactionManagerWrapper;
import org.apache.openejb.resource.jdbc.managed.local.ManagedDataSource;
import org.junit.Test;

public class ManagedConnectionBehaviorTest {
    @Test
    public void run() throws Exception {
        final GeronimoTransactionManager geronimoTransactionManager = new GeronimoTransactionManager(
                (int) TimeUnit.MINUTES.toMillis(10));
        final TransactionManager mgr = new TransactionManagerWrapper(geronimoTransactionManager,
                "ManagedConnectionBehaviorTest", new GeronimoTransactionManagerFactory.GeronimoXAResourceWrapper());

        final MyDs myDs = new MyDs();
        final DataSource ds = new ManagedDataSource(myDs, geronimoTransactionManager, geronimoTransactionManager);

        { // no tx
            final Connection connection = ds.getConnection();
            assertTrue(myDs.connections.isEmpty()); // not yet needed
            connection.createBlob(); // just to call something
            assertFalse(myDs.connections.iterator().next().closed);
            connection.close();
            assertTrue(myDs.connections.iterator().next().closed);
            myDs.connections.clear();
        }
        { // tx
            mgr.begin();
            final Connection connection = ds.getConnection();
            assertTrue(myDs.connections.isEmpty()); // not yet needed
            connection.createBlob(); // just to call something
            assertFalse(myDs.connections.iterator().next().closed);
            mgr.commit();
            assertTrue(myDs.connections.iterator().next().closed);
            assertTrue(myDs.connections.iterator().next().commit);
            assertFalse(myDs.connections.iterator().next().rollback);
            myDs.connections.clear();
        }
        { // tx already init
            mgr.begin();
            final Connection connection = ds.getConnection();
            assertTrue(myDs.connections.isEmpty()); // not yet needed
            connection.createBlob(); // just to call something
            assertFalse(myDs.connections.iterator().next().closed);
            for (int i = 0; i < 5; i++) { // here the connection is already created, ensure we dont leak other connections
                connection.createBlob();
            }
            assertEquals(1, myDs.connections.size());
            mgr.commit();
            assertTrue(myDs.connections.iterator().next().closed);
            assertTrue(myDs.connections.iterator().next().commit);
            assertFalse(myDs.connections.iterator().next().rollback);
            myDs.connections.clear();
        }
        { // multiple tx
            mgr.begin();
            final Connection connection = ds.getConnection();
            assertTrue(myDs.connections.isEmpty()); // not yet needed
            connection.createBlob(); // just to call something
            assertFalse(myDs.connections.iterator().next().closed);
            final Transaction previous = mgr.suspend();
            mgr.begin();
            final Connection connection2 = ds.getConnection();
            connection2.createBlob();
            assertEquals(2, myDs.connections.size());
            mgr.commit();
            mgr.resume(previous);
            mgr.commit();
            final Iterator<MyConn> iterator = myDs.connections.iterator();
            final MyConn first = iterator.next();
            assertTrue(first.closed);
            assertTrue(first.commit);
            assertTrue(myDs.connections.iterator().next().commit);
            myDs.connections.clear();
        }

    }

    public static class MyDs implements DataSource {
        private final Collection<MyConn> connections = new LinkedList<>();

        @Override
        public Connection getConnection() throws SQLException {
            final MyConn myConn = new MyConn(this);
            connections.add(myConn);
            return myConn;
        }

        @Override
        public Connection getConnection(final String username, final String password) throws SQLException {
            return null;
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            return null;
        }

        @Override
        public void setLogWriter(final PrintWriter out) throws SQLException {
            // no-op
        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return 0;
        }

        @Override
        public void setLoginTimeout(final int seconds) throws SQLException {
            // no-op
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return null;
        }

        @Override
        public <T> T unwrap(final Class<T> iface) throws SQLException {
            return null;
        }

        @Override
        public boolean isWrapperFor(final Class<?> iface) throws SQLException {
            return false;
        }
    }

    public static class MyConn implements Connection {
        private final MyDs parent;
        private Boolean autoCommit;
        private boolean commit;
        private boolean rollback;
        private boolean closed;

        public MyConn(final MyDs myDs) {
            this.parent = myDs;
        }

        @Override
        public Statement createStatement() throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return null;
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return null;
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return null;
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return false;
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            this.autoCommit = autoCommit;
        }

        @Override
        public void commit() throws SQLException {
            commit = true;
        }

        @Override
        public void rollback() throws SQLException {
            rollback = true;
        }

        @Override
        public void close() throws SQLException {
            closed = true;
        }

        @Override
        public boolean isClosed() throws SQLException {
            return closed;
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return null;
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return false;
        }

        @Override
        public void setReadOnly(final boolean readOnly) throws SQLException {

        }

        @Override
        public String getCatalog() throws SQLException {
            return null;
        }

        @Override
        public void setCatalog(final String catalog) throws SQLException {

        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return 0;
        }

        @Override
        public void setTransactionIsolation(final int level) throws SQLException {

        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return null;
        }

        @Override
        public void clearWarnings() throws SQLException {

        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
                throws SQLException {
            return null;
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
                throws SQLException {
            return null;
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return null;
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

        }

        @Override
        public int getHoldability() throws SQLException {
            return 0;
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {

        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return null;
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return null;
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {

        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {

        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
                throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                int resultSetHoldability) throws SQLException {
            return null;
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
                int resultSetHoldability) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return null;
        }

        @Override
        public Clob createClob() throws SQLException {
            return null;
        }

        @Override
        public Blob createBlob() throws SQLException {
            return null;
        }

        @Override
        public NClob createNClob() throws SQLException {
            return null;
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return null;
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return false;
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {

        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return null;
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return null;
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {

        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return null;
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return null;
        }

        @Override
        public String getSchema() throws SQLException {
            return null;
        }

        @Override
        public void setSchema(String schema) throws SQLException {

        }

        @Override
        public void abort(Executor executor) throws SQLException {

        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return 0;
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
        }
    }
}
