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
package org.apache.openejb.arquillian.openejb.reflection;

import java.lang.reflect.Field;
import java.net.URL;

import org.jboss.shrinkwrap.api.asset.Asset;

public final class Assets {
    public static final ClassLoader EMPTY_LOADER = new ClassLoader() {
        @Override
        public URL getResource(final String name) {
            return null;
        }
    };

    private Assets() {
        // no-op
    }

    public static <T> T get(final Class<T> fileClass, final String attr, final Asset asset) {
        try {
            final Field field = asset.getClass().getDeclaredField(attr);
            field.setAccessible(true);
            return fileClass.cast(field.get(asset));
        } catch (final Exception e) {
            return null;
        }
    }
}
