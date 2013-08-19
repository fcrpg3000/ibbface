package com.ibbface.domain.shared;

import java.io.Serializable;

/**
 * 一个抽象的实体类，实际的实体类只需继承此抽象类即可。
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class AbstractEntity<PK extends Serializable, T>
        implements Entity<PK, T> {
    private static final long serialVersionUID = 1L;

    private PK id;

    /**
     * 返回当前实体的主键。
     */
    @Override
    public PK getId() {
        return id;
    }

    /**
     * 设置当前实体的主键。
     *
     * @param id 当前实体的主键。
     */
    @Override
    public void setId(PK id) {
        this.id = id;
    }

    /**
     * 检查当前是否是一个新的实体类。
     *
     * @return 如果当前实体是一个实体类，则返回 {@code true}，否则 {@code false}。
     */
    @Override
    public boolean isNew() {
        return getId() == null;
    }

    /**
     * 实体类对象是否相等，只比较唯一标识（PK），不比较其他属性。
     * <p/>
     * 此接口方法类似于 equals 方法，但 sameIdentityAs 方法常用于比较两个对象是否相等。
     *
     * @param other 要比较的对象。
     * @return 如果实体相等，则返回 {@code true}，否则 {@code false}。
     */
    @Override
    public boolean sameIdentityAs(T other) {
        return equals(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;

        @SuppressWarnings("rawtypes")
        AbstractEntity<? extends Serializable, ?> that =
                (AbstractEntity<? extends Serializable, ?>) o;

        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        if (getId() == null) {
            hashCode += super.hashCode();
        } else {
            hashCode += (getClass().getName() + ":" + getId()).hashCode();
        }
        return hashCode;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@Entity: (id=" + getId() + ")";
    }
}
