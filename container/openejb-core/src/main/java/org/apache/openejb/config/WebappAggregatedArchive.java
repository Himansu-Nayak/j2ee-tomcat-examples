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

package org.apache.openejb.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import org.apache.xbean.finder.archive.Archive;
import org.apache.xbean.finder.archive.CompositeArchive;
import org.apache.xbean.finder.archive.FilteredArchive;
import org.apache.xbean.finder.filter.Filter;

public class WebappAggregatedArchive implements Archive, ScanConstants {
    private final Map<URL, List<String>> map = new HashMap<URL, List<String>>();
    private final Archive archive;
    private ScanUtil.ScanHandler handler;
    private boolean scanXmlExists; // faster than using an empty handler

    public WebappAggregatedArchive(final Module module, final Iterable<URL> urls, final Filter filter) {
        final List<Archive> archives = new ArrayList<Archive>();

        final URL scanXml = (URL) module.getAltDDs().get(ScanConstants.SCAN_XML_NAME);
        if (scanXml != null) {
            try {
                handler = ScanUtil.read(scanXml);
                scanXmlExists = true;
            } catch (final IOException e) {
                // ignored, will not use filtering with scan.xml
            }
        }

        for (final URL url : urls) {
            final List<String> classes = new ArrayList<String>();
            final Archive archive = new FilteredArchive(
                    new ConfigurableClasspathArchive(module.getClassLoader(), Arrays.asList(url)),
                    new ScanXmlSaverFilter(scanXmlExists, handler, classes, filter));
            map.put(url, classes);
            archives.add(archive);
        }

        archive = new CompositeArchive(archives);
    }

    public WebappAggregatedArchive(final Module module, final Iterable<URL> urls) {
        this(module, urls, null);
    }

    public WebappAggregatedArchive(final ClassLoader classLoader, final Map<String, Object> altDDs,
            final Collection<URL> xmls) {
        this(new ConfigurableClasspathArchive.FakeModule(classLoader, altDDs), xmls);
    }

    public Map<URL, List<String>> getClassesMap() {
        return map;
    }

    @Override
    public InputStream getBytecode(final String className) throws IOException, ClassNotFoundException {
        return archive.getBytecode(className);
    }

    @Override
    public Class<?> loadClass(final String className) throws ClassNotFoundException {
        return archive.loadClass(className);
    }

    @Override
    public Iterator<Entry> iterator() {
        return archive.iterator();
    }

    public static class ScanXmlSaverFilter implements Filter {
        private final boolean scanXmlExists;
        private final ScanUtil.ScanHandler handler;
        private final List<String> classes;
        private final Filter otherFilter;

        public ScanXmlSaverFilter(final boolean scanXmlExists, final ScanUtil.ScanHandler handler,
                final List<String> classes, final Filter otherFilter) {
            this.scanXmlExists = scanXmlExists;
            this.handler = handler;
            this.classes = classes;
            this.otherFilter = otherFilter;
        }

        @Override
        public boolean accept(final String name) {
            final boolean accept = otherFilter == null || otherFilter.accept(name);
            if (scanXmlExists) {
                for (final String packageName : handler.getPackages()) {
                    if (name.startsWith(packageName) && accept) {
                        classes.add(name);
                        return true;
                    }
                }
                for (final String className : handler.getClasses()) {
                    if (className.equals(name) && accept) {
                        classes.add(name);
                        return true;
                    }
                }
                return false;
            }
            if (accept) {
                classes.add(name);
            }
            return accept;
        }
    }
}
