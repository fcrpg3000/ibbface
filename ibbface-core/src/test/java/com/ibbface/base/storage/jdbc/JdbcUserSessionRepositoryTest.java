/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcUserSessionRepositoryTest.java 2013-09-12 00:10
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.Lists;
import com.ibbface.domain.model.session.UserSession;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Fuchun
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/test-context.xml")
public class JdbcUserSessionRepositoryTest {

    @Resource
    private JdbcUserSessionRepository userSessionRepository;

    private UserSession session1, session2, inValidSession;

    @Before
    public void setUp() throws Exception {
        session1 = UserSession.newSession(10001L);
        session2 = UserSession.newSession(10002L);
        inValidSession = UserSession.newSession(10003L);
        inValidSession.setValid(false);
        inValidSession.setLastAccessedTime(System.currentTimeMillis() - 30 * 60 * 1000L);

        userSessionRepository.save(Lists.newArrayList(session1, session2, inValidSession));
    }

    @After
    public void tearDown() throws Exception {
        userSessionRepository.deleteAllInternal();
    }

    @Test
    public void testFindBySessionId() throws Exception {
        String notExistSessionId = UserSession.generateSessionId();
        UserSession session = userSessionRepository.findBySessionId(notExistSessionId);
        assertNull(session);

        session = userSessionRepository.findBySessionId(session1.getSessionId());
        assertNotNull(session);
        assertThat(session, Is.is(session1));
    }

    @Test
    public void testFindByAccessToken() throws Exception {
        String notExistAccessToken = UserSession.newAccessToken();
        UserSession session = userSessionRepository.findByAccessToken(notExistAccessToken);
        assertNull(session);

        session = userSessionRepository.findByAccessToken(session2.getAccessToken());
        assertNotNull(session);
        assertThat(session, Is.is(session2));
    }

    @Test
    public void testFindActivities() throws Exception {
        long offset = 300;
        PageRequest pageRequest = new PageRequest(0, 10, Sort.Direction.DESC,
                UserSession.PROP_LAST_ACCESSED_TIME);
        Page<UserSession> page = userSessionRepository.findActivities(offset, pageRequest);
        assertNotNull(page);
        assertTrue(page.getContent().contains(session1));
        assertTrue(page.getContent().contains(session2));
        assertFalse(page.getContent().contains(inValidSession));
    }
}
