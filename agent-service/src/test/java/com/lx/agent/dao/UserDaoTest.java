package com.lx.agent.dao;

import com.lx.agent.SpringTest;
import com.lx.agent.domain.User;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @author hubery.chen
 */
public class UserDaoTest extends SpringTest {

    @Inject
    private UserDao userDao;

    @Test
    public void getTest() {
        User user = userDao.get("123");
        Assert.assertNull(user);
    }

    @Test
    public void countTest() {
        Integer count = userDao.count("123");
        Assert.assertTrue(count == 0);
    }
}
