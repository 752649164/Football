package com.itheima.dao;

import com.itheima.pojo.Member;

public interface MemberDao {
    Member findByTelephone(String telephone);

    void save(Member member1);

    void add(Member member);
}
