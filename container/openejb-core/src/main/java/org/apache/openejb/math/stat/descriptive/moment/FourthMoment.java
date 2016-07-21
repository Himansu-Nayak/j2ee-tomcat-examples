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

package org.apache.openejb.math.stat.descriptive.moment;

import java.io.Serializable;

/**
 * Computes a statistic related to the Fourth Central Moment. Specifically, what is computed is the sum of
 * <p>
 * (x_i - xbar) ^ 4,
 * </p>
 * <p>
 * where the x_i are the sample observations and xbar is the sample mean.
 * </p>
 * <p>
 * The following recursive updating formula is used:
 * </p>
 * <p>
 * Let
 * <ul>
 * <li>dev = (current obs - previous mean)</li>
 * <li>m2 = previous value of {@link SecondMoment}</li>
 * <li>m2 = previous value of {@link ThirdMoment}</li>
 * <li>n = number of observations (including current obs)</li>
 * </ul>
 * Then
 * </p>
 * <p>
 * new value = old value - 4 * (dev/n) * m3 + 6 * (dev/n)^2 * m2 + <br>
 * [n^2 - 3 * (n-1)] * dev^4 * (n-1) / n^3
 * </p>
 * <p>
 * Returns <code>Double.NaN</code> if no data values have been added and returns <code>0</code> if there is just one
 * value in the data set.
 * </p>
 * <p>
 * <strong>Note that this implementation is not synchronized.</strong> If multiple threads access an instance of this
 * class concurrently, and at least one of the threads invokes the <code>increment()</code> or <code>clear()</code>
 * method, it must be synchronized externally.
 * </p>
 *
 * @version $Revision: 811685 $ $Date: 2009-09-05 10:36:48 -0700 (Sat, 05 Sep 2009) $
 */
public class FourthMoment extends ThirdMoment implements Serializable {

    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 1233990447117157611L;

    /**
     * fourth moment of values that have been added
     */
    protected double m4;

    /**
     * Create a FourthMoment instance
     */
    public FourthMoment() {
        super();
        m4 = Double.NaN;
    }

    /**
     * Copy constructor, creates a new {@code FourthMoment} identical to the {@code original}
     *
     * @param original
     *            the {@code FourthMoment} instance to copy
     */
    public FourthMoment(final FourthMoment original) {
        super();
        copy(original, this);
    }

    /**
     * Copies source to dest.
     * <p>
     * Neither source nor dest can be null.
     * </p>
     *
     * @param source
     *            FourthMoment to copy
     * @param dest
     *            FourthMoment to copy to
     * @throws NullPointerException
     *             if either source or dest is null
     */
    public static void copy(final FourthMoment source, final FourthMoment dest) {
        ThirdMoment.copy(source, dest);
        dest.m4 = source.m4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increment(final double d) {
        if (n < 1) {
            m4 = 0.0;
            m3 = 0.0;
            m2 = 0.0;
            m1 = 0.0;
        }

        final double prevM3 = m3;
        final double prevM2 = m2;

        super.increment(d);

        final double n0 = n;

        m4 = m4 - 4.0 * nDev * prevM3 + 6.0 * nDevSq * prevM2
                + (n0 * n0 - 3 * (n0 - 1)) * nDevSq * nDevSq * (n0 - 1) * n0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getResult() {
        return m4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        super.clear();
        m4 = Double.NaN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FourthMoment copy() {
        final FourthMoment result = new FourthMoment();
        copy(this, result);
        return result;
    }
}
