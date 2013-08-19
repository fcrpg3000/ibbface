/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcUserOnlineRepositoryTest.java 2013-08-09 20:54
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.Lists;
import com.ibbface.domain.model.user.UserOnline;
import com.ibbface.util.RandomStrings;
import org.apache.commons.codec.digest.DigestUtils;
import org.hamcrest.core.Is;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.ibbface.domain.model.user.base.BaseUserOnline.PROP_LAST_ACCESSED_TIME;
import static com.ibbface.domain.model.user.base.BaseUserOnline.PROP_LAST_LOGIN_TIME;
import static com.ibbface.domain.model.user.base.BaseUserOnline.PROP_TOTAL_LOGIN_COUNT;
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
@ContextConfiguration(locations = "classpath:/META-INF/spring/test-*.xml")
public class JdbcUserOnlineRepositoryTest {

    private final Logger LOGGER = LoggerFactory.getLogger(JdbcUserOnlineRepositoryTest.class);

    @Resource
    private JdbcUserOnlineRepository userOnlineRepository;

    private UserOnline user1, user2;
    private UserOnline userOnline;

    @Before
    public void setUp() throws Exception {
        userOnline = UserOnline.create(10003L, "127.0.0.1", new Date());
        userOnlineRepository.save(userOnline);

        user1 = UserOnline.create(39171L, "10.200.2.12",
                DateTime.now().minusDays(1).toDate());
        user1.setTotalLoginCount(100);
        user1.setThatOnlineTime(900L);
        user1.setTotalOnlineTime(51000L);
        user2 = UserOnline.create(17629L, "50.180.223.243",
                DateTime.now().minusDays(2).toDate());
        user2.setTotalLoginCount(50);
        user2.setThatOnlineTime(1200L);
        user2.setTotalOnlineTime(34900L);
        userOnlineRepository.save(Lists.newArrayList(user1, user2));
        LOGGER.info("Start to test UserOnlineRepository before set up.");
    }

    @After
    public void tearDown() throws Exception {
        userOnlineRepository.delete(Lists.newArrayList(user1, user2, userOnline));
        LOGGER.info("Delete testing UserOnline after testing tear down.");
    }

    @Test
    public void testFindBySessionId() throws Exception {
        UserOnline exists = userOnlineRepository.findBySessionId(userOnline.getSessionId());
        assertNotNull(exists);
        assertThat(exists, Is.is(userOnline));

        exists = userOnlineRepository.findBySessionId(DigestUtils.md5Hex("10002"));
        assertNull(exists);
    }

    @Test
    public void testFindByAccessToken() throws Exception {
        UserOnline exists = userOnlineRepository.findByAccessToken(userOnline.getAccessToken());
        assertNotNull(exists);
        assertThat(exists, Is.is(userOnline));

        exists = userOnlineRepository.findByAccessToken(RandomStrings.random(16, true, true));
        assertNull(exists);
    }

    @Test
    public void testFindTop() throws Exception {
        // order by totalLoginCount desc
        // 0 => user1, 1 => user2
        List<UserOnline> userOnlineList = userOnlineRepository.findTop(
                new Sort(Sort.Direction.DESC, PROP_TOTAL_LOGIN_COUNT), 2);
        assertNotNull(userOnlineList);
        assertTrue(userOnlineList.size() == 2);
        assertThat(userOnlineList.get(0), Is.is(user1));
        assertThat(userOnlineList.get(1), Is.is(user2));

        // order by lastLoginTime desc
        // 0 => userOnline, 1 => user1
        userOnlineList = userOnlineRepository.findTop(
                new Sort(Sort.Direction.DESC, PROP_LAST_LOGIN_TIME), 2);
        assertNotNull(userOnlineList);
        assertTrue(userOnlineList.size() == 2);
        assertThat(userOnlineList.get(0), Is.is(userOnline));
        assertThat(userOnlineList.get(1), Is.is(user1));
    }

    @Test
    public void testFindOrderByThatOnlineTimeDesc() throws Exception {
        List<UserOnline> onlineList = userOnlineRepository.findOrderByThatOnlineTimeDesc(2);
        assertNotNull(onlineList);
        assertTrue(onlineList.size() == 2);
        assertThat(onlineList.get(0), Is.is(user2));
        assertThat(onlineList.get(1), Is.is(user1));
    }

    @Test
    public void testFindOrderByTotalOnlineTimeDesc() throws Exception {
        List<UserOnline> onlineList = userOnlineRepository.findOrderByTotalOnlineTimeDesc(2);
        assertNotNull(onlineList);
        assertTrue(onlineList.size() == 2);
        assertThat(onlineList.get(0), Is.is(user1));
        assertThat(onlineList.get(1), Is.is(user2));
    }

    @Test
    public void testFindOnline() throws Exception {
        long timeNow = System.currentTimeMillis();
        long offset = 10 * 60 * 1000L; // 10 minutes
        userOnline.setLastAccessedTime(timeNow - 9 * 60 * 1000L); // before 9 minutes
        userOnlineRepository.save(userOnline);
        LOGGER.info("Before user1#lastAccessedTime = " + user1.getLastAccessedTime());
        user1.setLastAccessedTime(timeNow - 11 * 60 * 1000L); // before 11 minutes
        user2.setLastAccessedTime(timeNow - 1 * 60 * 1000L); // before 1 minutes
        userOnlineRepository.save(Lists.newArrayList(user1, user2));

        LOGGER.info("After user1#lastAccessedTime = " +
                userOnlineRepository.findOne(user1.getId()).getLastAccessedTime());

        PageRequest pageRequest = new PageRequest(0, 10,
                new Sort(Sort.Direction.DESC, PROP_LAST_ACCESSED_TIME));
        Page<UserOnline> onlinePage = userOnlineRepository.findOnline(offset, pageRequest);
        assertNotNull(onlinePage);
        assertTrue(onlinePage.hasContent());

        assertThat(onlinePage.getContent().get(0), Is.is(user2));
        assertThat(onlinePage.getContent().get(1), Is.is(userOnline));
        assertFalse(onlinePage.getContent().contains(user1));
    }
}
