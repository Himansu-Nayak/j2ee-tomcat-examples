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
package org.apache.ziplock.maven;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jboss.shrinkwrap.api.Archive;

public final class PreloadableTestWar {
    private static final Future<Archive<?>> war;

    static {
        final ExecutorService es = Executors.newSingleThreadExecutor();
        war = es.submit(new Callable<Archive<?>>() {
            @Override
            public Archive<?> call() throws Exception {
                return Mvn.testWar();
            }
        });
        es.shutdown();
    }

    private PreloadableTestWar() {
        // no-op
    }

    public static Archive<?> war() {
        try {
            return war.get();
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
