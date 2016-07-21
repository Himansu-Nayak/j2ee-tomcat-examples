/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.openejb.server.axis;

public class EchoStruct {
    private int intVal;
    private double doubleVal;
    private byte[] bytesVal;
    private float floatVal;
    private long longVal;
    private short shortVal;
    private boolean boolaenVal;

    private String strVal;

    private int[] intaVal;
    private double[] doubleaVal;
    private byte[][] bytesaVal;
    private float[] floataVal;
    private long[] longaVal;
    private short[] shortaVal;
    private boolean[] boolaenaVal;

    private String[] straVal;

    private SmallEchoStruct sturctVal;
    private SmallEchoStruct sturctaVal;

    public boolean[] getBoolaenaVal() {
        return boolaenaVal;
    }

    public void setBoolaenaVal(boolean[] bs) {
        boolaenaVal = bs;
    }

    public boolean isBoolaenVal() {
        return boolaenVal;
    }

    public void setBoolaenVal(boolean b) {
        boolaenVal = b;
    }

    public byte[][] getBytesaVal() {
        return bytesaVal;
    }

    public void setBytesaVal(byte[][] bs) {
        bytesaVal = bs;
    }

    public byte[] getBytesVal() {
        return bytesVal;
    }

    public void setBytesVal(byte[] bs) {
        bytesVal = bs;
    }

    public double[] getDoubleaVal() {
        return doubleaVal;
    }

    public void setDoubleaVal(double[] ds) {
        doubleaVal = ds;
    }

    public double getDoubleVal() {
        return doubleVal;
    }

    public void setDoubleVal(double d) {
        doubleVal = d;
    }

    public float[] getFloataVal() {
        return floataVal;
    }

    public void setFloataVal(float[] fs) {
        floataVal = fs;
    }

    public float getFloatVal() {
        return floatVal;
    }

    public void setFloatVal(float f) {
        floatVal = f;
    }

    public int[] getIntaVal() {
        return intaVal;
    }

    public void setIntaVal(int[] is) {
        intaVal = is;
    }

    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int i) {
        intVal = i;
    }

    public long[] getLongaVal() {
        return longaVal;
    }

    public void setLongaVal(long[] ls) {
        longaVal = ls;
    }

    public long getLongVal() {
        return longVal;
    }

    public void setLongVal(long l) {
        longVal = l;
    }

    public short[] getShortaVal() {
        return shortaVal;
    }

    public void setShortaVal(short[] ses) {
        shortaVal = ses;
    }

    public short getShortVal() {
        return shortVal;
    }

    public void setShortVal(short s) {
        shortVal = s;
    }

    public String[] getStraVal() {
        return straVal;
    }

    public void setStraVal(String[] strings) {
        straVal = strings;
    }

    public String getStrVal() {
        return strVal;
    }

    public void setStrVal(String string) {
        strVal = string;
    }

    public SmallEchoStruct getSturctaVal() {
        return sturctaVal;
    }

    public void setSturctaVal(SmallEchoStruct struct) {
        sturctaVal = struct;
    }

    public SmallEchoStruct getSturctVal() {
        return sturctVal;
    }

    public void setSturctVal(SmallEchoStruct struct) {
        sturctVal = struct;
    }
}
