/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcApiResourceRepositoryTest.java 2013-08-18 17:28
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ibbface.domain.model.privilege.ApiParam;
import com.ibbface.domain.model.privilege.ApiResource;
import com.ibbface.domain.model.privilege.Grade;
import com.ibbface.domain.model.privilege.ParameterType;
import com.ibbface.domain.model.user.UserRole;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * The {@link JdbcApiResourceRepository} test case.
 *
 * @author Fuchun
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/test-*.xml")
public class JdbcApiResourceRepositoryTest {

    @Resource
    private JdbcApiResourceRepository apiResourceRepository;

    @Resource
    private JdbcApiParamRepository apiParamRepository;

    // users api resource
    private ApiResource userApi, showUser, chpwd;

    private Set<ApiParam> commonParams, showUserParams, chpwdParams;

    // comments api resource
    private ApiResource commentApi, createComment,
            showComment, showComment2, delComment;

    @Before
    public void setUp() throws Exception {
        setUpApiResource();

        setUpCommonParams();
        setUpShowUserParams();
        setUpChpwdParams();
    }

    private void setUpApiResource() {
        // Users API resource
        userApi = ApiResource.parent("/users", "用户接口", "1", Grade.NORMAL,
                ApiResource.GET, null, true, true);
        userApi.setRoles(Sets.newHashSet(UserRole.ROLE_USER));
        apiResourceRepository.save(userApi);

        showUser = ApiResource.sub(userApi, "/users/show", "获取用户信息", Grade.NORMAL,
                ApiResource.GET, null, true, true);
        chpwd = ApiResource.sub(userApi, "/users/chpwd", "修改登录密码", Grade.NORMAL,
                ApiResource.POST, null, true, true);
        showUser.setRoleData(UserRole.NOT_GUEST);
        chpwd.setRoleData(UserRole.NOT_GUEST);
        apiResourceRepository.save(Lists.newArrayList(showUser, chpwd));

        // Comment API resource
        commentApi = ApiResource.parent("/comments", "评论接口", "1", Grade.NORMAL,
                ApiResource.GET, null, true, true);
        apiResourceRepository.save(commentApi);

        createComment = ApiResource.sub(commentApi, "/comments/create", "发表一个评论",
                Grade.NORMAL, ApiResource.POST, null, true, true);
        showComment = ApiResource.sub(commentApi, "/comments/show", "获取某个资源的评论列表",
                Grade.NORMAL, ApiResource.GET, null, true, true);
        showComment2 = ApiResource.sub(commentApi, "/comments/show", "获取某个资源的评论列表",
                Grade.NORMAL, ApiResource.GET, null, true, true);
        delComment = ApiResource.sub(commentApi, "/comments/delete", "删除指定的评论",
                Grade.ADVANCED, ApiResource.POST, null, true, true);
        showComment2.setVersion("2");
        createComment.setRoleData(UserRole.NOT_GUEST);
        Set<UserRole> allRoles = Sets.newHashSet(UserRole.all());
        showComment.setRoles(allRoles);
        showComment2.setRoles(allRoles);
        delComment.setRoles(Sets.newHashSet(UserRole.ADMIN_USER,
                UserRole.SUPER_USER));
        apiResourceRepository.save(Lists.newArrayList(createComment,
                showComment, showComment2, delComment));
    }

    private void setUpCommonParams() {
        commonParams = Sets.newHashSet();
        commonParams.add(ApiParam.create(null, "access_token", "\\w+",
                ParameterType.STRING, null, "Access token", showUser.getVersion(),
                0, true));
        apiParamRepository.save(commonParams);
    }

    private void setUpShowUserParams() {
        showUserParams = Sets.newHashSet();
        showUserParams.add(ApiParam.create(showUser, "uid", "\\d+",
                ParameterType.UINT64, null, "uid", showUser.getVersion(),
                0, false));
        showUserParams.add(ApiParam.create(showUser, "username", null,
                ParameterType.STRING, null, "username", showUser.getVersion(),
                0, false));
        apiParamRepository.save(showUserParams);
        showUserParams.addAll(commonParams);
        showUser.setParams(showUserParams);
    }

