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
package org.apache.openejb.client;

import java.net.URI;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.openejb.client.event.FailoverSelection;
import org.apache.openejb.client.event.StickyFailoverSelection;

public class StickyConnectionStrategy extends AbstractConnectionStrategy {

    private final AbstractConnectionStrategy secondaryConnectionStrategy;

    public StickyConnectionStrategy() {
        this(new RoundRobinConnectionStrategy());
    }

    public StickyConnectionStrategy(final AbstractConnectionStrategy secondaryConnectionStrategy) {
        this.secondaryConnectionStrategy = secondaryConnectionStrategy;
    }

    public AbstractConnectionStrategy getSecondaryConnectionStrategy() {
        return secondaryConnectionStrategy;
    }

    @Override
    protected FailoverSelection createFailureEvent(final Set<URI> remaining, final Set<URI> failed, final URI uri) {
        return new StickyFailoverSelection(remaining, failed, uri);
    }

    @Override
    protected Iterable<URI> createIterable(final ClusterMetaData cluster) {
        return new StickyIterable(cluster);
    }

    public class StickyIterable implements Iterable<URI> {

        private final ClusterMetaData cluster;
        private final Iterable<URI> iterable;

        public StickyIterable(final ClusterMetaData cluster) {
            this.cluster = cluster;
            this.iterable = secondaryConnectionStrategy.createIterable(cluster);
        }

        @Override
        public Iterator<URI> iterator() {
            return new StickyIterator();
        }

        public class StickyIterator implements Iterator<URI> {

            private Iterator<URI> iterator;
            private URI last;
            private boolean first = true;

            private StickyIterator() {
                setLast(cluster.getLastLocation());
            }

            private void setLast(final URI lastLocation) {
                last = lastLocation;
            }

            @Override
            public boolean hasNext() {
                return first && last != null || getIterator().hasNext();
            }

            @Override

            public URI next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                if (first && last != null) {
                    first = false;
                    return last;
                }

                final Iterator<URI> iterator = getIterator();

                return iterator.next();
            }

            private Iterator<URI> getIterator() {
                if (iterator == null) {
                    iterator = iterable.iterator();
                }
                return iterator;
            }

            @Override
            public void remove() {
            }
        }
    }

}
