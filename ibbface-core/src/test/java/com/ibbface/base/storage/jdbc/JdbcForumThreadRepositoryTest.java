/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcForumThreadRepositoryTest.java 2013-08-05 13:30
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.ibbface.domain.exception.EntityNotFoundException;
import com.ibbface.domain.model.forum.ForumThread;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.joda.time.DateTime;
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
import java.util.Date;
import java.util.List;

import static com.ibbface.domain.model.forum.ForumThread.STATUS_DELETED;
import static com.ibbface.domain.model.forum.ForumThread.STATUS_DRAFT;
import static com.ibbface.domain.model.forum.ForumThread.STATUS_PUBLISHED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Fuchun
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/test-*.xml")
public class JdbcForumThreadRepositoryTest {

    private static final Integer BAN_WU_FORUM_ID = 1;
    private static final Integer FEELING_FORUM_ID = 2;
    private static final Integer HELP_FORUM_ID = 3;

    @Resource
    private JdbcForumThreadRepository forumThreadRepository;

    private ForumThread deletedThread, draftThread, publishedThread,
            topThread, goodThread;

    private ForumThread prevThread, todayThread;

    @Before
    public void setUp() throws Exception {
        deletedThread = ForumThread.create("qing-shang", FEELING_FORUM_ID, 10005L, "情殇", "我的情殇",
                "127.0.0.1", (short) 1, null, null, STATUS_DELETED);
        draftThread = ForumThread.create("kuai-le-jing-lin", FEELING_FORUM_ID, 30000L, "快乐精灵", "快乐精灵的内容体",
                "10.200.90.60", (short) 1, "快乐,精灵", null, STATUS_DRAFT);
        publishedThread = ForumThread.create("you-shang", FEELING_FORUM_ID, 72812L, "悠伤", "悠伤的内容",
                "180.150.75.223", (short) 1, "悠伤", null, STATUS_PUBLISHED);
        forumThreadRepository.save(Lists.newArrayList(deletedThread, draftThread, publishedThread));

        topThread = ForumThread.create("ban-wu", BAN_WU_FORUM_ID, 10000L, "版务说明", "版务说明内容",
                "10.200.10.27", (short) 1, "版务,公告", null, STATUS_PUBLISHED);
        topThread.setIsTop(true);
        goodThread = ForumThread.create("ai-zai-shen-qiu", FEELING_FORUM_ID, 65391L, "爱在深秋", "爱在深秋内容",
                "11.150,223.201", (short) 1, "爱,深秋", null, STATUS_PUBLISHED);
        goodThread.setIsGood(true);
        forumThreadRepository.save(Lists.newArrayList(topThread, goodThread));

        prevThread = ForumThread.create("bao-bao-qi-shui-dou", HELP_FORUM_ID, 15971L, "宝宝起水豆，发热怎么办",
                "求助的内容", "50.181.90.223", (short) 2, "水豆", null, STATUS_PUBLISHED);
        DateTime dateTime = DateTime.now();
        prevThread.setCreatedTime(dateTime.minusDays(1).toDate());
        prevThread.setLastModifiedTime(prevThread.getCreatedTime());

        todayThread = ForumThread.create("bao-bao-shang-xue", HELP_FORUM_ID, 15971L, "宝宝上学问题",
                "求助的内容", "50.181.90.223", (short) 2, "宝宝,上学", null, STATUS_PUBLISHED);
        forumThreadRepository.save(Lists.newArrayList(prevThread, todayThread));
    }

    @After
    public void tearDown() throws Exception {
        forumThreadRepository.deleteInBatch(Lists.newArrayList(deletedThread,
                draftThread, publishedThread, topThread, goodThread,
                prevThread, todayThread));
    }

    @Test
    public void testFindByIdAlias() throws Exception {
        ForumThread exist1 = forumThreadRepository.findByIdAlias(draftThread.getIdAlias());
        assertNotNull(exist1);
        assertThat(exist1, Is.is(draftThread));

        ForumThread exist2 = forumThreadRepository.findByIdAlias(publishedThread.getIdAlias());
        assertNotNull(exist2);
        assertThat(exist2, Is.is(publishedThread));
    }

    @Test
    public void testFindByAll() throws Exception {

    }

