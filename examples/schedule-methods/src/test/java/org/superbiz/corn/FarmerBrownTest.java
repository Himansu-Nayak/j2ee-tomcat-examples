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
package org.superbiz.corn;

import static java.util.concurrent.TimeUnit.SECONDS;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import junit.framework.TestCase;

/**
 * @version $Revision$ $Date$
 */
public class FarmerBrownTest extends TestCase {

    public void test() throws Exception {

        final Context context = EJBContainer.createEJBContainer().getContext();

        final FarmerBrown farmerBrown = (FarmerBrown) context.lookup("java:global/schedule-methods/FarmerBrown");

        // Give Farmer brown a chance to do some work
        Thread.sleep(SECONDS.toMillis(5));

        assertTrue(farmerBrown.getChecks() > 4);
    }
}
