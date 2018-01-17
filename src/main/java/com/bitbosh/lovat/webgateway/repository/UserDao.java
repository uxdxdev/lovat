package com.bitbosh.lovat.webgateway.repository;

import com.bitbosh.lovat.webgateway.core.User;
import com.bitbosh.lovat.webgateway.core.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(UserMapper.class)
public interface UserDao {

    @SqlQuery("select * from users where username = :username limit 1")
    User getUserByUsername(@Bind("username") String username);

}
