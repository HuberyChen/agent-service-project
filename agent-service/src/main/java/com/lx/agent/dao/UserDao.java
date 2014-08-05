package com.lx.agent.dao;

import com.core.database.JPAAccess;
import com.lx.agent.domain.User;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hubery.chen
 */
@Repository
public class UserDao {

    private JPAAccess jpaAccess;

    public int save(User user) {
        jpaAccess.save(user);
        return user.getId();
    }

    public User get(String emailAddress) {
        Map<String, Object> params = new HashMap<>();
        params.put("emailAddress", emailAddress);
        return jpaAccess.findUniqueResult("from " + User.class.getName() + " where emailAddress = :emailAddress", params);
    }

    public Integer count(String emailAddress) {
        Map<String, Object> params = new HashMap<>();
        params.put("emailAddress", emailAddress);
        Long count = jpaAccess.findUniqueResult("select count(id) from " + User.class.getName() + " where emailAddress = :emailAddress", params);
        return count.intValue();
    }

    @Inject
    public void setJpaAccess(JPAAccess jpaAccess) {
        this.jpaAccess = jpaAccess;
    }
}
