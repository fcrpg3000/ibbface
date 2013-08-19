/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Cache.java 2013-08-10 23:49
 */

package com.ibbface.base.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * 提供半持久化的键值对映射的缓存接口。使用 {@link #get(Object, java.util.concurrent.Callable)} 或者
 * {@link #put(Object, Object)} 方法添加条目，并存储在缓存中，直到它过期失效或者手动使其过期。
 *
 * @author Fuchun
 * @since 1.0
 * @version $Id: Cache.java 27663 2013-05-14 01:09:28Z C629 $
 */
public interface Cache<K, V> {

    /**
     * 返回缓存中指定键 {@code key} 关联的值（如果缓存中没有关联的值，则返回 {@code null}）。
     *
     * @param key 指定的键。
     * @return 返回缓存中指定键 {@code key} 关联的值。
     */
    V getIfPresent(K key);

    /**
     * 返回缓存中指定键 {@code key} 关联的值，如有必要，从 {@code valueLoader} 获得该值。这种方法提供了一种类似
     * "if cached, return; otherwise create, cache and return." 模版的简单的替换功能。
     *
     * <pre>
     * 该方法执行的过程简述：
     * 1）从缓存中查询指定键 {@code key} 关联的对象（记为 value）；
     *   a）如果 {@code value != null}，立即返回 {@code value}；
     *   b）否则，执行 {@code valueLoader} 并返回需要关联的对象 {@code value}，即 value = valueLoader.call();
     *   c) 将 {@code value} 存入缓存，并返回 {@code value}。
     * 2）返回关联的对象 {@code value}。
     * </pre>
     *
     * <strong>Warning:</strong> {@code valueLoader} 不能返回 {@code null} 值，valueLoader.call()
     * 只能返回一个非空值，或者抛出异常。
     *
     * @param key 指定的键。
     * @param valueLoader 加载缓存值的任务接口实例。
     * @return 返回缓存中指定键 {@code key} 关联的值，如有必要，从 {@code valueLoader} 获得该值。
     * @throws java.util.concurrent.ExecutionException 当执行 {@code valueLoader} 时，发生异常。
     */
    V get(K key, Callable<? extends V> valueLoader) throws ExecutionException;

    /**
     * 返回缓存中指定键 {@code key} 关联的值，如有必要，从 {@code valueLoader} 获得该值。这种方法提供了一种类似
     * "if cached, return; otherwise create, cache and return." 模版的简单的替换功能。
     *
     * <pre>
     * 该方法执行的过程简述：
     * 1）从缓存中查询指定键 {@code key} 关联的对象（记为 value）；
     *   a）如果 {@code value != null}，立即返回 {@code value}；
     *   b）否则，执行 {@code valueLoader} 并返回需要关联的对象 {@code value}，即 value = valueLoader.call();
     *   c) 将 {@code value} 存入缓存，并设置缓存过期时间（秒）后返回 {@code value}。
     * 2）返回关联的对象 {@code value}。
     * </pre>
     *
     * <strong>Warning:</strong> {@code valueLoader} 不能返回 {@code null} 值，valueLoader.call()
     * 只能返回一个非空值，或者抛出异常。
     *
     * @param key 指定的键。
     * @param seconds 指定键在缓存中的过期时间（秒）。
     * @param valueLoader 加载缓存值的任务接口实例。
     * @return 返回缓存中指定键 {@code key} 关联的值，如有必要，从 {@code valueLoader} 获得该值。
     * @throws java.util.concurrent.ExecutionException 当执行 {@code valueLoader} 时，发生异常。
     */
    V get(K key, int seconds, Callable<? extends V> valueLoader) throws ExecutionException;

    /**
     * 设置缓存中的键值对，如果指定的键 {@code key} 在缓存中已存在，则旧的值将被替换。
     * <p />
     * 如果要使用类似 "if cached, return; otherwise create, cache and return." 模版的替换功能，那就使用
     * {@link #get(Object, java.util.concurrent.Callable)} 方法。
     *
     * @param key 缓存键。
     * @param value 缓存的值。
     */
    void put(K key, V value);

    /**
     * 设置缓存中的键值对，并设置指定键关联的值的过期时间（秒），如果指定的键 {@code key} 在缓存中已存在，则旧的值将被替换。
     * <p />
     * 如果要使用类似 "if cached, return; otherwise create, cache and return." 模版的替换功能，那就使用
     * {@link #get(Object, int, java.util.concurrent.Callable)} 方法。
     *
     * @param key 缓存键。
     * @param value 缓存的值。
     * @param seconds 缓存过期的时间（秒）。
     */
    void put(K key, V value, int seconds);

    /**
     * 如果指定的 {@code key} 在本地缓存数据中还没有关联值 {@code value}，则在缓存数据中关联指定的值 {@code value}；否则直接返回指定的
     * {@code key} 在缓存中关联的值。调用该方法相当于：
     *
     * <pre>
     * if (!cache.exists(key)) {
     *     cache.put(key, value);
     * } else {
     *     return cache.getIfPresent(key);
     * }
     * </pre>
     *
     * 但上述过程不是原子的，而该方法是以原子方式执行的。
     *
     * @param key 指定的键。
     * @param value 指定键在缓存中关联的对象。
     * @return 返回指定键 {@code key} 在缓存中关联的旧的对象（或者 {@code null} 如果没有关联任何对象）。
     * @throws UnsupportedOperationException 如果该接口的实现不支持该方法。
     * @throws IllegalArgumentException 如果指定键 {@code key == null} 或值 {@code value == null}
     *         ，或者某些属性不能被存入缓存。
     */
    V putIfAbsent(K key, V value);

    /**
     * 返回以一个线程安全的 {@code ConcurrentMap} 对象作为缓存的视图对象，对该对象的任何修改，将直接影响缓存的数据。
     *
     * @throws UnsupportedOperationException 如果缓存实现不支持此方法。
     */
    ConcurrentMap<K, V> asMap();

    /**
     * 从缓存中移除指定的键 {@code key} 所关联的对象。
     *
     * @param key 要移除的键。
     */
    void remove(K key);

    /**
     * 从缓存中移除指定的一组键 {@code keys} 所关联的所有对象。
     *
     * @param keys 要移除的一组键。
     */
    void removeAll(K... keys);

    /**
     * 从缓存中移除所有的键。
     */
    void clear();

    /**
     * 返回缓存中的键 {@code key} 的数量。
     */
    int size();

    /**
     * 关闭本地缓存，如果它需要关闭。
     */
    void shutdown();

}
