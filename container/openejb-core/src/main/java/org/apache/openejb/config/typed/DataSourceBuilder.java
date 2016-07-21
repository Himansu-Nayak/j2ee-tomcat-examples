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

package org.apache.openejb.config.typed;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.openejb.config.sys.Resource;
import org.apache.openejb.config.typed.util.Builders;
import org.apache.openejb.config.typed.util.DurationAdapter;
import org.apache.openejb.util.Duration;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DataSource")
public class DataSourceBuilder extends Resource {

    @XmlAttribute
    private String serviceId;
    @XmlAttribute
    private String definition;
    @XmlAttribute
    private boolean jtaManaged = true;
    @XmlAttribute
    private String jdbcDriver = "org.hsqldb.jdbcDriver";
    @XmlAttribute
    private URI jdbcUrl = URI.create("jdbc:hsqldb:mem:hsqldb");
    @XmlAttribute
    private String userName = "sa";
    @XmlAttribute
    private String password;
    @XmlAttribute
    private String passwordCipher = "PlainText";
    @XmlAttribute
    private String connectionProperties;
    @XmlAttribute
    private boolean defaultAutoCommit = true;
    @XmlAttribute
    private String defaultReadOnly;
    @XmlAttribute
    private int initialSize;
    @XmlAttribute
    private int maxActive = 20;
    @XmlAttribute
    private int maxIdle = 20;
    @XmlAttribute
    private int minIdle;
    @XmlJavaTypeAdapter(DurationAdapter.class)
    @XmlAttribute
    private Duration maxWaitTime = Duration.parse("-1 millisecond");
    @XmlAttribute
    private String validationQuery;
    @XmlAttribute
    private boolean testOnBorrow = true;
    @XmlAttribute
    private boolean testOnReturn;
    @XmlAttribute
    private boolean testWhileIdle;
    @XmlJavaTypeAdapter(DurationAdapter.class)
    @XmlAttribute
    private Duration timeBetweenEvictionRuns = Duration.parse("-1 millisecond");
    @XmlAttribute
    private int numTestsPerEvictionRun = 3;
    @XmlJavaTypeAdapter(DurationAdapter.class)
    @XmlAttribute
    private Duration minEvictableIdleTime = Duration.parse("30 minutes");
    @XmlAttribute
    private boolean poolPreparedStatements;
    @XmlAttribute
    private int maxOpenPreparedStatements;
    @XmlAttribute
    private boolean accessToUnderlyingConnectionAllowed;
    @XmlAttribute
    private boolean ignoreDefaultValues;

    public DataSourceBuilder() {
        setClassName("org.apache.openejb.resource.jdbc.DataSourceFactory");
        setType("javax.sql.DataSource");
        setId("DataSource");

        setConstructor(
                "serviceId, jtaManaged, jdbcDriver, definition, maxWaitTime, timeBetweenEvictionRuns, minEvictableIdleTime");

        setFactoryName("create");

    }

    public DataSourceBuilder id(final String id) {
        setId(id);
        return this;
    }

