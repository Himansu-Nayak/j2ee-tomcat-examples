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
package jug.rest;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import jug.dao.SubjectDao;
import jug.dao.VoteDao;
import jug.domain.Result;
import jug.domain.Subject;
import jug.domain.Value;
import jug.domain.Vote;
import jug.monitoring.VoteCounter;

@Path("subject")
@Singleton // an ejb just to be able to test in standalone
@Lock(LockType.READ)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Produces({ MediaType.APPLICATION_JSON })
public class SubjectService {

    @Inject
    private SubjectDao dao;

    @Inject
    private VoteDao voteDao;

    @Inject
    private VoteCounter counter;

    @Resource(name = "poll.blacklist")
    private List<String> blackList;

    @POST
    @Path("create")
    public Subject create(final String question, @QueryParam("name") final String name) {
        if (blackList.contains(name)) {
            throw new IllegalArgumentException("name blacklisted");
        }

        final Subject subject = dao.create(name, question);

        counter.putSubject(subject);

        return subject;
    }

    @GET
    @Path("list")
    public Collection<Subject> list() {
        return dao.findAll();
    }

    @GET
    @Path("find/{name}")
    public Subject findByName(@PathParam("name") final String name) {
        return dao.findByName(name);
    }

    @GET
    @Path("best")
    public Subject best() {
        return dao.bestSubject();
    }

    @GET
    @Path("result/{name}")
    public Result result(@PathParam("name") final String name) {
        return new Result(dao.subjectLikeVoteNumber(name), -dao.subjectNotLikeVoteNumber(name));
    }

    @POST
    @Path("vote")
    public Vote vote(final String input, @QueryParam("subject") final String subjectName) {
        final Vote vote = voteDao.create(Value.valueOf(input));
        final Subject subject = dao.findByName(subjectName);
        dao.addVote(subject, vote);

        counter.putSubject(subject); // update

        return vote;
    }
}
