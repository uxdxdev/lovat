package com.bitbosh.lovat.webgateway.repository;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface AccountsDao {

    @SqlQuery("select passhash from accounts where email = :email")
    String getPasswordByEmail(@Bind("email") String email);

}
