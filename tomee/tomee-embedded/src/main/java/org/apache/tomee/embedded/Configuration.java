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
package org.apache.tomee.embedded;

import java.io.File;
import java.util.*;

import org.apache.catalina.Realm;
import org.apache.catalina.connector.Connector;
import org.apache.openejb.util.NetworkUtil;

/**
 * @version $Rev$ $Date$
 */
public class Configuration {

    private final Collection<Connector> connectors = new ArrayList<>();
    private final Collection<ConfigurationCustomizer> customizers = new ArrayList<>();
    protected String dir;
    private int httpPort = 8080;
    private int stopPort = 8005;
    private String host = "localhost";
    private File serverXml;
    private boolean keepServerXmlAsThis;
    private Properties properties;
    private boolean quickSession = true;
    private boolean skipHttp;
    private int httpsPort = 8443;
    private boolean ssl;
    private boolean withEjbRemote;
    private String keystoreFile;
    private String keystorePass;
    private String keystoreType = "JKS";
    private String clientAuth;
    private String keyAlias;
    private String sslProtocol;
    private String webXml;
    private LoginConfigBuilder loginConfig;
    private Collection<SecurityConstaintBuilder> securityConstraints = new LinkedList<>();
    private Realm realm;
    private boolean deployOpenEjbApp;
    private Map<String, String> users;
    private Map<String, String> roles;
    private boolean http2;
    /**
     * when needed temp file only (deployClasspathAsWebapp() for instance)
     */
    private String tempDir = new File(System.getProperty("java.io.tmpdir"),
            "tomee-embedded_" + System.currentTimeMillis()).getAbsolutePath();

    private boolean webResourceCached = true;

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(final int httpPort) {
        this.httpPort = httpPort;
    }

    public Configuration randomHttpPort() {
        this.httpPort = NetworkUtil.getNextAvailablePort();
        return this;
    }

    public int getStopPort() {
        return stopPort;
    }

    public void setStopPort(final int stopPort) {
        this.stopPort = stopPort;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(final String dir) {
        this.dir = dir;
    }

    public Configuration dir(final String dir) {
        setDir(dir);
        return this;
    }

    public boolean isWithEjbRemote() {
        return withEjbRemote;
    }

    public void setWithEjbRemote(final boolean withEjbRemote) {
        this.withEjbRemote = withEjbRemote;
    }

    public Configuration withEjbRemote(final boolean withEjbRemote) {
        setWithEjbRemote(withEjbRemote);
        return this;
    }

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public int getHttpsPort() {
        return httpsPort;
    }

    public void setHttpsPort(final int httpsPort) {
        this.httpsPort = httpsPort;
    }

    public String getKeystoreFile() {
        return keystoreFile;
    }

    public void setKeystoreFile(final String keystoreFile) {
        this.keystoreFile = keystoreFile;
    }

    public String getKeystorePass() {
        return keystorePass;
    }

    public void setKeystorePass(final String keystorePass) {
        this.keystorePass = keystorePass;
    }

    public String getKeystoreType() {
        return keystoreType;
    }

    public void setKeystoreType(final String keystoreType) {
        this.keystoreType = keystoreType;
    }

    public String getClientAuth() {
        return clientAuth;
    }

    public void setClientAuth(final String clientAuth) {
        this.clientAuth = clientAuth;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public void setKeyAlias(final String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public void setServerXml(final String file) {
        if (file == null) {
            serverXml = null;
        } else {
            final File sXml = new File(file);
            if (sXml.exists()) {
                serverXml = sXml;
            }
        }
    }

    public File getServerXmlFile() {
        return serverXml;
    }

    public boolean hasServerXml() {
        return serverXml != null && serverXml.exists();
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(final Properties properties) {
        this.properties = properties;
    }

    public boolean isQuickSession() {
        return quickSession;
    }

    public void setQuickSession(final boolean quickSession) {
        this.quickSession = quickSession;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(final boolean ssl) {
        this.ssl = ssl;
    }

    public boolean isSkipHttp() {
        return skipHttp;
    }

    public void setSkipHttp(final boolean skipHttp) {
        this.skipHttp = skipHttp;
    }

    public String getSslProtocol() {
        return sslProtocol;
    }

    public void setSslProtocol(final String sslProtocol) {
        this.sslProtocol = sslProtocol;
    }

    public Configuration property(final String key, final String value) {
        if (properties == null) {
            properties = new Properties();
        }
        properties.setProperty(key, value);
        return this;
    }

    public String getTempDir() {
        return tempDir;
    }

    public void setTempDir(final String tempDir) {
        this.tempDir = tempDir;
    }

    public boolean isDeployOpenEjbApp() {
        return deployOpenEjbApp;
    }

    public void setDeployOpenEjbApp(final boolean deployOpenEjbApp) {
        this.deployOpenEjbApp = deployOpenEjbApp;
    }

    public Configuration http(final int port) {
        setHttpPort(port);
        return this;
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public void setUsers(final Map<String, String> users) { // useful for tools like maven plugin
        this.users = users;
    }

    public Map<String, String> getRoles() {
        return roles;
    }

    public void setRoles(final Map<String, String> roles) {
        this.roles = roles;
    }

    public boolean isKeepServerXmlAsThis() {
        return keepServerXmlAsThis;
    }

    public void setKeepServerXmlAsThis(final boolean keepServerXmlAsThis) {
        this.keepServerXmlAsThis = keepServerXmlAsThis;
    }

    public Configuration user(final String name, final String pwd) {
        if (users == null) {
            users = new HashMap<>();
        }
        this.users.put(name, pwd);
        return this;
    }

    public Configuration role(final String user, final String roles) {
        if (this.roles == null) {
            this.roles = new HashMap<>();
        }
        this.roles.put(user, roles);
        return this;
    }

    public String getWebXml() {
        return webXml;
    }

    public Configuration setWebXml(final String webXml) {
        this.webXml = webXml;
        return this;
    }

    public LoginConfigBuilder getLoginConfig() {
        return loginConfig;
    }

    public Configuration loginConfig(final LoginConfigBuilder loginConfig) {
        this.loginConfig = loginConfig;
        return this;
    }

    public Collection<SecurityConstaintBuilder> getSecurityConstraints() {
        return securityConstraints;
    }

    public Configuration securityConstaint(final SecurityConstaintBuilder constraint) {
        securityConstraints.add(constraint);
        return this;
    }

    public Realm getRealm() {
        return realm;
    }

    public Configuration setRealm(final Realm realm) {
        this.realm = realm;
        return this;
    }

    public boolean areWebResourcesCached() {
        return webResourceCached;
    }

    public void setWebResourceCached(boolean cached) {
        this.webResourceCached = cached;
    }

    public boolean isHttp2() {
        return http2;
    }

    public void setHttp2(final boolean http2) {
        this.http2 = http2;
    }

    public Collection<Connector> getConnectors() {
        return connectors;
    }

    public void addCustomizer(final ConfigurationCustomizer configurationCustomizer) {
        configurationCustomizer.customize(this);
    }

    public interface ConfigurationCustomizer {
        void customize(Configuration configuration);
    }
}
