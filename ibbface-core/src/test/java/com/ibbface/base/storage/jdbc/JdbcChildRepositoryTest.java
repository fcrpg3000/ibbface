package com.ibbface.base.storage.jdbc;

import com.google.common.collect.Lists;
import com.ibbface.domain.model.common.BloodType;
import com.ibbface.domain.model.user.Child;
import com.ibbface.domain.model.user.Gender;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Fuchun
 * @version $Id$
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/test-*.xml")
public class JdbcChildRepositoryTest {

    private static final Long ONE_CHILD_USER = 10001L;
    private static final Long TWO_CHILD_USER = 10002L;

    @Resource
    private JdbcChildRepository childRepository;

    private List<Child> oneChild;
    private List<Child> twoChild;

    @Before
    public void setUp() throws Exception {
        oneChild = Lists.newArrayListWithExpectedSize(1);
        Child child = Child.newChild(ONE_CHILD_USER, "龙龙", Gender.MALE);
        child.setBloodType(BloodType.B_BLOOD);
        child.birthday(DateTime.parse("2012-12-12").toDate());
        oneChild.add(child);
        childRepository.save(oneChild);

        twoChild = Lists.newArrayListWithExpectedSize(2);
        Child c1 = Child.newChild(TWO_CHILD_USER, "欢欢", Gender.MALE);
        c1.setBloodType(BloodType.A_BLOOD);
        c1.birthday(DateTime.parse("2013-03-14").toDate());
        Child c2 = Child.newChild(TWO_CHILD_USER, "乐乐", Gender.FEMALE);
        c2.setBloodType(BloodType.A_BLOOD);
        c2.birthday(DateTime.parse("2013-03-14").toDate());

        twoChild.add(c1);
        twoChild.add(c2);
        childRepository.save(twoChild);
    }

    @After
    public void tearDown() throws Exception {
        Child child = oneChild.isEmpty() ? null : oneChild.get(0);
        if (child != null && child.getId() != null) {
            childRepository.delete(child.getId());
        }
        if (twoChild == null || twoChild.size() == 0) {
            return;
        }
        for (Child c : twoChild) {
            if (c != null && c.getId() != null) {
                childRepository.delete(c.getId());
            }
        }
    }

    @Test
    public void testFindByUserId() throws Exception {
        List<Child> children = childRepository.findByUserId(TWO_CHILD_USER);

        assertNotNull(children);
        assertEquals(children.size(), 2);
        assertTrue(children.contains(twoChild.get(0)));
        assertTrue(children.contains(twoChild.get(1)));
    }
}
