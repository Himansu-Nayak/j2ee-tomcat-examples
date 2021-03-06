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

package org.apache.openejb.core.timer.quartz;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import org.apache.openejb.core.rmi.BlacklistClassResolver;
import org.apache.openejb.quartz.spi.ClassLoadHelper;

public class QuartzObjectInputStream extends ObjectInputStream {
    private final ClassLoadHelper loader;

    public QuartzObjectInputStream(final InputStream binaryInput, final ClassLoadHelper classLoadHelper)
            throws IOException {
        super(binaryInput);
        this.loader = classLoadHelper;
    }

    @Override
    protected Class<?> resolveClass(final ObjectStreamClass desc) throws ClassNotFoundException, IOException {
        return loader.loadClass(BlacklistClassResolver.DEFAULT.check(desc.getName()));
    }
}
