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

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.Arrays;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Tie;
import javax.rmi.PortableRemoteObject;

import org.apache.openejb.client.serializer.EJBDSerializer;
import org.apache.openejb.client.serializer.SerializationWrapper;
import org.omg.CORBA.ORB;

public class EJBRequest implements ClusterableRequest {

    public static final int SESSION_BEAN_STATELESS = 6;
    public static final int SESSION_BEAN_STATEFUL = 7;
    public static final int ENTITY_BM_PERSISTENCE = 8;
    public static final int ENTITY_CM_PERSISTENCE = 9;
    private static final long serialVersionUID = -2075915633178128028L;
    // Only visible on the client side
    private transient final EJBMetaDataImpl ejbMetaData;
    private transient RequestMethodCode requestMethod;
    private transient int deploymentCode = 0;
    private transient Object clientIdentity;
    private transient String deploymentId;
    private transient int serverHash;
    private transient Body body;
    private transient EJBDSerializer serializer;
    private transient ProtocolMetaData metaData;

    public EJBRequest() {
        body = new Body(null);
        ejbMetaData = null;
    }

    public EJBRequest(final RequestMethodCode requestMethod, final EJBMetaDataImpl ejb, final Method method,
            final Object[] args, final Object primaryKey, final EJBDSerializer serializer) {

        this.body = new Body(ejb);
        this.serializer = serializer;
        this.ejbMetaData = ejb;
        this.requestMethod = requestMethod;

        setDeploymentCode(ejb.deploymentCode);
        setDeploymentId(ejb.deploymentID);
        setMethodInstance(method);
        setMethodParameters(args);
        setPrimaryKey(primaryKey);
    }

    @Override
    public void setMetaData(final ProtocolMetaData metaData) {
        this.metaData = metaData;
        this.body.setMetaData(this.metaData);
    }

    public EJBMetaDataImpl getEjbMetaData() {
        return ejbMetaData;
    }

    public Class getInterfaceClass() {
        return body.getInterfaceClass();
    }

    public Method getMethodInstance() {
        return body.getMethodInstance();
    }

    public void setMethodInstance(final Method methodInstance) {
        body.setMethodInstance(methodInstance);
    }

    public String getMethodName() {
        return body.getMethodName();
    }