    @Test
    public void testFindByUserId() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2, Sort.Direction.DESC,
                ForumThread.PROP_CREATED_TIME);
        Page<ForumThread> pageThread = forumThreadRepository.findByUserId(prevThread.getUserId(), pageRequest);
        assertNotNull(pageThread);

        // validate page total
        assertEquals(pageThread.getTotalElements(), 2);
        assertEquals(pageThread.getContent().size(), 2);

        // validate page content list
        assertTrue(pageThread.getContent().contains(prevThread));
        assertTrue(pageThread.getContent().contains(todayThread));

        // validate sort: order by createdTime desc
        assertThat(pageThread.getContent().get(0), Is.is(todayThread));
        assertThat(pageThread.getContent().get(1), Is.is(prevThread));
    }

    @Test
    public void testFindGoodByUserId() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2, Sort.Direction.DESC,
                ForumThread.PROP_CREATED_TIME);
        Page<ForumThread> pageThread = forumThreadRepository.findGoodByUserId(
                goodThread.getUserId(), pageRequest);

        assertNotNull(pageThread);
        assertTrue(pageThread.getTotalElements() == 1);
        for (ForumThread t : pageThread.getContent()) {
            assertTrue(t.getIsGood());
        }

        pageThread = forumThreadRepository.findGoodByUserId(topThread.getUserId(),
                pageRequest);
        assertNotNull(pageThread);
        assertTrue(pageThread.getTotalElements() == 0);
        assertTrue(pageThread.getContent().isEmpty());
    }

    @Test
    public void testFindByForumIdAndUserId() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2, Sort.Direction.DESC,
                ForumThread.PROP_CREATED_TIME);
        Page<ForumThread> pageThread = forumThreadRepository.findByForumIdAndUserId(
                HELP_FORUM_ID, prevThread.getUserId(), pageRequest);

        assertNotNull(pageThread);
        assertTrue(pageThread.getTotalElements() == 2);
        for (ForumThread t : pageThread.getContent()) {
            assertThat(t.getStatus(), Is.is(ForumThread.STATUS_PUBLISHED));
        }

        assertTrue(pageThread.getContent().contains(prevThread));
        assertTrue(pageThread.getContent().contains(todayThread));
    }

    @Test
    public void testFindByForumId() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2, Sort.Direction.DESC,
                ForumThread.PROP_CREATED_TIME);
        Page<ForumThread> pageThread = forumThreadRepository.findByForumId(
                FEELING_FORUM_ID, pageRequest);
        assertNotNull(pageThread);
        assertTrue(pageThread.getTotalElements() == 2);
        assertTrue(pageThread.getContent().contains(publishedThread));
        assertTrue(pageThread.getContent().contains(goodThread));

        for (ForumThread t : pageThread.getContent()) {
            assertThat(FEELING_FORUM_ID, Is.is(t.getForumId()));
        }
    }

    @Test
    public void testFindGoodByForumId() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2, Sort.Direction.DESC,
                ForumThread.PROP_CREATED_TIME);
        Page<ForumThread> pageThread = forumThreadRepository.findGoodByForumId(
                FEELING_FORUM_ID, pageRequest);

        assertNotNull(pageThread);
        assertThat(pageThread.getContent().get(0), Is.is(goodThread));
        assertTrue(pageThread.getTotalElements() == 1);

        pageThread = forumThreadRepository.findGoodByForumId(HELP_FORUM_ID, pageRequest);

        assertNotNull(pageThread);
        assertTrue(pageThread.getTotalElements() == 0);
        assertTrue(pageThread.getContent().isEmpty());
    }

    @Test
    public void testFindByStatus() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2, Sort.Direction.DESC,
                ForumThread.PROP_CREATED_TIME);
        Page<ForumThread> pageThread = forumThreadRepository.findByStatus(STATUS_DELETED,
                pageRequest);
        assertNotNull(pageThread);
        assertTrue(pageThread.getTotalElements() == 1);
        assertTrue(pageThread.getContent().contains(deletedThread));
        assertFalse(pageThread.getContent().contains(publishedThread));
        assertFalse(pageThread.getContent().contains(draftThread));

        pageThread = forumThreadRepository.findByStatus(STATUS_DRAFT, pageRequest);
        assertNotNull(pageThread);
        assertTrue(pageThread.getTotalElements() == 1);
        assertThat(pageThread.getContent().get(0), Is.is(draftThread));
        assertFalse(pageThread.getContent().contains(deletedThread));
        assertFalse(pageThread.getContent().contains(publishedThread));

        pageThread = forumThreadRepository.findByStatus(STATUS_PUBLISHED, pageRequest);
        assertNotNull(pageThread);
        assertTrue(pageThread.getTotalElements() == 5);

        for (ForumThread t : pageThread.getContent()) {
            assertThat(STATUS_PUBLISHED, Is.is(t.getStatus()));
        }
    }

    @Test
    public void testFindByCreatedTime() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2, Sort.Direction.DESC,
                ForumThread.PROP_CREATED_TIME);
        Page<ForumThread> pageThread = forumThreadRepository.findByCreatedTime(
                new Date(), new Short[]{STATUS_PUBLISHED}, pageRequest);
        assertNotNull(pageThread);
        assertTrue(pageThread.getTotalElements() == 4);
    }

    @Test
    public void testFindByCreatedTimeRange() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2, Sort.Direction.DESC,
                ForumThread.PROP_CREATED_TIME);
        DateTime dateTime = DateTime.now();
        Page<ForumThread> pageThread = forumThreadRepository.findByCreatedTimeRange(
                Range.closed(dateTime.minusDays(2).toDate(), dateTime.toDate()),
                new Short[]{STATUS_PUBLISHED},
                pageRequest);
        assertNotNull(pageThread);
        assertEquals("Actual total: " + pageThread.getTotalElements(), pageThread.getTotalElements(), 5);
        for (ForumThread t : pageThread.getContent()) {
            assertThat(STATUS_PUBLISHED, Is.is(t.getStatus()));
        }
    }

    @Test
    public void testFindOrderByPostTimeDescLimit() throws Exception {
        List<ForumThread> threads = forumThreadRepository.findOrderByPostTimeDescLimit(
                FEELING_FORUM_ID, 1);
        assertNotNull(threads);
        assertTrue(threads.size() == 1);

        threads = forumThreadRepository.findOrderByPostTimeDescLimit(FEELING_FORUM_ID, 10);
        assertNotNull(threads);
        assertTrue(threads.size() == 2);
        assertTrue(threads.contains(publishedThread));
        assertTrue(threads.contains(goodThread));
    }

    @Test
    public void testSetReplyCount() throws Exception {
        forumThreadRepository.setReplyCount(publishedThread.getId(), 100);
        ForumThread thread = forumThreadRepository.findOne(publishedThread.getId());
        assertNotNull(thread);
        assertThat(thread.getId(), Is.is(publishedThread.getId()));
        assertThat(thread.getReplyCount(), IsNot.not(publishedThread.getReplyCount()));
        assertThat(thread.getReplyCount(), Is.is(100));
    }

    @Test
    public void testSetReplyCountNotFound() throws Exception {
        try {
            forumThreadRepository.setReplyCount(100L, 100);
        } catch (Exception e) {
            assertTrue(e instanceof EntityNotFoundException);
        }
    }

    @Test
    public void testSetViewCount() throws Exception {
        forumThreadRepository.setViewCount(goodThread.getId(), 1000);
        ForumThread thread = forumThreadRepository.findOne(goodThread.getId());
        assertNotNull(thread);
        assertThat(thread.getId(), Is.is(goodThread.getId()));
        assertThat(thread.getViewCount(), IsNot.not(goodThread.getViewCount()));
        assertThat(thread.getViewCount(), Is.is(1000));
    }

    @Test
    public void testSetViewCountNotFound() throws Exception {
        try {
            forumThreadRepository.setViewCount(100L, 100);
        } catch (Exception e) {
            assertTrue(e instanceof EntityNotFoundException);
        }
    }

    @Test
    public void testSetTopGood() throws Exception {
        forumThreadRepository.setTopGood(publishedThread.getId(), true, null);
        ForumThread thread = forumThreadRepository.findOne(publishedThread.getId());
        assertNotNull(thread);
        assertTrue(thread.getIsTop());
        assertFalse(thread.getIsGood());

        forumThreadRepository.setTopGood(publishedThread.getId(), true, true);
        thread = forumThreadRepository.findOne(publishedThread.getId());
        assertNotNull(thread);
        assertTrue(thread.getIsTop());
        assertTrue(thread.getIsGood());
        assertTrue(thread.getIsTop() != publishedThread.getIsTop());
        assertTrue(thread.getIsGood() != publishedThread.getIsGood());
    }

    @Test
    public void testSetTopGoodNotFound() throws Exception {
        try {
            forumThreadRepository.setTopGood(100L, true, true);
        } catch (Exception e) {
            assertTrue(e instanceof EntityNotFoundException);
        }
    }

    @Test
    public void testSetTop() throws Exception {
        forumThreadRepository.setTop(topThread.getId(), false);
        ForumThread thread = forumThreadRepository.findOne(topThread.getId());
        assertNotNull(thread);
        assertEquals(thread, topThread);
        assertFalse(thread.getIsTop());
        assertTrue(topThread.getIsTop());
    }

    @Test
    public void testSetGood() throws Exception {
        forumThreadRepository.setGood(goodThread.getId(), false);
        ForumThread thread = forumThreadRepository.findOne(goodThread.getId());
        assertNotNull(thread);
        assertEquals(thread, goodThread);
        assertFalse(thread.getIsGood());
        assertTrue(goodThread.getIsGood());
    }
}
