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
package org.apache.openejb.maven.plugin.customizer.monkey.index;

import java.io.File;

public class Item implements Comparable<Item> {
    private final String path;
    private final File file;
    private final Action action;
    Item(final String path, final File file, final Action action) {
        this.path = path;
        this.file = file;
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    @Override
    public int compareTo(final Item o) {
        return file.compareTo(o.file);
    }

    public enum Action {
        ADD_OR_UPDATE, REMOVE;
    }
}
