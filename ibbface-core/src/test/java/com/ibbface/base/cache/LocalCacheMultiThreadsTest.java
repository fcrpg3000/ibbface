/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) LocalCacheTest.java 2013-08-12 13:03
 */

package com.ibbface.base.cache;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.apache.commons.codec.digest.DigestUtils;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Fuchun
 * @since 1.0
 */
public class LocalCacheMultiThreadsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalCacheMultiThreadsTest.class);
    private static Cache<String, String> cache;

    @BeforeClass
    public static void initialize() throws Exception {
        cache = CacheBuilder.newBuilder("TestCache").initialCapacity(100)
                .maximumSize(10000)
                .addRemovalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(
                            RemovalNotification<String, String> notification) {
                        if (notification.getCause() == RemovalCause.REPLACED) {
                            LOGGER.debug("RemovalCause = REPLACED, ignored.");
                            return;
                        }
                        LOGGER.info("The entry({}, {}) was removed.",
                                notification.getKey(), notification.getValue());
                    }
                })
                .expireAfterAccess(15, TimeUnit.MINUTES)
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .build();
    }

    @AfterClass
    public static void destroy() throws Exception {
        cache.shutdown();
    }

    @Before
    public void setUp() throws Exception {
        List<String> dataList = Lists.newLinkedList();
        for (int i = 0; i < 1000; i++) {
            String data = DigestUtils.sha1Hex(String.valueOf(i));
            dataList.add(data);
            cache.put(data, data);
        }
    }

    @After
    public void tearDown() throws Exception {
        cache.clear();
    }

    @Test
    public void testCacheOpsOnMulti() throws Throwable {
        TestRunnable[] tr = new TestRunnable[] {
                new GetValueInCache(0, 1000),
                new GetValueInCache(50, 1500),
                new PutValueToCache(1000, 2000),
                new PutValueToCache(2000, 3000)
        };
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(tr);
        mttr.runTestRunnables(5 * 60 * 1000L);
        Assert.assertTrue(cache.size() == 3000);
    }

    public static class GetValueInCache extends TestRunnable {

        final int start;
        final int end;

        public GetValueInCache(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void runTest() throws Throwable {
            Random r = new Random();
            Stopwatch stopwatch = new Stopwatch().start();
            for (int i = start; i < end; i++) {
                int v = r.nextInt(end) + start;
                String key = DigestUtils.sha1Hex(String.valueOf(v));
                String data = cache.getIfPresent(key);
                if (v >= 0 && v <= 9) {
                    Assert.assertNotNull(data);
                    Assert.assertThat(data, Is.is(key));
                } else {
                    if (data != null) {
                        LOGGER.info("Found new entry: {} = {}", key, data);
                    }
                }
            }
            stopwatch.stop();
            long totalMs = stopwatch.elapsed(TimeUnit.MILLISECONDS),
                    totalNs = stopwatch.elapsed(TimeUnit.NANOSECONDS);
            LOGGER.info("Cache#put run times: {}, average: {} ms({} ns)",
                    (end - start), totalMs / (end - start), totalNs / (end - start));
        }
    }

    public static class PutValueToCache extends TestRunnable {

        final int start;
        final int end;

        public PutValueToCache(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void runTest() throws Throwable {
            Stopwatch stopwatch = new Stopwatch().start();
            for (int i = start; i < end; i++) {
                String data = DigestUtils.sha1Hex(String.valueOf(i));
                cache.put(data, data);
            }
            stopwatch.stop();
            long totalMs = stopwatch.elapsed(TimeUnit.MILLISECONDS),
                totalNs = stopwatch.elapsed(TimeUnit.NANOSECONDS);
            LOGGER.info("Cache#put run times: {}, average: {} ms({} ns)",
                    (end - start), totalMs / (end - start), totalNs / (end - start));
        }
    }
}
