/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UserRole.java 2013-08-16 16:57
 */

package com.ibbface.domain.model.user;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.ibbface.domain.shared.AbstractValueObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

    public static final UserRole ADMIN_USER = new UserRole("Admin", "系统管理员，拥有无限制的权限！");
    public static final UserRole SUPER_USER = new UserRole("SuperUser", "超级用户，拥有除后台管理外的所有权限！");
    public static final UserRole AUTH_USER = new UserRole("AuthUser", "通过身份认证的会员用户，拥有比普通注册会员更高级的权限！");
    public static final UserRole ROLE_USER = new UserRole("RoleUser", "注册用户，拥有所有注册会员用户的权限！");
    public static final UserRole GUEST = new UserRole("Guest", "游客，可以查看不受权限控制的资源！");

    private static final Map<String, UserRole> ALL_ENTRIES;

    static {
        ImmutableMap.Builder<String, UserRole> builder = ImmutableMap.builder();
        ALL_ENTRIES = builder
                .put(ADMIN_USER.getName(), ADMIN_USER)
                .put(SUPER_USER.getName(), SUPER_USER)
                .put(AUTH_USER.getName(), AUTH_USER)
                .put(ROLE_USER.getName(), ROLE_USER)
                .put(GUEST.getName(), GUEST)
                .build();
    }

    public static final Function<UserRole, String> ROLE_2_NAMES_FUNC =
            new Function<UserRole, String>() {
                @Nullable
                @Override
                public String apply(@Nullable UserRole input) {
                    return input == null ? null : input.getName();
                }
            };

    /**
     * Returns the {@link UserRole} of the specified name, or {@link #GUEST} if
     * not exists.
     *
     * @param name the UserRole name.
     */
    @Nonnull
    public static UserRole get(String name) {
        UserRole role = ALL_ENTRIES.get(name);
        return role == null ? GUEST : role;
    }

    /**
     * Returns all {@link UserRole}s list.
     */
    @Nonnull
    public static List<UserRole> all() {
        List<UserRole> list = Lists.newArrayListWithCapacity(ALL_ENTRIES.size());
        for (Map.Entry<String, UserRole> entry : ALL_ENTRIES.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    public static Iterable<String> toRoleNames(Iterable<UserRole> roles) {
        if (roles == null) {
            return null;
        }
        return Iterables.transform(roles, ROLE_2_NAMES_FUNC);
    }

    public static void names2Roles(
            final Iterable<String> names, @Nonnull final Set<UserRole> roles) {
        for (String roleName : names) {
            roles.add(UserRole.get(roleName));
        }
    }

    private final String name;
    private final String description;

    public UserRole(String name, String description) {
        this.name = name;
        this.description = description;
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

        return !(name != null ? !name.equals(userRole.name) : userRole.name != null) &&
                !(description != null ? !description.equals(userRole.description) :
                        userRole.description != null);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("UserRole{name='%s', description='%s'}", getName(), getDescription());
    }
}
