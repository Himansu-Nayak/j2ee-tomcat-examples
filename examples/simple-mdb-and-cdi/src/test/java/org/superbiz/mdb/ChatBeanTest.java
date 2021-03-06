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
//START SNIPPET: code
package org.superbiz.mdb;

import javax.annotation.Resource;
import javax.ejb.embeddable.EJBContainer;
import javax.jms.*;

import junit.framework.TestCase;

public class ChatBeanTest extends TestCase {

    @Resource
    private ConnectionFactory connectionFactory;

    @Resource(name = "ChatBean")
    private Queue questionQueue;

    @Resource(name = "AnswerQueue")
    private Queue answerQueue;

    public void test() throws Exception {
        EJBContainer.createEJBContainer().getContext().bind("inject", this);

        final Connection connection = connectionFactory.createConnection();

        connection.start();

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        final MessageProducer questions = session.createProducer(questionQueue);

        final MessageConsumer answers = session.createConsumer(answerQueue);

        sendText("Hello World!", questions, session);

        assertEquals("Hello, Test Case!", receiveText(answers));

        sendText("How are you?", questions, session);

        assertEquals("I'm doing well.", receiveText(answers));

        sendText("Still spinning?", questions, session);

        assertEquals("Once every day, as usual.", receiveText(answers));

    }

    private void sendText(String text, MessageProducer questions, Session session) throws JMSException {

        questions.send(session.createTextMessage(text));

    }

    private String receiveText(MessageConsumer answers) throws JMSException {

        return ((TextMessage) answers.receive(1000)).getText();

    }

}
//END SNIPPET: code