    public DataSourceBuilder withServiceId(final String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(final String serviceId) {
        this.serviceId = serviceId;
    }

    public DataSourceBuilder withDefinition(final String definition) {
        this.definition = definition;
        return this;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(final String definition) {
        this.definition = definition;
    }

    public DataSourceBuilder withJtaManaged(final boolean jtaManaged) {
        this.jtaManaged = jtaManaged;
        return this;
    }

    public boolean getJtaManaged() {
        return jtaManaged;
    }

    public void setJtaManaged(final boolean jtaManaged) {
        this.jtaManaged = jtaManaged;
    }

    public DataSourceBuilder withJdbcDriver(final String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
        return this;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(final String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public DataSourceBuilder withJdbcUrl(final URI jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
        return this;
    }

    public URI getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(final URI jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public DataSourceBuilder withUserName(final String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public DataSourceBuilder withPassword(final String password) {
        this.password = password;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public DataSourceBuilder withPasswordCipher(final String passwordCipher) {
        this.passwordCipher = passwordCipher;
        return this;
    }

    public String getPasswordCipher() {
        return passwordCipher;
    }

    public void setPasswordCipher(final String passwordCipher) {
        this.passwordCipher = passwordCipher;
    }

    public DataSourceBuilder withConnectionProperties(final String connectionProperties) {
        this.connectionProperties = connectionProperties;
        return this;
    }

    public String getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(final String connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public DataSourceBuilder withDefaultAutoCommit(final boolean defaultAutoCommit) {
        this.defaultAutoCommit = defaultAutoCommit;
        return this;
    }

    public boolean getDefaultAutoCommit() {
        return defaultAutoCommit;
    }

    public void setDefaultAutoCommit(final boolean defaultAutoCommit) {
        this.defaultAutoCommit = defaultAutoCommit;
    }

    public DataSourceBuilder withDefaultReadOnly(final String defaultReadOnly) {
        this.defaultReadOnly = defaultReadOnly;
        return this;
    }

    public String getDefaultReadOnly() {
        return defaultReadOnly;
    }

    public void setDefaultReadOnly(final String defaultReadOnly) {
        this.defaultReadOnly = defaultReadOnly;
    }

    public DataSourceBuilder withInitialSize(final int initialSize) {
        this.initialSize = initialSize;
        return this;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(final int initialSize) {
        this.initialSize = initialSize;
    }

    public DataSourceBuilder withMaxActive(final int maxActive) {
        this.maxActive = maxActive;
        return this;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(final int maxActive) {
        this.maxActive = maxActive;
    }

    public DataSourceBuilder withMaxIdle(final int maxIdle) {
        this.maxIdle = maxIdle;
        return this;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(final int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public DataSourceBuilder withMinIdle(final int minIdle) {
        this.minIdle = minIdle;
        return this;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(final int minIdle) {
        this.minIdle = minIdle;
    }

    public DataSourceBuilder withMaxWaitTime(final Duration maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
        return this;
    }

    public Duration getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(final Duration maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public DataSourceBuilder withMaxWaitTime(final long time, final TimeUnit unit) {
        return withMaxWaitTime(new Duration(time, unit));
    }

    public void setMaxWaitTime(final long time, final TimeUnit unit) {
        setMaxWaitTime(new Duration(time, unit));
    }

    public DataSourceBuilder withValidationQuery(final String validationQuery) {
        this.validationQuery = validationQuery;
        return this;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(final String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public DataSourceBuilder withTestOnBorrow(final boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
        return this;
    }

    public boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(final boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public DataSourceBuilder withTestOnReturn(final boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
        return this;
    }

    public boolean getTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(final boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public DataSourceBuilder withTestWhileIdle(final boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
        return this;
    }

    public boolean getTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(final boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public DataSourceBuilder withTimeBetweenEvictionRuns(final Duration timeBetweenEvictionRuns) {
        this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
        return this;
    }

    public Duration getTimeBetweenEvictionRuns() {
        return timeBetweenEvictionRuns;
    }

    public void setTimeBetweenEvictionRuns(final Duration timeBetweenEvictionRuns) {
        this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
    }

    public DataSourceBuilder withTimeBetweenEvictionRuns(final long time, final TimeUnit unit) {
        return withTimeBetweenEvictionRuns(new Duration(time, unit));
    }

    public void setTimeBetweenEvictionRuns(final long time, final TimeUnit unit) {
        setTimeBetweenEvictionRuns(new Duration(time, unit));
    }

    public DataSourceBuilder withNumTestsPerEvictionRun(final int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
        return this;
    }

    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(final int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public DataSourceBuilder withMinEvictableIdleTime(final Duration minEvictableIdleTime) {
        this.minEvictableIdleTime = minEvictableIdleTime;
        return this;
    }

    public Duration getMinEvictableIdleTime() {
        return minEvictableIdleTime;
    }

    public void setMinEvictableIdleTime(final Duration minEvictableIdleTime) {
        this.minEvictableIdleTime = minEvictableIdleTime;
    }

    public DataSourceBuilder withMinEvictableIdleTime(final long time, final TimeUnit unit) {
        return withMinEvictableIdleTime(new Duration(time, unit));
    }

    public void setMinEvictableIdleTime(final long time, final TimeUnit unit) {
        setMinEvictableIdleTime(new Duration(time, unit));
    }

    public DataSourceBuilder withPoolPreparedStatements(final boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
        return this;
    }

    public boolean getPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(final boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public DataSourceBuilder withMaxOpenPreparedStatements(final int maxOpenPreparedStatements) {
        this.maxOpenPreparedStatements = maxOpenPreparedStatements;
        return this;
    }

    public int getMaxOpenPreparedStatements() {
        return maxOpenPreparedStatements;
    }

    public void setMaxOpenPreparedStatements(final int maxOpenPreparedStatements) {
        this.maxOpenPreparedStatements = maxOpenPreparedStatements;
    }

    public DataSourceBuilder withAccessToUnderlyingConnectionAllowed(
            final boolean accessToUnderlyingConnectionAllowed) {
        this.accessToUnderlyingConnectionAllowed = accessToUnderlyingConnectionAllowed;
        return this;
    }

    public boolean getAccessToUnderlyingConnectionAllowed() {
        return accessToUnderlyingConnectionAllowed;
    }

    public void setAccessToUnderlyingConnectionAllowed(final boolean accessToUnderlyingConnectionAllowed) {
        this.accessToUnderlyingConnectionAllowed = accessToUnderlyingConnectionAllowed;
    }

    public DataSourceBuilder withIgnoreDefaultValues(final boolean ignoreDefaultValues) {
        this.ignoreDefaultValues = ignoreDefaultValues;
        return this;
    }

    public boolean getIgnoreDefaultValues() {
        return ignoreDefaultValues;
    }

    public void setIgnoreDefaultValues(final boolean ignoreDefaultValues) {
        this.ignoreDefaultValues = ignoreDefaultValues;
    }

    public Properties getProperties() {
        return Builders.getProperties(this);
    }

}
