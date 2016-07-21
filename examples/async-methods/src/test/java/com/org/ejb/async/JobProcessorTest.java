/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.org.ejb.async;

import static org.junit.Assert.*;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.Test;

public class JobProcessorTest {

    @Test
    public void test() throws Exception {
        final String BASE_JNDI_CONTEXT = "java:global";
        final Context context = EJBContainer.createEJBContainer().getContext();

        final JobProcessor processor = (JobProcessor) context.lookup(BASE_JNDI_CONTEXT + "/async-methods/JobProcessor");

        final long start = System.nanoTime();

        // Queue up a bunch of work
        final Future<String> red = processor.addJob("red");
        final Future<String> orange = processor.addJob("orange");
        final Future<String> yellow = processor.addJob("yellow");
        final Future<String> green = processor.addJob("green");
        final Future<String> blue = processor.addJob("blue");
        final Future<String> violet = processor.addJob("violet");

        // Wait for the result -- 1 minute worth of work
        assertEquals("Blue Match", "blue", blue.get());
        assertEquals("Orange Match", "orange", orange.get());
        assertEquals("Green Match", "green", green.get());
        assertEquals("Red Match", "red", red.get());
        assertEquals("Yellow Match", "yellow", yellow.get());
        assertEquals("Violet Match", "violet", violet.get());

        // How long did it take?
        final long total = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start);

        // Execution should be around 9 - 21 seconds
        // The execution time depends on the number of threads available for asynchronous execution.
        // In the best case it is 10s plus some minimal processing time.
        assertTrue("Expected > 9 but was: " + total, total > 9);
        assertTrue("Expected < 21 but was: " + total, total < 21);

    }

}