    public Object[] getMethodParameters() {
        final Object[] params = body.getMethodParameters();
        if (params == null || serializer == null) {
            return params;
        }

        final Object[] unserialized = new Object[params.length];
        int i = 0;
        for (final Object o : params) {
            if (SerializationWrapper.class.isInstance(o)) {
                final SerializationWrapper wrapper = SerializationWrapper.class.cast(o);
                try {
                    unserialized[i] = serializer.deserialize(wrapper.getData(), body.getMethodInstance()
                            .getDeclaringClass().getClassLoader().loadClass(wrapper.getClassname()));
                } catch (final ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                unserialized[i] = o;
            }
            i++;
        }
        return unserialized;
    }

    public void setMethodParameters(final Object[] methodParameters) {
        if (serializer == null || methodParameters == null) {
            body.setMethodParameters(methodParameters);
        } else {
            final Object[] params = new Object[methodParameters.length];
            int i = 0;
            for (final Object o : methodParameters) {
                if (o == null) {
                    params[i] = null;
                } else {
                    params[i] = new SerializationWrapper(serializer.serialize(o), o.getClass().getName());
                }
                i++;
            }
            body.setMethodParameters(params);
        }
    }

    public Class[] getMethodParamTypes() {
        return body.getMethodParamTypes();
    }

    public Object getPrimaryKey() {
        return body.getPrimaryKey();
    }

    public void setPrimaryKey(final Object primaryKey) {
        body.setPrimaryKey(primaryKey);
    }

    public Body getBody() {
        return body;
    }

    public void setBody(final Body body) {
        this.body = body;
    }

    public byte getVersion() {
        return this.body.getVersion();
    }

    public void setSerializer(final EJBDSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.EJB_REQUEST;
    }

    public RequestMethodCode getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(final RequestMethodCode requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Object getClientIdentity() {
        return clientIdentity;
    }

    public void setClientIdentity(final Object clientIdentity) {
        this.clientIdentity = clientIdentity;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(final String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public int getDeploymentCode() {
        return deploymentCode;
    }

    public void setDeploymentCode(final int deploymentCode) {
        this.deploymentCode = deploymentCode;
    }

    @Override
    public int getServerHash() {
        return serverHash;
    }

    @Override
    public void setServerHash(final int serverHash) {
        this.serverHash = serverHash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("EJBRequest{");
        sb.append("deploymentId='");
        sb.append(deploymentId);
        sb.append("'");

        if (requestMethod != null) {
            sb.append(", type=").append(requestMethod);
        }
        if (body != null) {
            sb.append(", ").append(body.toString());
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Changes to this method must observe the optional {@link #metaData} version
     * <p/>
     * When the Request externalizes itself, it will reset the appropriate values so that this instance can be used
     * again.
     * <p/>
     * There will be one request instance for each handler
     */
    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        ClassNotFoundException ex = null;

        deploymentId = null;
        deploymentCode = -1;
        clientIdentity = null;

        final int code = in.readByte();
        try {
            requestMethod = RequestMethodCode.valueOf(code);
        } catch (IllegalArgumentException iae) {
            throw new IOException("Invalid request code " + code);
        }
        try {
            deploymentId = (String) in.readObject();
        } catch (ClassNotFoundException cnfe) {
            ex = cnfe;
        }
        deploymentCode = in.readShort();
        try {
            clientIdentity = in.readObject();
        } catch (ClassNotFoundException cnfe) {
            if (ex == null) {
                ex = cnfe;
            }
        }
        serverHash = in.readInt();

        if (ex != null) {
            throw ex;
        }
    }

    /**
     * Write to server. WARNING: To maintain backwards compatibility never change the order or insert new writes, always
     * append to {@link org.apache.openejb.client.EJBRequest.Body#writeExternal(java.io.ObjectOutput)}
     *
     * @param out
     *            ObjectOutput
     * @throws IOException
     */
    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {

        out.writeByte(requestMethod.getCode());

        if (deploymentCode > 0) {
            out.writeObject(null);
        } else {
            out.writeObject(deploymentId);
        }

        out.writeShort(deploymentCode);
        out.writeObject(clientIdentity);
        out.writeInt(serverHash);
        out.flush();

        body.setMetaData(metaData);
        body.writeExternal(out);
    }

    public static class Body implements java.io.Externalizable {

        static final Class[] noArgsC = new Class[0];
        static final Object[] noArgsO = new Object[0];
        private static final long serialVersionUID = -5364100745236348268L;
        private static final int INT = 0;
        private static final int BYTE = 1;
        private static final int LONG = 2;
        private static final int FLOAT = 3;
        private static final int DOUBLE = 4;
        private static final int SHORT = 5;
        private static final int CHAR = 6;
        private static final int BOOLEAN = 7;
        private static final int OBJECT = 8;
        private transient volatile String toString = null;
        private transient EJBMetaDataImpl ejb;
        private transient ORB orb;
        private transient Method methodInstance;
        private transient Class interfaceClass;
        private transient String methodName;
        private transient Class[] methodParamTypes;
        private transient Object[] methodParameters;
        private transient Object primaryKey;
        private transient String requestId;
        private byte version = EJBResponse.VERSION;
        private transient JNDIContext.AuthenticationInfo authentication;
        private transient ProtocolMetaData metaData;

        public Body(final EJBMetaDataImpl ejb) {
            this.ejb = ejb;
        }

        public Body() {
        }

        public void setMetaData(final ProtocolMetaData metaData) {
            this.metaData = metaData;
        }

        public byte getVersion() {
            return version;
        }

        public JNDIContext.AuthenticationInfo getAuthentication() {
            return authentication;
        }

        public void setAuthentication(final JNDIContext.AuthenticationInfo authentication) {
            this.authentication = authentication;
        }

        public Method getMethodInstance() {
            return methodInstance;
        }

        @SuppressWarnings("unchecked")
        public void setMethodInstance(final Method methodInstance) {
            if (methodInstance == null) {
                throw new NullPointerException("methodInstance input parameter is null");
            }
            this.methodInstance = methodInstance;
            this.methodName = methodInstance.getName();
            this.methodParamTypes = methodInstance.getParameterTypes();
            final Class methodClass = methodInstance.getDeclaringClass();

            if (ejb.homeClass != null) {
                if (methodClass.isAssignableFrom(ejb.homeClass)) {
                    this.interfaceClass = ejb.homeClass;
                    return;
                }
            }

            if (ejb.remoteClass != null) {
                if (methodClass.isAssignableFrom(ejb.remoteClass)) {
                    this.interfaceClass = ejb.remoteClass;
                    return;
                }
            }

            for (final Class businessClass : ejb.businessClasses) {
                if (methodClass.isAssignableFrom(businessClass)) {
                    this.interfaceClass = businessClass;
                    return;
                }
            }
        }

        public Object[] getMethodParameters() {
            return methodParameters;
        }

        public void setMethodParameters(final Object[] methodParameters) {
            this.methodParameters = methodParameters;
        }

        public Object getPrimaryKey() {
            return primaryKey;
        }

        public void setPrimaryKey(final Object primaryKey) {
            this.primaryKey = primaryKey;
        }

        public Class getInterfaceClass() {
            return interfaceClass;
        }

        public String getMethodName() {
            return methodName;
        }

        public Class[] getMethodParamTypes() {
            return methodParamTypes;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(final String requestId) {
            this.requestId = requestId;
        }

        /**
         * Changes to this method must observe the optional {@link #metaData} version
         */
        @SuppressWarnings("unchecked")
        @Override
        public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {

            this.version = in.readByte();

            requestId = null;
            ClassNotFoundException result = null;
            primaryKey = null;
            methodName = null;
            methodInstance = null;
            try {
                requestId = (String) in.readObject();
                primaryKey = in.readObject();
                interfaceClass = (Class) in.readObject();
            } catch (ClassNotFoundException cnfe) {
                result = cnfe;
            }

            methodName = in.readUTF();

            try {
                readMethodParameters(in);
            } catch (ClassNotFoundException cnfe) {
                if (result == null) {
                    result = cnfe;
                }
            }

            if (interfaceClass != null) {
                try {
                    //noinspection unchecked
                    methodInstance = interfaceClass.getMethod(methodName, methodParamTypes);
                } catch (NoSuchMethodException nsme) {
                    if (result == null) {
                        throw new ClassNotFoundException(
                                interfaceClass.getSimpleName() + "#" + methodName + " is not valid");
                    }
                }
            }

            if (null == metaData || metaData.isAtLeast(4, 6)) {
                authentication = JNDIContext.AuthenticationInfo.class.cast(in.readObject());
            } else {
                authentication = null;
            }

            if (result != null) {
                throw result;
            }
        }

        /**
         * Changes to this method must observe the optional {@link #metaData} version
         */
        @Override
        public void writeExternal(final ObjectOutput out) throws IOException {

            out.writeByte(this.version);

            out.writeObject(requestId);

            out.writeObject(primaryKey);

            out.writeObject(interfaceClass);

            out.writeUTF(methodName);

            writeMethodParameters(out, methodParamTypes, methodParameters);

            if (null == metaData || metaData.isAtLeast(4, 6)) {
                out.writeObject(authentication);
            }

            out.flush();
        }

        /**
         * Changes to this method must observe the optional {@link #metaData} version
         */
        protected void writeMethodParameters(final ObjectOutput out, final Class[] types, final Object[] args)
                throws IOException {

            out.writeByte(types.length);

            for (int i = 0; i < types.length; i++) {
                final Class clazz = types[i];
                Object obj = args[i];

                if (clazz.isPrimitive()) {
                    if (clazz == Byte.TYPE) {
                        out.write(BYTE);
                        final byte bytevalue = (Byte) obj;
                        out.writeByte(bytevalue);

                    } else if (clazz == Character.TYPE) {
                        out.write(CHAR);
                        final char charvalue = (Character) obj;
                        out.writeChar(charvalue);

                    } else if (clazz == Integer.TYPE) {
                        out.write(INT);
                        final int intvalue = (Integer) obj;
                        out.writeInt(intvalue);

                    } else if (clazz == Boolean.TYPE) {
                        out.write(BOOLEAN);
                        final boolean booleanvalue = (Boolean) obj;
                        out.writeBoolean(booleanvalue);

                    } else if (clazz == Long.TYPE) {
                        out.write(LONG);
                        final long longvalue = (Long) obj;
                        out.writeLong(longvalue);

                    } else if (clazz == Float.TYPE) {
                        out.write(FLOAT);
                        final float fvalue = (Float) obj;
                        out.writeFloat(fvalue);

                    } else if (clazz == Double.TYPE) {
                        out.write(DOUBLE);
                        final double dvalue = (Double) obj;
                        out.writeDouble(dvalue);

                    } else if (clazz == Short.TYPE) {
                        out.write(SHORT);
                        final short shortvalue = (Short) obj;
                        out.writeShort(shortvalue);

                    } else {
                        throw new IOException("Unkown primitive type: " + clazz);
                    }
                } else {
                    if (obj instanceof PortableRemoteObject && obj instanceof Remote) {
                        final Tie tie = javax.rmi.CORBA.Util.getTie((Remote) obj);
                        if (tie == null) {
                            throw new IOException(
                                    "Unable to serialize PortableRemoteObject; object has not been exported: " + obj);
                        }
                        final ORB orb = getORB();
                        tie.orb(orb);
                        obj = PortableRemoteObject.toStub((Remote) obj);
                    }
                    out.write(OBJECT);
                    out.writeObject(clazz);
                    out.writeObject(obj);
                }
            }
        }

        /**
         * Obtain an ORB instance for this request to activate remote arguments and return results.
         *
         * @return An ORB instance.
         * @throws java.io.IOException
         *             On error
         */
        protected ORB getORB() throws IOException {
            // first ORB request?  Check our various sources
            if (orb == null) {
                try {
                    final Context initialContext = new InitialContext();
                    orb = (ORB) initialContext.lookup("java:comp/ORB");
                } catch (Throwable e) {
                    try {
                        // any orb will do if we can't get a context one.
                        orb = ORB.init();
                    } catch (Throwable ex) {
                        throw new IOException(
                                "Unable to connect PortableRemoteObject stub to an ORB, no ORB bound to java:comp/ORB");
                    }
                }
            }
            return orb;
        }

        /**
         * Changes to this method must observe the optional {@link #metaData} version
         */
        protected void readMethodParameters(final ObjectInput in) throws IOException, ClassNotFoundException {
            final int length = in.read();

            if (length < 1) {
                methodParamTypes = noArgsC;
                methodParameters = noArgsO;
                return;
            }

            final Class[] types = new Class[length];
            final Object[] args = new Object[length];

            for (int i = 0; i < types.length; i++) {
                final Class clazz;
                final Object obj;

                final int type = in.read();

                switch (type) {
                case BYTE:
                    clazz = Byte.TYPE;
                    obj = in.readByte();
                    break;

                case CHAR:
                    clazz = Character.TYPE;
                    obj = in.readChar();
                    break;

                case INT:
                    clazz = Integer.TYPE;
                    obj = in.readInt();
                    break;

                case BOOLEAN:
                    clazz = Boolean.TYPE;
                    obj = in.readBoolean();
                    break;

                case LONG:
                    clazz = Long.TYPE;
                    obj = in.readLong();
                    break;

                case FLOAT:
                    clazz = Float.TYPE;
                    obj = in.readFloat();
                    break;

                case DOUBLE:
                    clazz = Double.TYPE;
                    obj = in.readDouble();
                    break;

                case SHORT:
                    clazz = Short.TYPE;
                    obj = in.readShort();
                    break;

                case OBJECT:
                    clazz = (Class) in.readObject();
                    obj = in.readObject();
                    if (obj instanceof Stub) {
                        final Stub stub = (Stub) obj;
                        final ORB orb = getORB();
                        stub.connect(orb);
                    }
                    break;
                default:
                    throw new IOException("Unkown data type: " + type);
                }

                types[i] = clazz;
                args[i] = obj;
            }

            methodParamTypes = types;
            methodParameters = args;
        }

        @Override
        public String toString() {
            if (null == toString) {
                toString = "Body{" + "ejb=" + ejb + ", orb=" + orb + ", methodInstance=" + methodInstance
                        + ", interfaceClass=" + interfaceClass + ", methodName='" + methodName + '\''
                        + ", methodParamTypes=" + (methodParamTypes == null ? null : Arrays.asList(methodParamTypes))
                        + ", methodParameters=" + (methodParameters == null ? null : Arrays.asList(methodParameters))
                        + ", primaryKey=" + primaryKey + ", requestId='" + requestId + '\'' + ", version=" + version
                        + '}';
            }

            return toString;
        }
    }
}
