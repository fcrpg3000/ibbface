/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcUserRepositoryTest.java 2013-07-30 00:23
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ibbface.domain.model.user.Gender;
import com.ibbface.domain.model.user.User;
import com.ibbface.repository.user.UserRepository;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
public class JdbcUserRepositoryTest {

    private static final String USER1_NAME = "userName1";
    private static final String USER2_NAME = "userName2";
    private static final String USER3_NAME = "userName3";
    private static final String INNER_USER_NAME = "Shammy";
    private static final String TEST_USER_NAME = "test_user";

    @Resource
    private UserRepository userRepository;

    private User user1;
    private User user2;
    private User user3;

    private User innerUser;
    private User testUser;

    @Before
    public void setUp() throws Exception {
        initUsers();
    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteInBatch(Lists.newArrayList(
                user1, user2, user3, innerUser));
        if (testUser.getId() != null)
            userRepository.delete(testUser);
    }

    private void initUsers() {
        // mock exists innerUser:
        innerUser = User.createUser("fuchun@ibbface.com", INNER_USER_NAME, "fuchun", "11111111", Gender.MALE);
        innerUser.setUserId(8888L);
        innerUser.setMobileNo("13866668888");
        innerUser.setMobileVerified(true);
        userRepository.save(innerUser);

        // mock exists users:
        user1 = User.createUser("user1@ibbface.com", USER1_NAME, "userHandle1", "11111111", Gender.FEMALE);
        user2 = User.createUser("user2@ibbface.com", USER2_NAME, "userHandle2", "11111111", Gender.FEMALE);
        user3 = User.createUser("user3@ibbface.com", USER3_NAME, "userHandle3", "11111111", Gender.MALE);
        userRepository.save(Lists.newArrayList(user1, user2, user3));

        // mock test the user:
        testUser = User.createUser("test_user@ibbface.com", TEST_USER_NAME,
                "test_user_handle", "11111111", Gender.FEMALE);
    }

    @Test
    public void testFindByEmail() throws Exception {
        User user = userRepository.findByEmail(user1.getEmail());
        assertNotNull("user1 saved fail.", user);
        assertThat(user, Is.is(user1));
    }

    @Test
    public void testFindByUserName() throws Exception {
        User user = userRepository.findByUserName(user2.getUserName());
        assertNotNull("user2 saved fail.", user);
        assertThat(user, Is.is(user2));
    }

    @Test
    public void testFindByMobileNo() throws Exception {
        User user = userRepository.findByMobileNo(innerUser.getMobileNo());
        assertNotNull("innerUser saved fail.", user);
        assertThat(user, Is.is(innerUser));
    }

    @Test
    public void testUpdate() throws Exception {
        User existsUser1 = userRepository.findByUserName(user1.getUserName());
        assertNotNull(existsUser1);
        user1.setSpareEmail("8888@gmail.com");
        user1.preUpdate();
        userRepository.save(user1);
        assertThat(existsUser1, Is.is(user1));
        assertNotEquals(user1.getSpareEmail(), existsUser1.getSpareEmail());
        assertNotEquals(user1.getLastModifiedTime(), existsUser1.getLastModifiedTime());
    }

    @Test
    public void testFindOne() throws Exception {
        User existUser1 = userRepository.findOne(user1.getId());
        assertNotNull(existUser1);
        assertThat(existUser1, Is.is(user1));
    }

    @Test
    public void testExists() throws Exception {
        boolean exists = userRepository.exists(user1.getId());
        assertTrue(exists);
    }

    @Test
    public void testCount() throws Exception {
        // user1, user2, user3 and innerUser
        long count = userRepository.count();
        assertEquals(count, 4);
    }

    @Test
    public void testDeleteById() throws Exception {
        userRepository.delete(user1.getId());

        User existsUser1 = userRepository.findOne(user1.getId());
        assertNull(existsUser1);
    }

    @Test
    public void testDeleteEntity() throws Exception {
        userRepository.delete(user1);

        User existsUser1 = userRepository.findOne(user1.getId());
        assertNull(existsUser1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDeleteAll() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void testDelete() throws Exception {
        Iterable<User> users = Sets.newHashSet(user1, user2, user3);
        userRepository.delete(users);

        assertEquals(1, userRepository.count());
    }

    @Test
    public void testFindAll() throws Exception {
        List<User> users = userRepository.findAll();
        assertEquals(users.size(), 4);

        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        assertTrue(users.contains(user3));
        assertTrue(users.contains(innerUser));
    }

    @Test
    public void testFindAllBySort() throws Exception {
        // order by user_id asc
        // innerUser(8888), user1, user2, user3
        List<User> list1 = userRepository.findAll(new Sort(
                Sort.Direction.ASC, User.PROP_USER_ID));
        assertNotNull(list1);
        assertThat(list1.get(0), Is.is(innerUser));

        // order by user_id desc
        // user3, user2, user1, innerUser
        List<User> list2 = userRepository.findAll(new Sort(
                Sort.Direction.DESC, User.PROP_USER_ID));
        assertNotNull(list2);
        assertThat(Iterables.getLast(list2), Is.is(innerUser));

        // order by user_name asc
        // innerUser(Shammy), user1(userName1), user2(userName2), user3(userName3)
        List<User> list3 = userRepository.findAll(new Sort(
                Sort.Direction.ASC, User.PROP_USER_NAME));
        assertNotNull(list3);
        assertThat(list3.get(0), Is.is(innerUser));
        assertThat(list3.get(1), Is.is(user1));
    }

    @Test
    public void testFindAllByPageable() throws Exception {
        PageRequest pageRequest = new PageRequest(
                0, 2, Sort.Direction.ASC, User.PROP_USER_ID);
        Page<User> pageUser = userRepository.findAll(pageRequest);
        assertNotNull(pageUser);
        assertNotNull(pageUser.getContent());
        assertThat(pageUser.getContent().get(0), Is.is(innerUser));
        assertEquals(pageUser.getTotalElements(), 4);
    }

    @Test
    public void testFindAllByIds() throws Exception {
        Iterable<User> users = userRepository.findAll(
                Sets.newHashSet(user1.getId(), user2.getId()));

        assertNotNull(users);
        assertTrue(Iterables.size(users) == 2);
        assertTrue(Iterables.contains(users, user1));
        assertTrue(Iterables.contains(users, user2));
    }

    @Test
    public void testSaveAndFlush() throws Exception {
        User updatedUser = userRepository.saveAndFlush(testUser);
        assertNotNull(updatedUser);
        assertNotNull(testUser.getId());
        assertTrue(testUser == updatedUser);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDeleteAllInBatch() throws Exception {
        userRepository.deleteAllInBatch();
    }

    @Test
    public void testDeleteInBatch() throws Exception {
        userRepository.deleteInBatch(Sets.newHashSet(user1, user2));

        User existsUser1 = userRepository.findOne(user1.getId());
        User existsUser2 = userRepository.findOne(user2.getId());
        assertNull(existsUser1);
        assertNull(existsUser2);
    }

    @Test
    public void testSaveAll() throws Exception {
        List<User> savedUsers = userRepository.save(Sets.newHashSet(testUser));
        assertNotNull(savedUsers);
        assertTrue(savedUsers.size() == 1);
        assertThat(savedUsers.get(0), Is.is(testUser));
    }
}
