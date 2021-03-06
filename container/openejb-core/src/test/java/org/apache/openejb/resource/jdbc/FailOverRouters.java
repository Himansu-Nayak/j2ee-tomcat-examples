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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.apache.openejb.testng.PropertiesBuilder;

public final class FailOverRouters {
    private FailOverRouters() {
        // no-op
    }

    public static String url(final Connection c) throws SQLException {
        final DatabaseMetaData dmd = c.getMetaData();
        try {
            return dmd.getURL();
        } finally {
            c.close();
        }
    }

    public static PropertiesBuilder datasource(final PropertiesBuilder propertiesBuilder, final String name) {
        return propertiesBuilder.property(name, "new://Resource?type=DataSource").property(name + ".JdbcUrl",
                "jdbc:hsqldb:mem:" + name);
    }
}
