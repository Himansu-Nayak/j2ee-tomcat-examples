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
package org.apache.openejb.assembler.classic;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

public class ComparableValidationConfig implements Serializable {
    private final String providerClassName;
    private final String messageInterpolatorClass;
    private final String traversableResolverClass;
    private final String constraintFactoryClass;
    private final String parameterNameProviderClass;
    private final String version;
    private final Properties propertyTypes;
    private final List<String> constraintMappings;
    private final boolean executableValidationEnabled;
    private final List<String> validatedTypes;

    private final int hash;

    public ComparableValidationConfig(final String providerClassName, final String messageInterpolatorClass,
            final String traversableResolverClass, final String constraintFactoryClass,
            final String parameterNameProviderClass, final String version, final Properties propertyTypes,
            final List<String> constraintMappings, final boolean executableValidationEnabled,
            final List<String> validatedTypes) {
        this.providerClassName = providerClassName;
        this.messageInterpolatorClass = messageInterpolatorClass;
        this.traversableResolverClass = traversableResolverClass;
        this.constraintFactoryClass = constraintFactoryClass;
        this.parameterNameProviderClass = parameterNameProviderClass;
        this.version = version;
        this.propertyTypes = propertyTypes;
        this.constraintMappings = constraintMappings;
        this.executableValidationEnabled = executableValidationEnabled;
        this.validatedTypes = validatedTypes;

        int result = providerClassName != null ? providerClassName.hashCode() : 0;
        result = 31 * result + (messageInterpolatorClass != null ? messageInterpolatorClass.hashCode() : 0);
        result = 31 * result + (traversableResolverClass != null ? traversableResolverClass.hashCode() : 0);
        result = 31 * result + (constraintFactoryClass != null ? constraintFactoryClass.hashCode() : 0);
        result = 31 * result + (parameterNameProviderClass != null ? parameterNameProviderClass.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (propertyTypes != null ? propertyTypes.hashCode() : 0);
        result = 31 * result + (constraintMappings != null ? constraintMappings.hashCode() : 0);
        result = 31 * result + (executableValidationEnabled ? 1 : 0);
        result = 31 * result + (validatedTypes != null ? validatedTypes.hashCode() : 0);
        hash = result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ComparableValidationConfig that = (ComparableValidationConfig) o;

        if (executableValidationEnabled != that.executableValidationEnabled) {
            return false;
        }
        if (constraintFactoryClass != null ? !constraintFactoryClass.equals(that.constraintFactoryClass)
                : that.constraintFactoryClass != null) {
            return false;
        }
        if (constraintMappings != null ? !constraintMappings.equals(that.constraintMappings)
                : that.constraintMappings != null) {
            return false;
        }
        if (messageInterpolatorClass != null ? !messageInterpolatorClass.equals(that.messageInterpolatorClass)
                : that.messageInterpolatorClass != null) {
            return false;
        }
        if (parameterNameProviderClass != null ? !parameterNameProviderClass.equals(that.parameterNameProviderClass)
                : that.parameterNameProviderClass != null) {
            return false;
        }
        if (propertyTypes != null ? !propertyTypes.equals(that.propertyTypes) : that.propertyTypes != null) {
            return false;
        }
        if (providerClassName != null ? !providerClassName.equals(that.providerClassName)
                : that.providerClassName != null) {
            return false;
        }
        if (traversableResolverClass != null ? !traversableResolverClass.equals(that.traversableResolverClass)
                : that.traversableResolverClass != null) {
            return false;
        }
        if (validatedTypes != null ? !validatedTypes.equals(that.validatedTypes) : that.validatedTypes != null) {
            return false;
        }
        if (version != null ? !version.equals(that.version) : that.version != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
