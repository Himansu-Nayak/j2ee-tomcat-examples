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

package org.apache.openejb.resource.jdbc.dbcp;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DataSourceConnectionFactory;
import org.apache.openejb.resource.jdbc.DataSourceHelper;

public class DbcpDataSource extends BasicDataSource {

    protected final DataSource ds;

    public DbcpDataSource(final String name, final DataSource dataSource) {
        super(name);
        this.ds = dataSource;
    }

    @Override
    protected ConnectionFactory createConnectionFactory() throws SQLException {
        return new DataSourceConnectionFactory(this.ds, getUsername(), getPassword());
    }

    @Override
    public void setJdbcUrl(final String url) {
        try {
            DataSourceHelper.setUrl(this.ds, url);
        } catch (final Throwable e1) {
            super.setUrl(url);
        }
    }
}
