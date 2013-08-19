/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcForumRepositoryTest.java 2013-08-04 20:58
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.ibbface.domain.model.forum.Forum;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Fuchun
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/test-*.xml")
public class JdbcForumRepositoryTest {

    @Resource
    private JdbcForumRepository forumRepository;

    private List<Forum> sortedForums;
    private Forum forum1, forum2, forum3, forum4, forum5, forum6;

    @Before
    public void setUp() throws Exception {
        forum1 = Forum.newForum("forum_1", "Forum one", "Forum one");
        forum2 = Forum.newForum("forum_2", "Forum two", "Forum two");
        forum3 = Forum.newForum("forum_3", "Forum three", "Forum three");
        forum4 = Forum.newForum("forum_4", "Forum four", "Forum four");
        forum5 = Forum.newForum("forum_5", "Forum five", "Forum five");
        forum6 = Forum.newForum("forum_6", "Forum six", "Forum six");
        forum1.setSortOrder(1);
        forum2.setSortOrder(2);
        forum3.setSortOrder(3);
        forum4.setSortOrder(4);
        forum5.setSortOrder(5);
        forum6.setSortOrder(6);

        forumRepository.save(forum1);
        forumRepository.save(forum2);
        forumRepository.save(forum3);
        forumRepository.save(forum4);
        forumRepository.save(forum5);
        forumRepository.save(forum6);

        sortedForums = Lists.newArrayList(forum1, forum2, forum3, forum4, forum5, forum6);
        Collections.sort(sortedForums);
    }

    @After
    public void tearDown() throws Exception {
        List<Forum> forums = Lists.newArrayList();
        if (forum1.getId() != null) {
            forums.add(forum1);
        }
        if (forum2.getId() != null) {
            forums.add(forum2);
        }
        if (forum3.getId() != null) {
            forums.add(forum3);
        }
        if (forum4.getId() != null) {
            forums.add(forum4);
        }
        if (forum5.getId() != null) {
            forums.add(forum5);
        }
        if (forum6.getId() != null) {
            forums.add(forum6);
        }
        forumRepository.deleteInBatch(forums);
    }

    @Test
    public void testUpdateStatus() throws Exception {
        Integer oldSortOrder = forum1.getSortOrder();
        forumRepository.updateSortOrder(forum1.getId(), 7);
        Forum existForum1 = forumRepository.findOne(forum1.getId());
        assertNotNull(existForum1);
        assertNotEquals(existForum1.getSortOrder(), oldSortOrder);
        assertThat(existForum1.getSortOrder(), Is.is(7));
    }

    @Test
    public void testFindByIdAlias() throws Exception {
        Forum existForum2 = forumRepository.findByIdAlias(forum2.getIdAlias());
        assertNotNull(existForum2);
        assertEquals(existForum2, forum2);
    }

    @Test
    public void testFindOrderBySortOrder() throws Exception {
        List<Forum> forums = forumRepository.findOrderBySortOrder(Sort.Direction.ASC);
        assertNotNull(forums);
        assertTrue(forums.size() == sortedForums.size());
        assertEquals(sortedForums, forums);

        Collections.reverse(forums); // order by sortOrder desc
        List<Forum> existForums = forumRepository.findOrderBySortOrder(Sort.Direction.DESC);
        assertNotNull(existForums);
        assertEquals(forums, existForums);
    }

    @Test
    public void testFindIdSortMapping() throws Exception {
        Map<Integer, Integer> idSortMapping = forumRepository.findIdSortMapping();
        assertNotNull(idSortMapping);
        assertThat(idSortMapping.size(), Is.is(sortedForums.size()));

        for (Forum f : sortedForums) {
            Integer sortOrder = idSortMapping.get(f.getId());
            assertThat(sortOrder, Is.is(f.getSortOrder()));
        }
    }

    @Test
    public void testGetSortOrder() throws Exception {
        Integer forum1SortOrder = forumRepository.getSortOrder(forum1.getId());
        assertNotNull(forum1SortOrder);
        assertThat(forum1SortOrder, Is.is(forum1.getSortOrder()));
    }

    @Test
    public void testUpdateSortOrder() throws Exception {
        forumRepository.updateSortOrder(forum1.getId(), 9);

        Forum existForum1 = forumRepository.findOne(forum1.getId());

        assertNotNull(existForum1);
        assertThat(forum1.getSortOrder(), IsNot.not(existForum1.getSortOrder()));
        assertThat(existForum1.getSortOrder(), Is.is(9));
    }

    @Test
    public void testSubtractSortOrder() throws Exception {
        Range<Integer> range = Range.closed(forum3.getSortOrder(), forum5.getSortOrder());
        // 3,4,5 => 2,3,4
        forumRepository.subtractSortOrder(range);
        // 2 => 5
        forumRepository.updateSortOrder(forum2.getId(), 5);

        List<Forum> forums = forumRepository.findOrderBySortOrder(Sort.Direction.ASC);
        assertNotNull(forums);

        assertThat(forums.get(1), Is.is(forum3));
        assertThat(forums.get(2), Is.is(forum4));
        assertThat(forums.get(3), Is.is(forum5));
        assertThat(forums.get(4), Is.is(forum2));
    }

    @Test
    public void testAddSortOrder() throws Exception {
        Range<Integer> range = Range.closed(forum1.getSortOrder(), forum3.getSortOrder());
        // 1,2,3 => 2,3,4
        forumRepository.addSortOrder(range);
        // 4 => 1
        forumRepository.updateSortOrder(forum4.getId(), 1);

        List<Forum> forums = forumRepository.findOrderBySortOrder(Sort.Direction.ASC);
        assertNotNull(forums);

        assertThat(forums.get(0), Is.is(forum4));
        assertThat(forums.get(1), Is.is(forum1));
        assertThat(forums.get(2), Is.is(forum2));
        assertThat(forums.get(3), Is.is(forum3));
    }

    @Test
    public void testGetMaxSortOrder() throws Exception {
        Integer maxSortOrder = forumRepository.getMaxSortOrder();
        assertNotNull(maxSortOrder);
        assertThat(maxSortOrder, Is.is(forum6.getSortOrder()));
    }
}
