/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) TestDatabaseFactory.java 2013-07-30 00:12
 */

package com.ibbface.base.storage;

import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

/**
 * @author Fuchun
 * @since 1.0
 */
public class HSQLDBFactory {

    private static final String DERBY_DATABASE_DRIVER = "org.hsqldb.jdbc.JDBCDriver";

    private final List<String> tableDDLs = Lists.newLinkedList();
    private String databaseUrl;
    private String username;
    private String password;

    private static final HSQLDBFactory INSTANCE = new HSQLDBFactory();

    public static HSQLDBFactory getFactory() {
        return INSTANCE;
    }

    private HSQLDBFactory() {

    }

    public void initialize() {
        Statement st = null;
        Connection conn = null;
        try {
            Class.forName(DERBY_DATABASE_DRIVER).newInstance();
            conn = DriverManager.getConnection(databaseUrl);
            st = conn.createStatement();

            for (String tableDDL : INSTANCE.tableDDLs) {
                st.executeUpdate(tableDDL);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) { /* ignore */ }
        }
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void setTableDDLs(List<String> tableDDLs) {
        if (tableDDLs == null || tableDDLs.isEmpty()) {
            return;
        }
        this.tableDDLs.addAll(tableDDLs);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
