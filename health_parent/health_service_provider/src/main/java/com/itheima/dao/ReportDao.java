package com.itheima.dao;

public interface ReportDao {
    int findCountMembers();

    int findCountInDate(String today);



    int findCountAfterDate(String today);

    int findCountByDate(String s);
}
