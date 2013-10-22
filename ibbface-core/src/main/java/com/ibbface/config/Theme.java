package com.ibbface.config;

import java.io.Serializable;
import java.util.Locale;

/**
 * @author Fuchun
 * @since 1.0
 */
public final class Theme implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Locale DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;

    public static Theme simplifiedChinese(final String themeName) {
        return new Theme(themeName, DEFAULT_LOCALE.getDisplayName());
    }

    private final String name;
    private final String local;

    public Theme(String name, String local) {
        this.name = name;
        this.local = local;
    }

    public String getName() {
        return name;
    }

    public String getLocal() {
        return local;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Theme)) return false;

        Theme theme = (Theme) o;

        return local.equals(theme.local) && name.equals(theme.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + local.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("Theme{name='%s', local='%s'}", getName(), getLocal());
    }
}
