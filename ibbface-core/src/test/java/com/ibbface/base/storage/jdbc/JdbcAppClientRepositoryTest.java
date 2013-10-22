package com.ibbface.base.storage.jdbc;

import com.google.common.collect.Lists;
import com.ibbface.domain.model.client.AppClient;
import com.ibbface.domain.model.client.ClientType;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Fuchun
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/test-context.xml")
public class JdbcAppClientRepositoryTest {

    @Resource
    private JdbcAppClientRepository appClientRepository;

    private AppClient android_v1, android_v1_1;
    private AppClient iphone_v1, iphone_v1_1;
    private List<AppClient> iphoneClients;
    private List<AppClient> androidClients;

    @Before
    public void setUp() throws Exception {
        android_v1 = AppClient.newAndroid("1.0", "Release",
                "IBBFace Android v1.0", null, null, true, new Date());
        android_v1_1 = AppClient.newAndroid("1.1", "Release",
                "IBBFace Android v1.1", null, null, true, new Date());
        androidClients = Lists.newArrayList(android_v1, android_v1_1);

        iphone_v1 = AppClient.newIPhone("1.0", "Release",
                "IBBFace iPhone v1.0", null, null, true, new Date());
        iphone_v1_1 = AppClient.newIPhone("1.1", "Release",
                "IBBFace iPhone v1.1", null, null, true, new Date());
        iphoneClients = Lists.newArrayList(iphone_v1, iphone_v1_1);

        appClientRepository.save(androidClients);
        appClientRepository.save(iphoneClients);
    }

    @After
    public void tearDown() throws Exception {
        appClientRepository.deleteAllInBatch();
        androidClients.clear();
        iphoneClients.clear();
        androidClients = iphoneClients = null;
    }

    @Test
    public void testFindByVersion() {
        List<AppClient> appClients = appClientRepository.findByVersion("1.0");

        assertNotNull(appClients);
        assertTrue(appClients.size() == 2);
        assertTrue(appClients.contains(android_v1));
        assertTrue(appClients.contains(iphone_v1));

        appClients = appClientRepository.findByVersion("1.1");

        assertNotNull(appClients);
        assertTrue(appClients.size() == 2);
        assertTrue(appClients.contains(android_v1_1));
        assertTrue(appClients.contains(iphone_v1_1));

        appClients = appClientRepository.findByVersion("1.2");

        assertNotNull(appClients); // returned value never null
        assertTrue(appClients.isEmpty());
    }

    @Test
    public void testFindByTypeAndVersion() {
        AppClient appClient = appClientRepository.findByTypeAndVersion(ClientType.ANDROID.getCode(),
                "1.1");
        assertNotNull(appClient);
        assertThat(appClient, Is.is(android_v1_1));

        appClient = appClientRepository.findByTypeAndVersion(ClientType.ANDROID.getCode(),
                "1.2");
        assertNull(appClient);
    }
}
