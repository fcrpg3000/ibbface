/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UserRole.java 2013-08-16 16:57
 */

package com.ibbface.domain.model.user;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ibbface.domain.shared.AbstractValueObject;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The user role information.
 *
 * @author Fuchun
 * @since 1.0
 */
public class UserRole extends AbstractValueObject<UserRole> {
    private static final long serialVersionUID = 1L;

    /**
     * Administrators.
     */
    public static final UserRole ADMIN_USER = new UserRole(11, "Admin", "系统管理员，拥有无限制的权限！");
    /**
     * Super users.
     */
    public static final UserRole SUPER_USER = new UserRole(7, "SuperUser", "超级用户，拥有除后台管理外的所有权限！");
    /**
     * Authorization users.
     */
    public static final UserRole AUTH_USER = new UserRole(5, "AuthUser", "通过身份认证的会员用户，拥有比普通注册会员更高级的权限！");
    /**
     * Register users.
     */
    public static final UserRole ROLE_USER = new UserRole(3, "RoleUser", "注册用户，拥有所有注册会员用户的权限！");
    /**
     * Guest.
     */
    public static final UserRole GUEST = new UserRole(2, "Guest", "游客，可以查看不受权限控制的资源！");

    public static final Integer NOT_GUEST = ADMIN_USER.getId() * SUPER_USER.getId() *
            AUTH_USER.getId() * ROLE_USER.getId();
    public static final Integer ALL = ADMIN_USER.getId() * SUPER_USER.getId() *
            AUTH_USER.getId() * ROLE_USER.getId() * GUEST.getId();

    private static final Map<Integer, UserRole> ALL_ENTRIES;
    private static final Map<String, Integer> NAME_ID_MAP;

    static {
        ImmutableMap.Builder<Integer, UserRole> builder = ImmutableMap.builder();
        ImmutableMap.Builder<String, Integer> nameIdMapBuilder = ImmutableMap.builder();
        ALL_ENTRIES = builder
                .put(GUEST.getId(), GUEST)
                .put(ROLE_USER.getId(), ROLE_USER)
                .put(AUTH_USER.getId(), AUTH_USER)
                .put(SUPER_USER.getId(), SUPER_USER)
                .put(ADMIN_USER.getId(), ADMIN_USER)
                .build();
        NAME_ID_MAP = nameIdMapBuilder
                .put(GUEST.getName(), GUEST.getId())
                .put(ROLE_USER.getName(), ROLE_USER.getId())
                .put(AUTH_USER.getName(), AUTH_USER.getId())
                .put(SUPER_USER.getName(), SUPER_USER.getId())
                .put(ADMIN_USER.getName(), ADMIN_USER.getId())
                .build();
    }

    /**
     * Returns the {@link UserRole} of the specified id, or {@link #GUEST} if
     * not exists.
     *
     * @param id the UserRole id.
     */
    @Nonnull
    public static UserRole get(Integer id) {
        UserRole role = ALL_ENTRIES.get(id);
        return role == null ? GUEST : role;
    }

    /**
     * Returns the {@link UserRole} of the specified name, or {@link #GUEST} if
     * not exists.
     *
     * @param name the UserRole name.
     */
    @Nonnull
    public static UserRole get(String name) {
        Integer id = NAME_ID_MAP.get(name);
        if (id == null) {
            return GUEST;
        }
        return ALL_ENTRIES.get(id);
    }

    /**
     * Returns all {@link UserRole}s list.
     */
    @Nonnull
    public static List<UserRole> all() {
        List<UserRole> list = Lists.newArrayListWithCapacity(ALL_ENTRIES.size());
        for (Map.Entry<Integer, UserRole> entry : ALL_ENTRIES.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    /**
     * Calculate and return multiplied by all id the specified {@link UserRole}s.
     *
     * @param roles the UserRoles.
     */
    public static Integer toRoleData(Iterable<UserRole> roles) {
        if (roles == null) {
            return null;
        }
        Integer result = 1;
        for (UserRole role : roles) {
            if (result % role.getId() == 0) {
                continue;
            }
            result *= role.getId();
        }
        return result;
    }

    /**
     * Returns the {@link UserRole}s of the specified {@code roleData} value.
     *
     * @param roleData the role data value.
     */
    public static Set<UserRole> fromRoleData(Integer roleData) {
        Set<UserRole> result = Sets.newHashSet();
        for (Map.Entry<Integer, UserRole> entry : ALL_ENTRIES.entrySet()) {
            if (roleData % entry.getKey() == 0) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

    private final Integer id;
    private final String name;
    private final String description;

    public UserRole(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole)) return false;

        UserRole userRole = (UserRole) o;

        if (description != null ? !description.equals(userRole.description) : userRole.description != null)
            return false;
        if (!id.equals(userRole.id)) return false;
        return name.equals(userRole.name);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("UserRole{id=%s, name='%s', description='%s'}",
                getId(), getName(), getDescription());
    }
}
