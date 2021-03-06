/**
 *
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
package org.apache.openejb.test.entity.cmr.onetomany;

public class ArtistPk {
    public Integer id;
    public String name;

    public ArtistPk() {
    }

    public ArtistPk(final Integer id, final String name) {
        this.id = id;
        this.name = name;
    }

    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final ArtistPk artistPk = (ArtistPk) o;

        return id.equals(artistPk.id) && name.equals(artistPk.name);
    }

    public int hashCode() {
        int result;
        result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
