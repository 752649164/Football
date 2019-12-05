package itheima.service;

import com.itheima.pojo.Member;

public interface MemberService {
    Member findMemberByTelephone(String telephone);
    void add(Member member);
}
