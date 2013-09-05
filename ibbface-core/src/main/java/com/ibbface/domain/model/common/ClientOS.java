/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ClientOS.java 2013-09-03 17:12
 */

package com.ibbface.domain.model.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.io.Closer;
import com.google.common.io.LineReader;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * The client operation system information (e.g. id, name and version).
 *
 * @author Fuchun
 * @since 1.0
 */
public class ClientOS implements Serializable, Comparable<ClientOS> {
    private static final long serialVersionUID = 1L;

    public static final String WINDOWS = "windows";
    public static final String WINDOWS_PHONE = "windows phone";
    public static final String MAC_OS_X = "mac os x";
    public static final String IPHONE = "iphone";
    public static final String ANDROID = "android";
    public static final String IPAD = "ipad";
    public static final String IPOD = "ipod";

    private static final Map<Integer, ClientOS> DATA_MAP = new HashMap<Integer, ClientOS>(60);
    private static final Table<String, String, Integer> DATA_TABLE = HashBasedTable.create();

    /**
     * Unknown Android Operation System.
     */
    public static final ClientOS UNKNOWN_ANDROID = putIfAbsent(100, ANDROID, "Android", "");
    /**
     * Unknown iPhone Operation System.
     */
    public static final ClientOS UNKNOWN_IPHONE = putIfAbsent(200, IPHONE, "iPhone", "");
    /**
     * Unknown Mac OS X.
     */
    public static final ClientOS UNKNOWN_MAX_OS_X = putIfAbsent(6100, MAC_OS_X, "Mac OS X", "");
    /**
     * Unknown Windows.
     */
    public static final ClientOS UNKNOWN_WINDOWS = putIfAbsent(500, WINDOWS, "Windows", "");
    /**
     * Unknown Windows phone.
     */
    public static final ClientOS UNKNOWN_WINDOWS_PHONE = putIfAbsent(570, WINDOWS_PHONE, "Windows Phone", "");
    /**
     * Unknown Operation System.
     */
    public static final ClientOS OTHER = putIfAbsent(1, "other", "OtherOS", "");

    static {
        InputStream input = ClientOS.class.getResourceAsStream("/META-INF/data/client_os.data");
        Closer closer = Closer.create();
        try {
            InputStreamReader reader = closer.register(
                    new InputStreamReader(input, Charsets.UTF_8));
            LineReader lineReader = new LineReader(reader);
            String line;
            while ((line = lineReader.readLine()) != null) {
                JSON.parseObject(line, ClientOS.class);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            try {
                closer.close();
            } catch (IOException ioe) {
                // quiet close
            }
        }
    }

    @JSONCreator
    public static ClientOS putIfAbsent(
            @JSONField(name = "id") Integer id,
            @JSONField(name = "name") String name,
            @JSONField(name = "fullName") String fullName,
            @JSONField(name = "version") String version) {
        ClientOS clientOS = DATA_MAP.get(id);
        if (clientOS == null) {
            clientOS = new ClientOS(id, name, fullName, version);
            DATA_MAP.put(id, clientOS);
            DATA_TABLE.put(name, version, id);
        }
        return clientOS;
    }

    /**
     * Returns the {@code Optional<ClientOS>} of the specified id.
     * <p/>
     * Returns a absent {@code Optional} if {@code id == null || id <= 0} or not exists.
     * <pre>
     * ClientOS os = ClientOS.get(1).orNull();
     * </pre>
     *
     * @param id the specified id.
     * @return a (not null) Optional&lt;ClientOS&gt; of the specified id.
     */
    @Nonnull
    public static Optional<ClientOS> get(Integer id) {
        if (id == null || id <= 0) {
            return Optional.absent();
        }
        return Optional.fromNullable(DATA_MAP.get(id));
    }

    /**
     * Returns the {@code Optional<ClientOS>} of the specified name and version.
     * <p/>
     * Returns a absent {@code Optional} if {@code name == null || version == null} or not exists.
     * <pre>
     * ClientOS os = ClientOS.get("windows", "1.1").orNull();
     * </pre>
     *
     * @param name    the ClientOS name.
     * @param version the ClientOS version.
     * @return a (not null) Optional&lt;ClientOS&gt; of the specified name and version.
     */
    @Nonnull
    public static Optional<ClientOS> get(String name, String version) {
        if (name == null || version == null) {
            return Optional.absent();
        }
        Integer id = DATA_TABLE.get(name, version);
        return get(id);
    }

    /**
     * Returns all {@link ClientOS} of the specified name, or empty list if name is null or empty,
     * or name not exists.
     * <pre>
     * List&lt;ClientOS&gt; list = ClientOS.find("iphone");
     * for (ClientOS cos : list) {
     *     .... do something
     * }
     * </pre>
     *
     * @param name the client os name.
     */
    @Nonnull
    public static List<ClientOS> find(String name) {
        if (isNullOrEmpty(name)) {
            return ImmutableList.of();
        }
        Map<String, Integer> rowMap = DATA_TABLE.row(name);
        if (rowMap.isEmpty()) {
            return ImmutableList.of();
        }
        final List<Integer> ids = Lists.newArrayList(rowMap.values());
        Map<Integer, ClientOS> resultMap = Maps.filterKeys(DATA_MAP, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return ids.contains(input);
            }
        });
        List<ClientOS> result = Lists.newArrayList(resultMap.values());
        Collections.sort(result);
        return result;
    }

    private final Integer id;
    private final String name;
    private final String fullName;
    private final String version;

    ClientOS(Integer id, String name, String fullName, String version) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientOS)) return false;

        ClientOS that = (ClientOS) o;

        if (!id.equals(that.id)) {
            return false;
        } else if (!name.equals(that.name)) {
            return false;
        } else if (!version.equals(that.version)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + version.hashCode();
        // result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public int compareTo(ClientOS that) {
        return getId().compareTo(that.getId());
    }

    @Override
    public String toString() {
        return String.format("ClientOS{id=%d, name='%s', fullName='%s', version='%s'}",
                getId(), getName(), getFullName(), getVersion());
    }
}
