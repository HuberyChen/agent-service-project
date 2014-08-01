package com.lx.agent.service;

import com.core.crypto.EncryptionUtils;
import com.core.utils.ClasspathResource;
import com.core.utils.StringUtils;
import com.lx.agent.dao.UserDao;
import com.lx.agent.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author hubery.chen
 */
@Service
public class UserService {

    private UserDao userDao;

    @Transactional
    public int register(User user) {
        userDao.save(user);
        return user.getId();
    }

    public Integer count(String emailAddress) {
        return userDao.count(emailAddress);
    }

    public User get(String emailAddress, String password) {
        User user = userDao.get(emailAddress);
        String decryptPassword = EncryptionUtils.decrypt(user.getPassword(), new ClasspathResource("private.key"));
        if (!StringUtils.equals(password, decryptPassword)) {
            return null;
        }
        user.setPassword(decryptPassword);
        return user;
    }

    @Inject
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
