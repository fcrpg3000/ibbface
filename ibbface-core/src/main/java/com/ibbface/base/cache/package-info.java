/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) package-info.java 2013-08-10 23:49
 */

/**
 * 提供缓存所需的类和接口。
 * <p />
 * 该缓存组件中的接口设计参考了 <a href="http://code.google.com/p/guava-libraries/">Google-Guava library</a>。
 * 但缓存的存储实现与 {@code guava} 完全不同，该缓存使用了 {@link java.util.concurrent.Delayed} 接口作为缓存对象的载体，
 * 实现在指定过期时间后自动执行 {@code remove} 操作。
 */
package com.ibbface.base.cache;