    private void setUpChpwdParams() {
        chpwdParams = Sets.newHashSet(
                ApiParam.create(chpwd, "old_pwd", null, ParameterType.STRING,
                        null, "old password", chpwd.getVersion(), 0, true),
                ApiParam.create(chpwd, "new_pwd1", null, ParameterType.STRING,
                        null, "new password1", chpwd.getVersion(), 0, true),
                ApiParam.create(chpwd, "new_pwd2", null, ParameterType.STRING,
                        null, "new password2", chpwd.getVersion(), 0, true));
        apiParamRepository.save(chpwdParams);
        chpwdParams.addAll(commonParams);
        chpwd.setParams(chpwdParams);
    }

    @After
    public void tearDown() throws Exception {
        apiResourceRepository.deleteAllInBatch();
        apiParamRepository.deleteAllInBatch();
    }

    @Test
    public void testFindByRoleId() throws Exception {
        List<ApiResource> list = apiResourceRepository.findByRoleId(
                UserRole.ROLE_USER.getId());
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertFalse(list.contains(delComment));

        list = apiResourceRepository.findByRoleId(UserRole.GUEST.getId());
        assertNotNull(list);
        assertTrue(list.contains(showComment));
        assertTrue(list.contains(showComment2));
        assertTrue(list.contains(commentApi));
    }

    @Test
    public void testFindByRoleIds() throws Exception {
        List<ApiResource> list = apiResourceRepository.findByRoleIds(
                Sets.newHashSet(UserRole.ROLE_USER.getId(),
                        UserRole.GUEST.getId()));
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertTrue(list.contains(commentApi));
        assertTrue(list.contains(showComment));
        assertTrue(list.contains(showComment2));

        assertFalse(list.contains(delComment));
    }

    @Test
    public void testFindByVersionAndBasePath() throws Exception {
        ApiResource exists = apiResourceRepository.findByVersionAndBasePath(showComment.getVersion(),
                showComment.getBasePath());
        assertNotNull(exists);
        assertThat(exists, Is.is(showComment));

        exists = apiResourceRepository.findByVersionAndBasePath("2", showUser.getBasePath());
        assertNull(exists);
    }

    @Test
    public void testFindByBasePath() throws Exception {
        List<ApiResource> resources = apiResourceRepository.findByBasePath(
                showComment.getBasePath());
        assertNotNull(resources);
        assertTrue(resources.size() == 2);
        assertTrue(resources.contains(showComment) && resources.contains(showComment2));

        resources = apiResourceRepository.findByBasePath(showUser.getBasePath());
        assertNotNull(resources);
        assertTrue(resources.size() == 1);
        assertThat(resources.get(0), Is.is(showUser));
    }

    @Test
    public void testFindByParentId() throws Exception {
        List<ApiResource> subList = apiResourceRepository.findByParentId(userApi.getId());
        assertNotNull(subList);
        assertTrue(subList.size() == 2);
        assertTrue(subList.contains(showUser) && subList.contains(chpwd));

        subList = apiResourceRepository.findByParentId(commentApi.getId());
        assertNotNull(subList);
        assertTrue(subList.size() == 4);
        assertTrue(subList.contains(showComment) && subList.contains(createComment) &&
                subList.contains(showComment2));
    }

    @Test
    public void testFindAllParents() throws Exception {
        List<ApiResource> parents = apiResourceRepository.findAllParents();
        assertNotNull(parents);
        assertTrue(parents.size() == 2);
        assertTrue(parents.contains(userApi) && parents.contains(commentApi));
    }

    // ApiParam test
    // ----------------------------------------------------------------------------------

    @Test
    public void testFindByResourceId() throws Exception {
        List<ApiParam> params = apiParamRepository.findByResourceId(showUser.getId());

        assertNotNull(params);
        assertTrue(params.size() == 2);
        for (final ApiParam param : params) {
            ApiParam exists = Iterables.tryFind(showUserParams, new Predicate<ApiParam>() {
                @Override
                public boolean apply(@Nullable ApiParam input) {
                    return input != null && input.equals(param);
                }
            }).orNull();
            assertNotNull(exists);
            assertThat(param, Is.is(exists));
        }

        List<ApiParam> commons = apiParamRepository.findByResourceId(ApiResource.PARENT_PARENT_ID);
        assertNotNull(commons);
        assertTrue(commons.size() == 1);
        assertTrue(commons.containsAll(commonParams));
    }

    @Test
    public void testFindByResourceIdAndSince() throws Exception {
        List<ApiParam> params = apiParamRepository.findByResourceIdAndSince(
                chpwd.getId(), chpwd.getVersion());
        assertNotNull(params);
        assertTrue(params.size() == 3);
    }
}
