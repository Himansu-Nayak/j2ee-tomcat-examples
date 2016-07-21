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
package org.apache.openejb.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.concurrent.*;

import junit.framework.TestCase;

public class AsynchronousRunnerTest extends TestCase {

    /**
     * Tests the cancel method
     */
    public void testCancel() throws Exception {
        final CountDownLatch cdl = new CountDownLatch(1);
        final AsynchronousRunner asyncRunner = instantiate(cdl);
        final Future<Object> future = asyncRunner.runAsync(object(), method(), arguments());
        future.cancel(true);
        assertTrue(future.isCancelled());
        try {
            future.get();
            fail();
        } catch (final CancellationException e) {
            //Ok
        }
    }

    /**
     * A delayed get, sleeping until the result is complete
     */
    public void testDelayedGet() throws Exception {
        final CountDownLatch cdl = new CountDownLatch(1);
        final AsynchronousRunner asyncRunner = instantiate(cdl);
        final Future<Object> future = asyncRunner.runAsync(object(), method(), arguments());
        assertFalse(future.isDone());
        cdl.countDown();
        //Give some time for the execution to finish
        Thread.sleep(500);
        assertTrue(future.isDone());
        assertEquals(expected(), future.get());
    }

    /**
     * A regular get, waiting for result to be complete
     */
    public void testGet() throws Exception {
        final CountDownLatch cdl = new CountDownLatch(0);
        final AsynchronousRunner asyncRunner = instantiate(cdl);
        final Future<Object> future = asyncRunner.runAsync(object(), method(), arguments());
        assertEquals(expected(), future.get());
    }

    /**
     * A get with max timeout
     */
    public void testTimedGet() throws Exception {
        final CountDownLatch cdl = new CountDownLatch(1);
        final AsynchronousRunner asyncRunner = instantiate(cdl);
        final Future<Object> future = asyncRunner.runAsync(object(), method(), arguments());
        try {
            future.get(1, TimeUnit.SECONDS);
            fail();
        } catch (final TimeoutException e) {
            //Ok
        }
        cdl.countDown();
        //Give some time for the execution to finish
        Thread.sleep(500);
        assertTrue(future.isDone());
        assertEquals(expected(), future.get());
    }

    private Object[] arguments() {
        return new Object[] { BigDecimal.ONE };
    }

    private BigDecimal expected() {
        return new BigDecimal(11);
    }

    private AsynchronousRunner instantiate(final CountDownLatch cdl) {
        return new AsynchronousRunner(new TestExecutor(cdl));
    }

    private Method method() {
        try {
            return BigDecimal.class.getMethod("add", BigDecimal.class);
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private Object object() {
        return BigDecimal.TEN;
    }

    private static class TestExecutor implements Executor {

        private final CountDownLatch countDownLatch;

        public TestExecutor(final CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        public void execute(final Runnable command) {
            final Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        countDownLatch.await();
                    } catch (final InterruptedException e) {
                        throw new IllegalStateException(e);
                    }
                    command.run();
                }
            };
            new Thread(runnable).start();
        }
    }

}
