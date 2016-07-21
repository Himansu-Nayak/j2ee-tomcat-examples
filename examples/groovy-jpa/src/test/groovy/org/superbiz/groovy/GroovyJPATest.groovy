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
package org.superbiz.groovy

import org.apache.ziplock.JarLocation
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.junit.Arquillian
import org.jboss.shrinkwrap.api.ArchivePaths
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.Test
import org.junit.runner.RunWith

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.PersistenceUnit

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

@RunWith(Arquillian.class)
class GroovyJPATest {

    @PersistenceUnit
    private EntityManagerFactory emf

    @Deployment
    static WebArchive war() {
        ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(JarLocation.jarLocation(GroovyObject.class))
                .addAsWebInfResource(new ClassLoaderAsset("META-INF/persistence.xml"), ArchivePaths.create("persistence.xml"))
                .addClasses(Person.class)
    }

    @Test
    void persist() {
        assertNotNull emf
        final EntityManager em = emf.createEntityManager()

        em.transaction.begin()
        em.persist(new Person(name: 'openejb'))
        em.transaction.commit()

        def list = em.createQuery("select p from Person p").resultList
        assertNotNull list
        assertEquals 1, list.size()
        assertEquals 'openejb', list.first().name
    }
}
